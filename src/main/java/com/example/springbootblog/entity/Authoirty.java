package com.example.springbootblog.entity;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

@Entity
public class Authoirty implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;
    @Override
    public String getAuthority() {
        return this.name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
}
