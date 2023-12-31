package com.payoya.diplomaproject.api.service;

import com.payoya.diplomaproject.api.entity.User;
import com.payoya.diplomaproject.api.enums.Role;
import com.payoya.diplomaproject.api.exceptions.UsernameExistException;
import com.payoya.diplomaproject.api.repository.IUserRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class UserService implements UserDetailsService {

    private final IUserRepository userRepository;

    private CartService cartService;

    private final PasswordEncoder passwordEncoder;

    public UserService(IUserRepository userRepository, CartService cartService, @Lazy PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.cartService = cartService;
        this.passwordEncoder = passwordEncoder;
    }

    public List<User> saveUsersList(List<User> users){

        for(User user: users){
            saveNewUser(user);
        }

        return users;
    }

    public User saveNewUser(User user) throws RuntimeException {

        if(userRepository.findUserByUsername(user.getUsername()).isPresent()){
            throw new UsernameExistException("user with this login is exist: " + user.getUsername());
        }

        if(user.getDateOfCreate() == null ){
            user.setDateOfCreate(LocalDateTime.now().withNano(0));
        }

        if(user.getRole() == null){
            user.setRole(Role.USER);
        }

        user.setActivationToken(UUID.randomUUID().toString());
        user.setIsActive(true);

        user.setPassword(passwordEncoder.encode(user.getPassword()));


        userRepository.save(user); //saving user entity to db with repository
        cartService.createNew(user); //creating new cart and binding with user which was just created
        return user;
    }

    //@PreAuthorize("hasAnyAuthority('ADMIN', 'USER', 'MODERATOR')")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER', 'MODERATOR')")
    public List<User> findAllUsers(){
        List<User> usersList = userRepository.findAll();

        return usersList;
    }

    /*
    this method return data only when user have this id login and pass or Authority's ADMIN
     */
    @PostAuthorize("returnObject.username == principal.username || hasAuthority('admin:read')")
    //@PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    public User findUserById(Long id) {
        return userRepository.findById(id).orElse(new User());
    }

    public User updateUserById(Long id, User user){
        if(userRepository.findById(id).isPresent()){
            User userUpd = userRepository.findById(id).get();

            userUpd.setUsername(user.getUsername());
            userUpd.setPassword(passwordEncoder.encode(user.getPassword()));
            userUpd.setFirstName(user.getFirstName());
            userUpd.setLastName(user.getLastName());
            userUpd.setRole(user.getRole());

            return userRepository.save(userUpd);
        }else {
            return saveNewUser(user);
        }

    }

    @PreAuthorize("principal.username == userService.findUserById(#id).username")
    public void deleteUserById(Long id){
        userRepository.deleteById(id);
    }

    @PreAuthorize("principal.username == #username")
    public void deleteUserByUsername(String username){
        User user = (User) loadUserByUsername(username);
        System.err.println(user);
        deleteUserById(user.getId());
    }

    /*
    return a user by his username or else return UsernameNotFoundException exception
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findUserByUsername(username).orElseThrow(()
                -> new UsernameNotFoundException("user not found with this username " + username));
    }

    /*
    processing given Long id and MultipartFile for add to existed user an image
     */
    public void uploadImage(Long id, MultipartFile multipartFile) throws IOException{
        User user = userRepository.findById(id).orElse(null);
        user.setImage(multipartFile.getBytes());
        userRepository.save(user);
    }

    public List<User> findAllUsersByFirstName(String firstName, Pageable pageable){
        return userRepository.findAllByFirstName(firstName, pageable).stream().toList();
    }

    /*
    method for retrieve image from userRepository without checks for anything
     */
    public ResponseEntity<byte[]> getImageFromUser(Long id){

        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(userRepository.findById(id).get().getImage());
    }


    public boolean activateAccount(String token) {
        // Find the user by activation token.
        User user = userRepository.findUserByActivationToken(token).get();

        if(user.getActivationToken() != null){
            user.setActivationToken(null);
            userRepository.save(user);
            // Activation successful
            return true;
        }
        return false; // Invalid or expired token
    }

    //@PreAuthorize("principal.username == userService.findUserById(#userId).username")
    @PostAuthorize("returnObject.username == principal.username")
    public User changePasswordByUserId(Long userId, String password){
        User user = userRepository.findById(userId).orElse(null);
        String resultPass = password.replace("\r\n", "");

        if(user == null){
            throw new IllegalStateException("this userID: "+ userId + " don't belong to any one");
        }

        user.setPassword(passwordEncoder.encode(resultPass));

        if(passwordEncoder.matches(resultPass, user.getPassword())){
            System.err.println("this: " + password + " is correct");
            return userRepository.save(user);
        } else {
            throw new IllegalStateException("passwords don't equals you cant log in");
        }
    }

}
