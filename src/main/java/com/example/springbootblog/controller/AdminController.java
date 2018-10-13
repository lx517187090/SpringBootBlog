package com.example.springbootblog.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * 用户admin控制器
 */
@RestController
@RequestMapping("/admins")
public class AdminController {

    @GetMapping
    public ModelAndView listUsers(Model model){
        return new ModelAndView("admin/index","menuList", model);
    }

}
