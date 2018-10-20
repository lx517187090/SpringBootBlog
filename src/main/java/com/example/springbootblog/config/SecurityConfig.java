package com.example.springbootblog.config;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * 安全控制类
 */
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

   /* *//**
     * 自定义配置类
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/css/**","/fonts/**","/js/**","/index").permitAll();//都可以访问

    }


}
