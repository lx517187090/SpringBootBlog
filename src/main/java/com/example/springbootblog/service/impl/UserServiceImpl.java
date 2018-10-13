package com.example.springbootblog.service.impl;

import com.example.springbootblog.dao.UserDao;
import com.example.springbootblog.entity.User;
import com.example.springbootblog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;


    @Override
    @Transactional
    public User saveOrUpdate(User user) {
        return userDao.save(user);
    }

    @Override
    @Transactional
    public User registerUser(User user) {
        return userDao.save(user);
    }

    @Override
    @Transactional
    public void removeUser(Long id) {
        userDao.deleteById(id);
    }

    @Override
    public List<User> findAll() {
        return userDao.findAll();
    }

    @Override
    public User findUserById(Long id) {
        return userDao.getOne(id);
    }

    @Override
    public Page<User> listUserByNameLike(String name, Pageable pageable) {
        name = "%" + name + "name";
        return userDao.findByNameLike(name, pageable);
    }
}
