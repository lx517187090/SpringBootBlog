package com.example.springbootblog.dao;


import com.example.springbootblog.entity.User;
import org.springframework.data.repository.CrudRepository;

/**
 * ES接口
 */
public interface UserDao extends CrudRepository<User, Long> {

}
