package com.example.springbootblog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 主页控制器
 */
@Controller
@RequestMapping("/u")
public class UserSpaceController {

    @GetMapping("/{username}")
    public String userspace(@PathVariable("username")String username){
        System.out.println("userName : " + username);
        return "userspace/u";
    }

    @GetMapping("/{username}/blogs")
    public String listOrderByOrders(@PathVariable("username") String username
            ,@RequestParam(value = "order",required = false ,defaultValue = "new") String order
            ,@RequestParam(value = "category",required = false) String category
            ,@RequestParam(value = "keyword",required = false) String keyword){
        if(null != category){
            System.out.println("category");
        }
        return "index";
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }


    @GetMapping("/login-error")
    public String LoginError(Model model){
        model.addAttribute("loginError", true);
        model.addAttribute("errorMsg", "登陆失败，用户名或密码错误");
        return "login";
    }

    @GetMapping("/register")
    public String register(){
        return "register";
    }
}
