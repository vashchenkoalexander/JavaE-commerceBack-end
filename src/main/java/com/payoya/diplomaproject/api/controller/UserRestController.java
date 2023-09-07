package com.payoya.diplomaproject.api.controller;

import com.payoya.diplomaproject.api.entity.User;
import com.payoya.diplomaproject.api.service.UserService;
import jakarta.validation.Valid;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/user")
public class UserRestController {

    private UserService userService;

    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/new")
    public ResponseEntity<User> createNewUser(@RequestBody @Valid User user){
        return new ResponseEntity<>(userService.createNewUser(user), HttpStatusCode.valueOf(201));
    }

    @GetMapping("/all")
    public ResponseEntity<Iterable<User>> findAllUsers(){
        return new ResponseEntity<>(userService.findAllUsers(), HttpStatusCode.valueOf(200));
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> findUserById(@PathVariable Long id){
        return new ResponseEntity<>(userService.findUserById(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody @Valid User user){
        return new ResponseEntity<>(userService.updateUserById(id, user), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUserById(@PathVariable Long id){
        userService.deleteUserById(id);
    }

//    @GetMapping("/123")
//    public List<User> findAllUsersByFirst(@PathVariable String firstName, @PageableDefault(page = 0, size = 5) Pageable pageable){
//        return userService.findAllUsersByFirstName(firstName, pageable);
//    }

    @GetMapping("/paged")
    public List<User> findAllUsersByFirstName(@PageableDefault(page = 0, size = 20) Pageable pageable){
        return userService.findAllUsersByFirstName(pageable);
    }


}
