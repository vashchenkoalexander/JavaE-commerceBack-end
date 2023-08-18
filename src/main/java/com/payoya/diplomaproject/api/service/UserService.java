package com.payoya.diplomaproject.api.service;

import com.payoya.diplomaproject.api.entity.User;
import com.payoya.diplomaproject.api.exceptions.UsernameExistException;
import com.payoya.diplomaproject.api.repository.IUserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    private IUserRepository userRepository;

    private PasswordEncoder passwordEncoder;

    public UserService(IUserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    //TODO create a check for uniq login
    public User createNewUser (User user) throws RuntimeException {

        if(userRepository.findUserByUsername(user.getUsername()).isPresent()){
            throw new UsernameExistException("user with this login is exist: " + user.getUsername());
        }

        if(user.getDateOfCreate() == null){
            user.setDateOfCreate();
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public Iterable<User> findAllUsers(){
        return userRepository.findAll();
    }

    public User findUserById(Long id) {
        return userRepository.findById(id).orElse(new User());
    }

    public User updateUserById(Long id, User user){
        if(userRepository.findById(id).isPresent()){
            User userUpd = userRepository.findById(id).get();

            userUpd.setUsername(user.getUsername());
            userUpd.setPassword(user.getPassword());
            userUpd.setFirstName(user.getFirstName());
            userUpd.setLastName(user.getLastName());

            return userRepository.save(userUpd);
        }else {
            return createNewUser(user);
        }

    }

    public void deleteUserById(Long id){
        userRepository.deleteById(id);
    }

    /*
    return a user by his username or else return UsernameNotFoundException exception
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findUserByUsername(username).orElseThrow(()
                -> new UsernameNotFoundException("user now found with this username" + username));
    }
}
