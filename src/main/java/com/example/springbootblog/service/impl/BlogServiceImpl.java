package com.example.springbootblog.service.impl;

import com.example.springbootblog.dao.BlogDao;
import com.example.springbootblog.entity.Blog;
import com.example.springbootblog.entity.User;
import com.example.springbootblog.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service("blogService")
public class BlogServiceImpl implements BlogService {

    @Autowired
    private BlogDao blogDao;


    @Override
    public Blog saveBlog(Blog blog) {
        return blogDao.save(blog);
    }

    @Override
    public void removeBlog(Long id) {
        blogDao.deleteById(id);
    }

    @Override
    public Blog getBlogById(Long id) {
        return blogDao.getOne(id);
    }

    @Override
    public Page<Blog> listBlogsByTitleVote(User user, String title, Pageable pageable) {
        // 模糊查询
        title = "%" + title + "%";
        Page<Blog> blogs = blogDao.findByUserAndTitleLikeOrderByCreateTimeDesc(user, title, pageable);
        return blogs;
    }

    @Override
    public Page<Blog> listBlogsByTitleVoteAndSort(User user, String title, Pageable pageable) {
        // 模糊查询
        title = "%" + title + "%";
        Page<Blog> blogs = blogDao.findByUserAndTitleLike(user, title, pageable);
        return blogs;
    }


    @Override
    public void readingIncIncrease(Long id) {
        Blog blog = blogDao.getOne(id);
        blog.setReading(blog.getReading()+1);
        blogDao.save(blog);
    }
}
