package com.payoya.diplomaproject.api.service;

import com.payoya.diplomaproject.api.entity.User;
import com.payoya.diplomaproject.api.repository.IUserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private IUserRepository userRepository;

    public UserService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createNewUser(User user){
        return userRepository.save(user);
    }

    public Iterable<User> findAllUsers(){
        return userRepository.findAll();
    }

    public User findUserById(Long id) {
        return userRepository.findById(id).orElse(new User());
    }

    public User updateUserById(Long id, User user){
        //if(userRepository.findById(id).isPresent()){
            User userUpd = userRepository.findById(id).get();

            userUpd.setUsername(user.getUsername());
            userUpd.setPassword(user.getPassword());
            userUpd.setFirstName(user.getFirstName());
            userUpd.setLastName(user.getLastName());
            return userRepository.save(userUpd);
//        } else {
//            return createNewUser(user);
//        }
    }

    public void deleteUserById(Long id){
        userRepository.deleteById(id);
    }



}
