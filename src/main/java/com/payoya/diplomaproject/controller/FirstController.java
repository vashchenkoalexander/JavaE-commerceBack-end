package com.payoya.diplomaproject.controller;

import com.payoya.diplomaproject.Service.UserTestService;
import com.payoya.diplomaproject.entity.UserTest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.concurrent.atomic.AtomicLong;

@Controller("/")
public class FirstController {

    private AtomicLong atomicLong = new AtomicLong(0);

    private UserTestService userTestService;

    public FirstController(UserTestService userTestService) {
        this.userTestService = userTestService;
    }

//    @GetMapping("home")
//    public String homePage(@RequestParam(value = "name", defaultValue = "test") String name, Model model){
//        model.addAttribute("welcome", "Hello " + name);
//        return "home";
//    }

    @GetMapping("greet")
    public String greetings(@RequestParam(value = "name", required = false, defaultValue = "first") String value, Model model){

        model.addAttribute("count", "Count: " + atomicLong.getAndIncrement());

        return "greetings";

    }

    @GetMapping("/")
    public String test(Model model){
        model.addAttribute("allusertestlist", userTestService.findAll());
        return "index";
    }

    @GetMapping("reg")
    public String addUserTest(Model model){
        UserTest userTest = new UserTest();
        model.addAttribute("userTest", userTest);
        return "Registration";
    }

    @PostMapping("save")
    public String saveUserTest(@ModelAttribute("test_user") UserTest user){
        userTestService.save(user);
        return "redirect:/";
    }






}
