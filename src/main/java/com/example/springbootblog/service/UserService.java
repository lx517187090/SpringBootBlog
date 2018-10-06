package com.example.springbootblog.service;

import com.example.springbootblog.dao.UserDao;
import com.example.springbootblog.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("userService")
public class UserService {

    @Autowired
    private UserDao userDao;

    public List<User> findAll() {
        return (List<User>)userDao.findAll();
    }

    public User findUserById(Long id) {
        return userDao.findById(id).get();
    }

    public User findUserByUserName(String userName) {
        return null;
    }

    public User saveOrUpdate(User user) {
        return userDao.save(user);
    }


    public void deleteById(Long id) {
        userDao.deleteById(id);
    }


}
