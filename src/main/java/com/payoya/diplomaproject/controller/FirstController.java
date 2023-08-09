package com.payoya.diplomaproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAdder;

@Controller("/")
public class FirstController {

    private static AtomicLong atomicLong = new AtomicLong(0);

    @GetMapping("home")
    public String homePage(@RequestParam(value = "name", defaultValue = "test") String name, Model model){
        model.addAttribute("name", name);
        return "homePage";
    }

    @GetMapping("greet")
    public String greetings(@RequestParam(value = "name", required = false, defaultValue = "first") String value, Model model){

        model.addAttribute("count", atomicLong.getAndIncrement());


        return null;


    }




}
