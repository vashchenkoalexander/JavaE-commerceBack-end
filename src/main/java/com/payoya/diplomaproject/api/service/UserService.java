package com.payoya.diplomaproject.api.service;

import com.payoya.diplomaproject.api.entity.User;
import com.payoya.diplomaproject.api.enums.Role;
import com.payoya.diplomaproject.api.exceptions.UsernameExistException;
import com.payoya.diplomaproject.api.repository.IUserRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
import java.util.Date;
import java.util.List;

@Service
public class UserService implements UserDetailsService {

    private final IUserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public UserService(IUserRepository userRepository, @Lazy PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
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

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER', 'MODERATOR')")
    public List<User> findAllUsers(){
        List<User> usersList = userRepository.findAll();

        return usersList;
    }

    /*
    this method return data only when user have this id login and pass or Authority's ADMIN
     */
    @PostAuthorize("returnObject.username == principal.username || hasAuthority('ADMIN')")
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
                -> new UsernameNotFoundException("user not found with this username" + username));
    }

    /*
    processing given Long id and MultipartFile multipartFile for add to existed user an image
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

}
