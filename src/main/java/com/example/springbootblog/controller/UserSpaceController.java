package com.example.springbootblog.controller;

import com.example.springbootblog.entity.Blog;
import com.example.springbootblog.entity.User;
import com.example.springbootblog.service.BlogService;
import com.example.springbootblog.service.UserService;
import com.example.springbootblog.util.ConstraintViolationExceptionHandler;
import com.example.springbootblog.vo.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.ConstraintViolationException;
import java.util.List;

/**
 * 用户主页控制器
 */
@Controller
@RequestMapping("/u")
public class UserSpaceController {

    @Autowired
    private UserService userService;

    @Autowired
    private BlogService blogService;

    @Value("${file.server.url}")
    private String fileServerUrl;

    @GetMapping("/{username}")
    public String userspace(@PathVariable("username")String username, Model model){
        User user = userService.findUserByUsername(username);
        model.addAttribute("user",user);
        return "redirect:/u"+ username +"/blogs";
    }

    /**
     * 获取个人页面
     * @param username
     * @param model
     * @return
     */
    @GetMapping("/{username}/profile")
    @PreAuthorize("authentication.name.equals(#username)")
    public ModelAndView profile(@PathVariable("username")String username, Model model){
        User user = userService.findUserByUsername(username);
        model.addAttribute("user",user);
        model.addAttribute("fileServerUrl", fileServerUrl);
        return new ModelAndView("userspace/profile","userModel",model);
    }

    /**
     * 保存用户数据
     * @param username
     * @param user
     * @return
     */
    @PostMapping("/{username}/profile")
    @PreAuthorize("authentication.name.equals(#username)")
    public String saveProfile(@PathVariable("username")String username,User user){
        User originalUser = userService.getUserById(user.getId());
        originalUser.setEmail(user.getEmail());
        originalUser.setName(user.getName());
        String password = originalUser.getPassword();
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodePassword = encoder.encode(user.getPassword());
        boolean matches = encoder.matches(password, encodePassword);
        if(!matches){
            originalUser.setPassword(user.getPassword());
        }
        userService.saveOrUpdate(user);
        return "redirect:/u/" + username + "/profile";
    }

    /**
     * 获取用户头像的页面
     * @param username
     * @param model
     * @return
     */
    @GetMapping("/{username}/avatar")
    @PreAuthorize("authentication.name.equals(#username)")
    public ModelAndView avatar(@PathVariable("username")String username, Model model){
        User user = userService.findUserByUsername(username);
        model.addAttribute("user",user);
        return new ModelAndView("userspace/avatar","userModel",model);
    }

    /**
     * 保存用户头像
     * @param username
     * @param user
     * @return
     */
    @PostMapping("/{username}/avatar")
    @PreAuthorize("authentication.name.equals(#username)")
    public ResponseEntity<Response> saveAvatar(@PathVariable("username")String username,@RequestBody User user){
        String avatarUrl = user.getAvatar();
        User originalUser = userService.getUserById(user.getId());
        originalUser.setAvatar(avatarUrl);
        userService.saveOrUpdate(originalUser);
        return ResponseEntity.ok().body(new Response(true,"处理成功", avatarUrl));
    }

    /**
     * 获取用户主页
     * @param username
     * @param order
     * @param categoryId
     * @param keyword
     * @param async
     * @param pageIndex
     * @param pageSize
     * @param model
     * @return
     */
    @GetMapping("/{username}/blogs")
    public String listOrderByOrders(@PathVariable("username") String username
            ,@RequestParam(value = "order",required = false ,defaultValue = "new") String order
            ,@RequestParam(value = "category",required = false) Long categoryId
            ,@RequestParam(value = "keyword",required = false) String keyword
            ,@RequestParam(value = "async",required = false) boolean async
            ,@RequestParam(value = "pageIndex",required = false) int pageIndex
            ,@RequestParam(value = "pageSize",required = false) int pageSize,Model model
            ){
        User user = userService.findUserByUsername(username);
        Page<Blog> page = null;

        if(categoryId != null && categoryId > 0){//分类查询

        }else if(order.equals("hot")){//最热查询
            Sort sort = new Sort(Sort.Direction.DESC, "readSize", "commentSize", "voteSize");
            Pageable pageable = PageRequest.of(pageIndex, pageSize, sort);
            page = blogService.listBlogsByTitleVoteAndSort(user, keyword, pageable);
        }else if(order.equals("new")){//最新查询
            Pageable pageable = PageRequest.of(pageIndex, pageSize);
            page = blogService.listBlogsByTitleVote(user, keyword, pageable);
        }
        List<Blog> list = page.getContent();
        model.addAttribute("user",user);
        model.addAttribute("order", order);
        model.addAttribute("catelogId", categoryId);
        model.addAttribute("keyword", keyword);
        model.addAttribute("page", page);
        model.addAttribute("blogList", list);
        return  (async==true?"userspace/u :: #mainContainerReplace": "/userspace/u");
    }


