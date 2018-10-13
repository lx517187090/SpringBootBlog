package com.example.springbootblog.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

 /**
 * Blog控制器
 */
@RestController
@RequestMapping("/blogs")
public class BlogController {

    @GetMapping
    public String listBlogs(@RequestParam(value = "order", required = false, defaultValue = "new") String order
            ,@RequestParam(value = "keyword",required = false ,defaultValue = "") String keyword){
        System.out.println(" order :" + order + "   keyword : " + keyword);
        return "redirect:/index?order=+" + order + "&keyword=" + keyword;
    }


}
