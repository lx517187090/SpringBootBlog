package com.example.springbootblog.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/helloWold")
public class HelloWorld {

    @GetMapping("/hello")
    public String helloWorld(){
        return "hello";
    }
}
