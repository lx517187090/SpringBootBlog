package com.example.springbootblog.service;

import com.example.springbootblog.entity.Blog;
import com.example.springbootblog.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BlogService {

    /**
     * 保存博客
     * @param blog
     * @return
     */
    Blog saveBlog(Blog blog);

    /**
     * 删除
     * @param id
     */
    void removeBlog(Long id);

    /**
     * 根据Id查询
     * @param id
     * @return
     */
    Blog getBlogById(Long id);

    /**
     * 根据用户名分页查询  最新
     *
     * @param user
     * @param title
     * @param pageable
     * @return
     */
    Page<Blog> listBlogsByTitleVote(User user, String title, Pageable pageable);

    /**
     * 根据用户名分页查询  最热
     *
     * @param user
     * @param title
     * @param pageable
     * @return
     */
    Page<Blog> listBlogsByTitleVoteAndSort(User user, String title, Pageable pageable);

    /**
     * 阅读量增加
     *
     * @param id
     */
    void readingIncIncrease(Long id);
}
