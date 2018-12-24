package com.tensquare.qa.client;

import com.tensquare.qa.client.impl.BaseClientImpl;
import entity.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author: 肖德子裕
 * @date: 2018/12/07 11:37
 * @description: 服务之间的调用
 * value：需要调用的模块名称，不能有下划线
 * fallback：错误时跳转到
 */
@Component
@FeignClient(value = "tensquare-base",fallback = BaseClientImpl.class)
public interface BaseClient {
    /**
     * 根据ID查询
     * @param labelId 必须和PathVariable的值一致才能找到
     * @return
     */
    @RequestMapping(value = "/label/{labelId}",method = RequestMethod.GET)
    public Result findById(@PathVariable("labelId") String labelId);
}
