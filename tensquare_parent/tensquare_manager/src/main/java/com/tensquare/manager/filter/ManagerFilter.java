package com.tensquare.manager.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import util.JwtUtil;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: 肖德子裕
 * @date: 2018/12/11 20:43
 * @description: Zuul(网关)过滤器
 */
@Component
public class ManagerFilter extends ZuulFilter {
    @Autowired
    private JwtUtil jwtUtil;

    /**
     * 过滤类型，pre之前过滤，post之后过滤
     *
     * @return
     */
    @Override
    public String filterType() {
        return "pre";
    }

    /**
     * 存在多个过滤器时的执行顺序，数值越小先执行
     *
     * @return
     */
    @Override
    public int filterOrder() {
        return 0;
    }

    /**
     * 当前过滤器是否开启，true开启
     *
     * @return
     */
    @Override
    public boolean shouldFilter() {
        return true;
    }

    /**
     * 该过滤器执行的操作：后台网关验证权限
     * 如果想让它不再继续执行，需要执行currentContext.setSendZuulResponse(false);
     *
     * @return 返回任意类型都会继续执行
     * @throws ZuulException
     */
    @Override
    public Object run() throws ZuulException {
        System.out.println("经过后台过滤器");
        //获取request上下文
        RequestContext currentContext = RequestContext.getCurrentContext();
        //获取request
        HttpServletRequest request = currentContext.getRequest();

        if ("OPTIONS".equals(request.getMethod())){
            return null;
        }

        if (request.getRequestURI().indexOf("login")>0){
            return null;
        }

        //获取头信息
        String header = request.getHeader("Authorization");
        //判断是否有头信息
        if (StringUtils.isNotBlank(header)) {
            if (header.startsWith("Bearer ")) {
                //获取token
                String token = header.substring(7);
                try {
                    //解析
                    Claims claims = jwtUtil.parseJWT(token);
                    //获取角色
                    String roles = (String) claims.get("roles");
                    //如果角色为admin
                    if ("admin".equals(roles)) {
                        //转发头信息
                        currentContext.addZuulRequestHeader("Authorization", header);
                        //放行
                        return null;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    //终止继续执行
                    currentContext.setSendZuulResponse(false);
                }
            }
        }
        //终止继续执行
        currentContext.setSendZuulResponse(false);
        //403权限不足
        currentContext.setResponseStatusCode(403);
        currentContext.setResponseBody("权限不足");
        currentContext.getResponse().setContentType("text/html;charset=utf-8");
        return null;
    }
}
