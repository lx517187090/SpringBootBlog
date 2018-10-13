package com.example.springbootblog.service;

import com.example.springbootblog.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {

    public User saveOrUpdate(User user);

    public User registerUser(User user);

    public void removeUser(Long id);

    public List<User> findAll();

    public User findUserById(Long id);

    public Page<User> listUserByNameLike(String name, Pageable pageable);


}
