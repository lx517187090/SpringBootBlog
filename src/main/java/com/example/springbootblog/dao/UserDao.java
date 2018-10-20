package com.example.springbootblog.dao;


import com.example.springbootblog.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

/**
 * ES接口
 */
public interface UserDao extends JpaRepository<User, Long> {
    /**
     * 更具用户名分页查询用户
     * @param name
     * @param pageable
     * @return
     */
    Page<User> findByNameLike(String name, Pageable pageable);

    /**
     * 根据用户账号查询用户
     * @param userName
     * @return
     */
    User findByUsername(String username);
}
