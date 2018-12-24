package com.tensquare.friend.dao;

import com.tensquare.friend.pojo.Friend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * @author: 肖德子裕
 * @date: 2018/12/10 19:19
 * @description:
 */
public interface FriendDao extends JpaRepository<Friend,String> {
    /**
     * 查询朋友是否已经存在
     * @param userid
     * @param friendid
     * @return
     */
    public Friend findByUseridAndFriendid(String userid,String friendid);

    /**
     * 如果双方都互相喜欢，就把islike都改为1
     * @param islike
     * @param userid
     * @param friendid
     */
    @Modifying
    @Query(value = "update tb_friend set islike=? where userid=? and friendid=?",nativeQuery = true)
    public void updateIslike(String islike,String userid,String friendid);

    /**
     * 删除当前登录用户的好友
     * @param userid
     * @param friendid
     */
    @Modifying
    @Query(value = "delete from tb_friend where userid=? and friendid=?",nativeQuery = true)
    public void deleteFriend(String userid, String friendid);
}
