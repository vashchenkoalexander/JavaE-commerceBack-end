package com.payoya.diplomaproject.api.controller;

import com.payoya.diplomaproject.api.entity.User;
import com.payoya.diplomaproject.api.service.UserService;
import jakarta.validation.Valid;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
        return new ResponseEntity<>(userService.saveNewUser(user), HttpStatusCode.valueOf(201));
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

    @DeleteMapping("/delete")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUserByUsername(@RequestParam String username){
        userService.deleteUserByUsername(username);
    }

    /*
    this method return all Users with FirstName who was presented like RequestParam in api also you can change Pageable
     with fields page and size also in RequestParam property
     */
    @GetMapping("/paged")
    public List<User> findAllUsersByFirstName(@RequestParam String firstName, @PageableDefault(page = 0, size = 20) Pageable pageable){
        return userService.findAllUsersByFirstName(firstName, pageable);
    }

    /*
    method for upload image into user entity for created user
     */
    @PatchMapping("image/{id}")
    public void uploadImage(@PathVariable Long id, @RequestParam MultipartFile file) throws IOException {
        userService.uploadImage(id, file);
    }

    /*
    GetMapping for retrieve image from userService
     */
    @GetMapping("userimage/{id}")
    public ResponseEntity<byte[]> getImageFromUser(@PathVariable Long id){
        return userService.getImageFromUser(id);
    }

    @GetMapping("/activate")
    public boolean ActivateAccount(@RequestParam String token){
        return userService.activateAccount(token);
    }

}
