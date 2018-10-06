package com.example.springbootblog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * 安全控制类
 */
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

   /* *//**
     * 自定义配置类
     * @param http
     * @throws Exception
     *//*
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/css/**","/fonts/**","/js/**","/index").permitAll()//都可以访问
                //.anyRequest().authenticated() //其他所有资源都需要认证，登陆后访问
                .antMatchers("/users/**").hasRole("ADMIN")//需要admin角色
                .and()
                .formLogin()//基于form表单登陆验证
                .loginPage("/login").failureUrl("/login-error");//自定义登陆页面
    }

    *//**
     * 认证信息管理
     * @param auth
     * @throws Exception
     *//*
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()//认证信息存储与内存中
            .withUser("lixi").password("123456").roles("ADMIN");
       // auth.eraseCredentials(false);		;
    }
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(4);
    }

   *//* @Bean
    public LoginSuccessHandler loginSuccessHandler(){
        return new LoginSuccessHandler();
    }*/
}
