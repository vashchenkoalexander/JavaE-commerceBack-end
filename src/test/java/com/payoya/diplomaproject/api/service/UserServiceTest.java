package com.payoya.diplomaproject.api.service;

import com.payoya.diplomaproject.api.entity.Cart;
import com.payoya.diplomaproject.api.entity.User;
import com.payoya.diplomaproject.api.enums.Role;
import com.payoya.diplomaproject.api.exceptions.UsernameExistException;
import com.payoya.diplomaproject.api.repository.IUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Mock
    private IUserRepository userRepository;

    @Mock
    private CartService cartService;

    @Mock
    PasswordEncoder passwordEncoder;


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        userService = new UserService(userRepository, cartService, passwordEncoder);

    }

    @Test
    void createNewUser() {

        User user = new User();
        user.setUsername("testUser");
        user.setPassword("password");
        user.setRole(Role.USER);

        when(userRepository.findUserByUsername(user.getUsername())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(user.getPassword())).thenReturn("1333");

        User savedUser = userService.saveNewUser(user);

        assertNotNull(savedUser);
        assertEquals("testUser", savedUser.getUsername());
        assertEquals("1333", savedUser.getPassword());
        assertEquals(Role.USER, savedUser.getRole());
        assertNotNull(savedUser.getActivationToken());
        assertTrue(savedUser.getIsActive());

        verify(userRepository, times(1)).findUserByUsername(user.getUsername());
        verify(userRepository, times(1)).save(user);
        verify(cartService, times(1)).createNew(user);

        User mockUser = new User(6L, "UserForTest", "password", "user0k",
                "rok", "user0k@gmail.com");

        Cart testCart = new Cart();
        testCart.setId(1L);
        testCart.setUser(mockUser);

        mockUser.setShoppingCart(testCart);

        when(cartService.createNew(mockUser)).thenReturn(testCart);
        User savedTestUser = userService.saveNewUser(mockUser);

        when(userRepository.findUserByUsername(savedTestUser.getUsername())).thenReturn(Optional.of(savedTestUser));

        assertEquals("UserForTest", userRepository.findUserByUsername("UserForTest").get().getUsername());
        assertEquals("1333", userRepository.findUserByUsername("UserForTest").get().getPassword());
        assertEquals("UserForTest", userRepository.findUserByUsername("UserForTest").get().getUsername());
        assertEquals(testCart, userRepository.findUserByUsername("UserForTest").get().getShoppingCart());

    }

    @Test
    public void SaveNewUserWithExistingUsername() {
        User user = new User();
        user.setUsername("existingUser");

        when(userRepository.findUserByUsername(user.getUsername())).thenReturn(Optional.of(new User()));

        assertThrows(UsernameExistException.class, () -> userService.saveNewUser(user));

        verify(userRepository, times(1)).findUserByUsername(user.getUsername());
        verify(userRepository, never()).save(user);
        verify(cartService, never()).createNew(user);
    }

    @Test
    void findAllUsers() {
        List<User> mockUsersList = new ArrayList<>();

        //mock lists of users in db
        mockUsersList.add(new User(1L, "first_user", "pass", "Name", "Surr_name", "1234@gmail.com"));
        mockUsersList.add(new User(2L,"igrick", "pass", "Igor", "Babenko", "igrick@gmail.com"));
        mockUsersList.add(new User(3L,"rom4ik", "pass", "Roma", "Karpenok", "rom4ik@gmail.com"));
        mockUsersList.add(new User(4L,"deniSka", "pass", "Denis", "Shvec", "deniSka@gmail.com"));

        when(userRepository.findAll()).thenReturn(mockUsersList);

        assertEquals(4, userService.findAllUsers().size());

        verify(userRepository, times(1)).findAll();
    }

    /*
    This test work with real db and with at least 3 data fields
     */
    @Test
    void findUserById() {

        User userTest1 = new User();
        userTest1.setId(1L);
        userTest1.setUsername("userTest1");
        userTest1.setPassword("1333");
        userTest1.setRole(Role.ADMIN);

        when(userRepository.findById(1L)).thenReturn(Optional.of(userTest1));

        assertEquals(1L, userService.findUserById(1L).getId());
        assertEquals("userTest1", userService.findUserById(1L).getUsername());
        assertEquals("1333", userService.findUserById(1L).getPassword());
        assertEquals(Role.ADMIN, userService.findUserById(1L).getRole());

    }

    @Test
    void updateUserById() {
        Long userTestId = 1L;
        User userTest1 = new User();
        userTest1.setId(userTestId);
        userTest1.setUsername("test1");
        userTest1.setPassword("1333");
        userTest1.setRole(Role.ADMIN);

        User updatedUser = new User();
        updatedUser.setId(userTestId);
        updatedUser.setUsername("updatedUser");
        updatedUser.setPassword("newPassword");
        updatedUser.setFirstName("John");
        updatedUser.setLastName("Doe");
        updatedUser.setRole(Role.USER);

        when(userRepository.findById(userTestId)).thenReturn(Optional.of(userTest1));
        when(userRepository.save(userTest1)).thenReturn(updatedUser);
        when(passwordEncoder.encode(updatedUser.getPassword())).thenReturn("encodedPass");

        User result = userService.updateUserById(userTestId, updatedUser);

        assertNotNull(result);

        assertEquals(userTestId, result.getId());
        assertEquals("updatedUser", result.getUsername());
        assertEquals(Role.USER, result.getRole());

    }

    @Test
    void loadUserByUsername() {

        User userTest1 = new User();
        userTest1.setId(1L);
        userTest1.setUsername("test1");
        userTest1.setPassword("1333");
        userTest1.setRole(Role.ADMIN);

        when(userRepository.findUserByUsername("test1")).thenReturn(Optional.of(userTest1));

        assertEquals(1L, ((User) userService.loadUserByUsername("test1")).getId());
        assertEquals("test1", ((User) userService.loadUserByUsername("test1")).getUsername());
        assertEquals("1333", ((User) userService.loadUserByUsername("test1")).getPassword());
        assertEquals(Role.ADMIN, ((User) userService.loadUserByUsername("test1")).getRole());

    }

    @Test
    void LoadUserByUsernameUserNotFound(){
        String username = "nonExistentser";

        when(userRepository.findUserByUsername(username)).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> userService.loadUserByUsername(username));

        verify(userRepository, times(1)).findUserByUsername(username);
    }

    @Test
    void findAllUsersByFirstName() {

        List<User> mockUsersList = new ArrayList<>();

        //mock lists of users in db
        mockUsersList.add(new User(1L, "first_user", "pass", "Name", "Surr_name", "1234@gmail.com"));
        mockUsersList.add(new User(2L,"igrick", "pass", "Igor", "Babenko", "igrick@gmail.com"));
        mockUsersList.add(new User(3L,"rom4ik", "pass", "Roma", "Karpenok", "rom4ik@gmail.com"));
        mockUsersList.add(new User(4L,"deniSka", "pass", "Denis", "Shvec", "deniSka@gmail.com"));
        mockUsersList.add(new User(5L,"deniSkac", "pass", "Name", "Shvec", "deniSka@gmail.com"));

        when(userRepository.findAllByFirstName("Name", Pageable.ofSize(2))).thenReturn(mockUsersList.stream().filter(n -> n.getFirstName().equals("Name")).toList());
        userService.findAllUsers().forEach(System.out::println);

        assertEquals(2, userService.findAllUsersByFirstName("Name", Pageable.ofSize(2)).size());
        userService.findAllUsersByFirstName("Name", Pageable.ofSize(2)).forEach(e -> System.out.println(e));

    }
}