package com.tensquare.friend.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author: 肖德子裕
 * @date: 2018/12/11 14:20
 * @description: 服务之间的调用
 * Component：其实这个注解在这里并没有什么作用，由于在注入这个类时会报红，
 * 所以通过这个注解将红线消除
 */
@Component
@FeignClient("tensquare-user")
public interface UserClient {
    /**
     * 修改用户关注数和好友粉丝数
     * @param userid 用户ID
     * @param friendid 好友ID
     * @param x 增加的数量
     */
    @RequestMapping(value = "/user/{userid}/{friendid}/{x}",method = RequestMethod.PUT)
    public void updatefanscountandfollowcount(@PathVariable("userid") String userid,
                                              @PathVariable("friendid") String friendid, @PathVariable("x") int x);
}
