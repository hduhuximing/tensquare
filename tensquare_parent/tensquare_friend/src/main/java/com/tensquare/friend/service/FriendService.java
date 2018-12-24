package com.tensquare.friend.service;

import com.tensquare.friend.dao.FriendDao;
import com.tensquare.friend.dao.NoFriendDao;
import com.tensquare.friend.pojo.Friend;
import com.tensquare.friend.pojo.NoFriend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author: 肖德子裕
 * @date: 2018/12/09 20:58
 * @description:
 */
@Service
@Transactional
public class FriendService {
    @Autowired
    private FriendDao friendDao;

    @Autowired
    private NoFriendDao noFriendDao;

    /**
     * 添加好友
     * @param userid 用户ID
     * @param friendid 好友ID
     * @return 0：失败；1：成功
     */
    public int addFriend(String userid, String friendid) {
        //判断userid到friendid是否有数据，有表示重复添加，返回0
        Friend friend = friendDao.findByUseridAndFriendid(userid, friendid);
        if (friend!=null){
            return 0;
        }
        //直接添加好友，单向喜欢islike为0
        friend=new Friend();
        friend.setUserid(userid);
        friend.setFriendid(friendid);
        friend.setIslike("0");
        friendDao.save(friend);
        //判断friendid到userid是否有数据，有表示双向喜欢，双方的islike改为1
        if (friendDao.findByUseridAndFriendid(friendid,userid)!=null){
            //双方的islike改为1
            friendDao.updateIslike("1",userid,friendid);
            friendDao.updateIslike("1",friendid,userid);
        }
        return 1;
    }

    /**
     * 添加非好友
     * @param userid 用户ID
     * @param friendid 好友ID
     * @return 0：失败；1：成功
     */
    public int addNoFriend(String userid, String friendid) {
        //判断是否已经是非好友，返回0
        NoFriend noFriend = noFriendDao.findByUseridAndFriendid(userid, friendid);
        if (noFriend!=null){
            return 0;
        }
        //直接添加到非好友列表
        noFriend=new NoFriend();
        noFriend.setUserid(userid);
        noFriend.setFriendid(friendid);
        noFriendDao.save(noFriend);
        return 1;
    }

    /**
     * 根据好友ID删除好友
     * @param userid
     * @param friendid
     */
    public void deleteFriend(String userid, String friendid) {
        //删除当前登录用户的好友
        friendDao.deleteFriend(userid,friendid);
        //修改好友的islike为0
        friendDao.updateIslike("0",friendid,userid);
        //添加到非好友表
        NoFriend noFriend=new NoFriend();
        noFriend.setUserid(userid);
        noFriend.setFriendid(friendid);
        noFriendDao.save(noFriend);
    }
}
