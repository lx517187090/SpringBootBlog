package com.example.springbootblog.controller;

import com.example.springbootblog.entity.User;
import com.example.springbootblog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/getUserList")
    public ModelAndView getUserList(Model model){
        model.addAttribute("userList",userService.findAll());
        model.addAttribute("title","用户");
        return new ModelAndView("user/list","userModel",model);
    }

    @GetMapping("/getUserById/{id}")
    public ModelAndView getUserById(@PathVariable("id") Long id, Model model){
        model.addAttribute("user",userService.findUserById(id));
        model.addAttribute("title","用户");
        return new ModelAndView("user/view","userModel",model);
    }

    @GetMapping("/form")
    public ModelAndView create(Model model){
        model.addAttribute("user",new User(null, null));
        model.addAttribute("title","创建用户");
        return new ModelAndView("user/form","userModel",model);
    }

    @PostMapping("/submit")
    public ModelAndView submit(User user, Model model){
        userService.saveOrUpdate(user);
        return new ModelAndView("redirect:/users/getUserList");
    }
}
