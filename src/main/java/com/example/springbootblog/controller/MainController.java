package com.example.springbootblog.controller;

import com.example.springbootblog.entity.User;
import com.example.springbootblog.service.AuthorityService;
import com.example.springbootblog.service.UserService;
import com.example.springbootblog.util.ConstraintViolationExceptionHandler;
import com.example.springbootblog.vo.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

/**
 * 主页控制器
 */
@Controller
public class MainController {

    private static final Long ROLE_USER_AUTHORITY_ID = 2L;
    @Autowired
    private UserService userService;
    @Autowired
    private AuthorityService authorityService;
    @GetMapping("/")
    public String root(){
        return "redirect:/index";
    }

    @GetMapping("/index")
    public String index(){
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
    public String register(User user){
        List authorityList = new ArrayList();
        authorityList.add(authorityService.getAuthorityById(ROLE_USER_AUTHORITY_ID));
        user.setAuthorities(authorityList);
        userService.registerUser(user);

        return "redirect:/login";
    }
}
