package com.payoya.diplomaproject.Service;

import com.payoya.diplomaproject.entity.UserTest;
import com.payoya.diplomaproject.repository.IUserTestRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserTestService {

    private IUserTestRepository userTestRepository;


    public UserTestService(IUserTestRepository userTestRepository) {
        this.userTestRepository = userTestRepository;
    }

    public List<UserTest> findAll(){
        return userTestRepository.findAll();
    }

    public UserTest save(UserTest user){
        return userTestRepository.save(user);
    }

    public Optional<UserTest> findUserTest(Long id) {
        return userTestRepository.findById(id);
    }
}
