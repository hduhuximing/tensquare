package com.tensquare.web.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: 肖德子裕
 * @date: 2018/12/11 21:58
 * @description: Zuul(网关)过滤器
 */
public class WebFilter extends ZuulFilter {
    /**
     * 过滤类型，pre之前过滤，post之后过滤
     * @return
     */
    @Override
    public String filterType() {
        return "pre";
    }

    /**
     * 存在多个过滤器时的执行顺序，数值越小先执行
     * @return
     */
    @Override
    public int filterOrder() {
        return 0;
    }

    /**
     * 当前过滤器是否开启，true开启
     * @return
     */
    @Override
    public boolean shouldFilter() {
        return true;
    }

    /**
     * 该过滤器执行的操作：防止丢失头信息，通过转发hearder
     * 如果想让它不再继续执行，需要执行currentContext.setSendZuulResponse(false);
     * @return 返回任意类型都会继续执行
     * @throws ZuulException
     */
    @Override
    public Object run() throws ZuulException {
        //获取request上下文
        RequestContext currentContext = RequestContext.getCurrentContext();
        //获取request
        HttpServletRequest request = currentContext.getRequest();
        //获取头信息
        String header = request.getHeader("Authorization");
        //判断是否有头信息
        if (StringUtils.isNotBlank(header)){
            //把头信息继续向下传递
            currentContext.addZuulRequestHeader("Authorization",header);
        }
        return null;
    }
}
