package com.tensquare.friend.dao;

import com.tensquare.friend.pojo.Friend;
import com.tensquare.friend.pojo.NoFriend;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author: 肖德子裕
 * @date: 2018/12/10 20:09
 * @description:
 */
public interface NoFriendDao extends JpaRepository<NoFriend,String> {
    /**
     * 查询朋友是否已经存在非好友列表（不能重复点不喜欢按钮）
     * @param userid
     * @param friendid
     * @return
     */
    public NoFriend findByUseridAndFriendid(String userid, String friendid);
}
