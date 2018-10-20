package com.example.springbootblog.service.impl;

import com.example.springbootblog.dao.AuthorityDao;
import com.example.springbootblog.entity.Authoirty;
import com.example.springbootblog.service.AuthorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("authorityService")
public class AuthorityServiceImpl implements AuthorityService {

    @Autowired
    private AuthorityDao authorityDao;


    @Override
    public Authoirty getAuthorityById(Long id) {
        return authorityDao.getOne(id);
    }
}
