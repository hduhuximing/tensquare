package com.tensquare.friend.controller;

import com.tensquare.friend.client.UserClient;
import com.tensquare.friend.service.FriendService;
import entity.Result;
import entity.StatusCode;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: 肖德子裕
 * @date: 2018/12/09 20:28
 * @description:
 */
@RestController
@RequestMapping("/friend")
public class FriendController {
    @Autowired
    private HttpServletRequest request;

    @Autowired
    private FriendService friendService;

    @Autowired
    private UserClient userClient;

    /**
     * 添加好友
     *
     * @param friendid 用户ID
     * @param type     1：喜欢（添加到好友）；2：不喜欢（添加到非好友）
     * @return
     */
    @RequestMapping(value = "/like/{friendid}/{type}", method = RequestMethod.PUT)
    public Result addFriend(@PathVariable String friendid, @PathVariable String type) {
        //验证用户是否登录
        Claims claims = (Claims) request.getAttribute("claims_user");
        //如果获取的用户为空
        if (claims == null) {
            return new Result(false, StatusCode.LOGINERROR, "权限不足");
        }
        //获取当前登录用户ID
        String userid = claims.getId();
        //判断是喜欢还是不喜欢
        if (type != null) {
            if ("1".equals(type)) {
                //添加好友
                int flag = friendService.addFriend(userid, friendid);
                if (flag == 0) {
                    return new Result(false, StatusCode.ERROR, "不能重复添加好友");
                }
                if (flag == 1) {
                    //用户关注数和好友粉丝数加一
                    userClient.updatefanscountandfollowcount(userid, friendid, 1);
                    return new Result(true, StatusCode.OK, "添加成功");
                }
            } else if ("2".equals(type)) {
                //添加非好友
                int flag = friendService.addNoFriend(userid, friendid);
                if (flag == 0) {
                    return new Result(false, StatusCode.ERROR, "不能重复添加非好友");
                }
                if (flag == 1) {
                    return new Result(true, StatusCode.OK, "添加成功");
                }
            }
            return new Result(false, StatusCode.ERROR, "参数异常");
        } else {
            return new Result(false, StatusCode.ERROR, "参数异常");
        }
    }

    /**
     * 根据好友ID删除好友
     *
     * @param friendid
     * @return
     */
    @RequestMapping(value = "/{friendid}", method = RequestMethod.DELETE)
    public Result deleteFriend(@PathVariable String friendid) {
        //验证用户是否登录
        Claims claims = (Claims) request.getAttribute("claims_user");
        //如果获取的用户为空
        if (claims == null) {
            return new Result(false, StatusCode.LOGINERROR, "权限不足");
        }
        //获取当前登录用户ID
        String userid = claims.getId();
        //删除好友
        friendService.deleteFriend(userid, friendid);
        //用户关注数和好友粉丝数减一
        userClient.updatefanscountandfollowcount(userid, friendid, -1);
        return new Result(true, StatusCode.OK, "删除成功");
    }
}
