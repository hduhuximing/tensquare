package com.tensquare.user.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * 安全配置类
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //authorizeRequests：全注解安全机制的开端，表示开始说明需要的权限
        //说明权限：第一部分为拦截的路径，第二部分为访问该路径需要的权限
        //antMatchers：表示拦截的路径；permitAll：所有权限都能访问
        //anyRequest：任何的请求；authenticated：认证后才能访问
        //and().csrf().disable()：固定写法，表示csrf拦截失效，即不能通过其他方式拦截而造成攻击
        http
                .authorizeRequests()
                .antMatchers("/**").permitAll()
                .anyRequest().authenticated()
                .and().csrf().disable();
    }
}
