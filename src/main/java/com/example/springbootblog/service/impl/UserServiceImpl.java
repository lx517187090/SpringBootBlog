package com.example.springbootblog.service.impl;

import com.example.springbootblog.dao.UserDao;
import com.example.springbootblog.entity.Authoirty;
import com.example.springbootblog.entity.User;
import com.example.springbootblog.service.UserService;
import com.example.springbootblog.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import sun.plugin.util.UserProfile;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service("userService")
public class UserServiceImpl implements UserService, UserDetailsService {

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
    public User getUserById(Long id) {
        return userDao.getOne(id);
    }

    @Override
    public Page<User> listUsersByNameLike(String name, Pageable pageable) {
        name = "%" + name + "name";
        return userDao.findByNameLike(name, pageable);
    }

    @Override
    public User findUserByUsername(String username) {
        return userDao.findByUsername(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDao.findByUsername(username);
        System.out.println("User : "+user);
        if(user==null){
            System.out.println("User not found");
            throw new UsernameNotFoundException("Username not found");
        }
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        // 加密
        String encodedPassword = passwordEncoder.encode(user.getPassword().trim());
        user.setPassword(encodedPassword);
        return new User(user.getName(),user.getUsername(),user.getPassword(),user.getEmail(),user.getAvatar(),getGrantedAuthorities(user));
    }

    private List<Authoirty> getGrantedAuthorities(User user){
        List<Authoirty> authorities = new ArrayList<Authoirty>();

        for(GrantedAuthority authoirty : user.getAuthorities()){
            System.out.println("authoirty : "+authoirty);
           authorities.add(new Authoirty("ROLE_" + authoirty.getAuthority()));
        }
        System.out.print("authorities :"+authorities);
        return authorities;
    }
}