    /**
     * 获取博客展示页面
     */
    @GetMapping("/username/blogs/{id}")
    public String getBlogById(@PathVariable("username")String username, @PathVariable("id")Long id, Model model){
        User principal = null;
        Blog blog = blogService.getBlogById(id);

        //每次读取都增加一
        blogService.readingIncIncrease(id);
        boolean isBlogOwner = false;

        if(SecurityContextHolder.getContext().getAuthentication() != null && SecurityContextHolder.getContext().getAuthentication().isAuthenticated()
                &&SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString().equals("anonymousUser")){
            principal = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if(principal != null && username.equals(principal.getUsername())){
                isBlogOwner = true;
            }
        }
        model.addAttribute("isBlogOwner", isBlogOwner);
        model.addAttribute("blogModel", blog);
        return "/userspace/blog";
    }


    /**
     *
     * @param id
     * @return
     */
    @GetMapping("/{username}/blogs/{id}")
    public String listBlogsByOrder(@PathVariable("id")Long id){
        System.out.println("blogs : " + id);
        return "/userspace/blog";
    }

    @GetMapping("/{username}/blogs/edit")
    public ModelAndView createBlog(@PathVariable("username")String username, Model model){
        model.addAttribute("blog",new Blog(null,null,null));
        model.addAttribute("fileServerUrl",fileServerUrl);
        return new ModelAndView("/userspace/blogedit","blogModel",model);
    }
    /**
     * 获取博客编辑页面
     * @return
     */
    @GetMapping("/{username}/blogs/edit/{id}")
    public ModelAndView editBlog(@PathVariable("username")String username, @PathVariable("id")Long id,Model model){
        model.addAttribute("blog",blogService.getBlogById(id));
        model.addAttribute("fileServerUrl",fileServerUrl);
        return new ModelAndView("/userspace/blogedit","blogModel",model);
    }

    /**
     * 获取博客编辑页面
     * @return
     */
    @PostMapping("/{username}/blogs/edit")
    public ResponseEntity<Response> saveBlog(@PathVariable("username")String username, @RequestBody Blog blog){
       try{
            if(blog.getId() == null){
                User user = userService.findUserByUsername(username);
                blog.setUser(user);
                blogService.saveBlog(blog);
            }else{
                Blog originalBlog = blogService.getBlogById(blog.getId());
                originalBlog.setTitle(blog.getTitle());
                originalBlog.setContent(blog.getContent());
                originalBlog.setSummary(blog.getSummary());
                blogService.saveBlog(blog);

            }
       }catch (ConstraintViolationException e){
            return ResponseEntity.ok().body(new Response(false,ConstraintViolationExceptionHandler.getMessage(e)));
       }
       String redirectUrl = "/u/" + username + "/blogs/" + blog.getId();
       return ResponseEntity.ok().body(new Response(true,"处理成功", redirectUrl));
    }

    /**
     * 获取博客编辑页面
     * @return
     */
    @DeleteMapping("/{username}/blogs/{id}")
    @PreAuthorize("authentication.name.equals(#username)")
    public ResponseEntity<Response> deleteBlog(@PathVariable("username")String username, @PathVariable Long id){
        try{
            blogService.removeBlog(id);
        }catch (ConstraintViolationException e){
            return ResponseEntity.ok().body(new Response(false,e.getMessage()));
        }
        String redirectUrl = "/u/" + username + "/blogs/";
        return ResponseEntity.ok().body(new Response(true,"处理成功", redirectUrl));
    }

}
