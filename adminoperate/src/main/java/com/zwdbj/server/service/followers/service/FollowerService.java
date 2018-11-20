package com.zwdbj.server.service.followers.service;

import com.zwdbj.server.service.followers.mapper.IFollowerMapper;
import com.zwdbj.server.service.followers.model.FollowersModel;
import com.zwdbj.server.utility.common.UniqueIDCreater;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FollowerService {
    @Autowired
    IFollowerMapper followerMapper;

    /**
     * userId是否有followerUserId这个粉丝
     * @param followerUserId
     * @param userId
     * @return
     */
    public int followIsExit(long followerUserId,long userId){
        return this.followerMapper.followIsExit(followerUserId,userId);
    }

    /**
     * focuses
     * userId是否存在粉丝
     */
    public boolean isExitFollow(long userId){
        List<FollowersModel> g = this.followerMapper.isExitFollow(userId);
        if (g==null)return false;
        return true;
    }
    /**
     * userId是否存在关注
     */
    public boolean isExitFocuses(long userId){
        List<FollowersModel> g = this.followerMapper.isExitFocuses(userId);
        if (g==null)return false;
        return true;
    }

    /**
     * 创建userId的粉丝或者userId的关注
     */
    public int newMyFollower(long followerUserId,long userId){
        long id = UniqueIDCreater.generateID();
        return this.followerMapper.newMyFollower(id,followerUserId,userId);
    }

}
