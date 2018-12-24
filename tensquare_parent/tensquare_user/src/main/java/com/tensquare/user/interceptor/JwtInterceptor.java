package com.tensquare.user.interceptor;

import io.jsonwebtoken.Claims;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import util.JwtUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author: 肖德子裕
 * @date: 2018/12/03 19:11
 * @description: 登录拦截器
 * 拦截器对请求头中的token进行解析
 */
@Component
public class JwtInterceptor implements HandlerInterceptor {
    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
        //添加请求头信息，确保是自己家的token
        String header = request.getHeader("Authorization");
        //无论如何都放行
        //如果头不为空
        if (StringUtils.isNotBlank(header)){
            //如果头包含该字符串
            if (header.startsWith("Bearer ")){
                //获取token
                String token = header.substring(7);
                try{
                    //解析
                    Claims claims = jwtUtil.parseJWT(token);
                    //获取角色
                    String roles = (String) claims.get("roles");
                    //如果角色不为空并且为admin
                    if (roles!=null && roles.equals("admin")){
                        request.setAttribute("claims_admin",token);
                    }
                    //如果角色不为空并且为user
                    if (roles!=null && roles.equals("user")){
                        request.setAttribute("claims_user",token);
                    }
                }catch (Exception e){
                    //这里防止它过期报错
                    throw new RuntimeException("令牌有误");
                }
            }
        }
        return true;
    }
}
