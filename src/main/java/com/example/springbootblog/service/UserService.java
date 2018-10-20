package com.example.springbootblog.service;

import com.example.springbootblog.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {

    User saveOrUpdate(User user);

    User registerUser(User user);

    void removeUser(Long id);

    List<User> findAll();

    User findUserById(Long id);

    Page<User> listUserByNameLike(String name, Pageable pageable);


}
