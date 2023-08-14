package com.payoya.diplomaproject.controller;

import com.payoya.diplomaproject.Service.UserTestService;
import com.payoya.diplomaproject.entity.UserTest;
import jakarta.validation.Valid;
import org.springframework.boot.autoconfigure.integration.IntegrationProperties;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.atomic.AtomicLong;

@Controller("/")
public class FirstController {

    private AtomicLong atomicLong = new AtomicLong(0);

    private UserTestService userTestService;

    public FirstController(UserTestService userTestService) {
        this.userTestService = userTestService;
    }

    @GetMapping("greet")
    public String greetings(@RequestParam(value = "name", required = false, defaultValue = "first") String value, Model model){

        model.addAttribute("count", "Count: " + atomicLong.getAndIncrement());

        return "side/greetings";

    }

    @GetMapping("home")
    public String test(Model model){
        model.addAttribute("allusertestlist", userTestService.findAll());
        return "homepage";
    }

    @GetMapping("reg")
    public String addUserTest(Model model){
        UserTest userTest = new UserTest();
        model.addAttribute("userTest", userTest);
        return "Registration";
    }

    @PostMapping("save")
    public String saveUserTest(@Valid UserTest user, Errors errors){ //
        if(errors.hasErrors()){
            return "Registration";
        }
        userTestService.save(user);
        return "redirect:/";
    }

    @GetMapping("UpdatePage{id}")
    public String getUpdatePage(@PathVariable Long id, Model model){
        model.addAttribute("updatebleUserTest", userTestService.findUserTest(id));
        return "UpdatePage";
    }

    @PutMapping("update")
    public String updateUserTest(@Valid UserTest user, Errors errors){
        if(errors.hasErrors()){
            return "UpdatePage";
        }
        return "redirect:/";
    }

    //TODO form for update and delete pages and redirections with good interact





}
