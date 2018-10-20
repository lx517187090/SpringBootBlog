package com.example.springbootblog.dao;

import com.example.springbootblog.entity.Authoirty;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityDao extends JpaRepository<Authoirty, Long> {
}
