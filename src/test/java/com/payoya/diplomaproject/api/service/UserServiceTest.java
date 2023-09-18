package com.payoya.diplomaproject.api.service;

import com.payoya.diplomaproject.api.entity.User;
import com.payoya.diplomaproject.api.enums.Role;
import com.payoya.diplomaproject.api.repository.IUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Mock
    private IUserRepository userRepository;

    @Mock
    PasswordEncoder passwordEncoder;

    private List<User> mockUsersList = new ArrayList<>();

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        // List of mock User objects

        mockUsersList.add(new User(1L, "first_user", "pass", "Name", "Surr_name", "1234@gmail.com"));
        mockUsersList.add(new User(2L,"igrick", "pass", "Igor", "Babenko", "igrick@gmail.com"));
        mockUsersList.add(new User(3L,"rom4ik", "pass", "Roma", "Karpenok", "rom4ik@gmail.com"));
        mockUsersList.add(new User(4L,"deniSka", "pass", "Denis", "Shvec", "deniSka@gmail.com"));

        when(userRepository.findAll()).thenReturn(mockUsersList);
    }

    @Test
    void createNewUser() {
        User mockUser = new User(6L, "UserForTest", "password", "user0k",
                         "rok", "user0k@gmail.com");
        when(passwordEncoder.encode(any())).thenReturn("encodedPassword");

        mockUser.setPassword(passwordEncoder.encode(mockUser.getPassword()));

        System.out.println("pass: " + mockUser.getPassword());

        when(userRepository.save(mockUser)).thenReturn(mockUser);

        User savedUser = userService.saveNewUser(mockUser);

        System.out.println("saved User:" + savedUser);
        System.out.println("mocked User:" + mockUser);

        assertEquals(savedUser, mockUser);
    }

    @Test
    void findAllUsers() {
        userService.findAllUsers().forEach(System.out::println);
        when(userRepository.findAll()).thenReturn(mockUsersList);

        assertEquals(4, mockUsersList.size());
    }

    /*
    This test work with real db and with at least 3 data fields
     */
    @Test
    void findUserById() {
        // Call the service method to retrieve the user details
        User user = userService.findUserById(1L);
        User user3 = userService.findUserById(3L);

        userService.findAllUsers().forEach(System.out::println);

        // Assert that the returned User object contains the expected values
        assertEquals("bbbb", user.getFirstName());
        assertEquals("Bbbbb", user.getLastName());
        assertEquals("123@gmail.com", user.getEmailAddress());
        assertEquals("$2a$10$kIHeKpZtIUcy6nVbmHlAP.fMTGo6ag55jEZzpbBMGlFkqaPeWLOrK", user3.getPassword());
    }

    @Test
    void updateUserById() {
    }

    @Test
    void loadUserByUsername() {

    }

    @Test
    void findAllUsersByFirstName() {
    }
}