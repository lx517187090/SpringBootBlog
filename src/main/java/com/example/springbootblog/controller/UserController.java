package com.example.springbootblog.controller;

import com.example.springbootblog.entity.User;
import com.example.springbootblog.service.AuthorityService;
import com.example.springbootblog.service.UserService;
import com.example.springbootblog.util.ConstraintViolationExceptionHandler;
import com.example.springbootblog.vo.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private AuthorityService authorityService;
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
        model.addAttribute("user",new User(null, null,null,null));
        model.addAttribute("title","创建用户");
        return new ModelAndView("user/form","userModel",model);
    }

    @PostMapping("/submit")
    public ModelAndView submit(User user, Model model){
        userService.saveOrUpdate(user);
        return new ModelAndView("redirect:/users/getUserList");
    }

    @PostMapping
    public ResponseEntity<Response> saveOrUpdateUser(User user, Long id){

        List authorityList = new ArrayList();
        authorityList.add(authorityService.getAuthorityById(id));
        user.setAuthorities(authorityList);
        try{
            userService.saveOrUpdate(user);
        }catch (ConstraintViolationException e){
            return ResponseEntity.ok().body(new Response(false,ConstraintViolationExceptionHandler.getMessage(e)));
        }
        return ResponseEntity.ok().body(new Response(true, "处理成功", user));
    }
}
