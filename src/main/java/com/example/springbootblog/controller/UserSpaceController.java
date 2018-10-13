package com.example.springbootblog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 用户主页控制器
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
            System.out.println("selflink " + "redirect:/u/"+ username + "/blogs?category=" + category);
            return  "/userspace/u";
        }else if(keyword != null && keyword.isEmpty() == false){
            System.out.println("keyword :" + keyword);
            System.out.println("selflink " + "redirect:/u/"+ username + "/blogs?keywprd=" + keyword);
            return  "/userspace/u";
        }
        System.out.println("order :" + order);
        System.out.println("selflink " + "redirect:/u/"+ username + "/blogs?order=" + order);
        return  "/userspace/u";
    }

    @GetMapping("/{username}/blogs/{id}")
    public String listBlogsByOrder(@PathVariable("id")Long id){
        System.out.println("blogs : " + id);
        return "/userspace/blog";
    }


    @GetMapping("/{username}/blogs/edit")
    public String editBlog(){
        return "/userspace/blogedit";
    }
}
