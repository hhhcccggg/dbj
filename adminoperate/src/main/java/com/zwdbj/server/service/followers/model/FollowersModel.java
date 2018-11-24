package com.zwdbj.server.service.followers.model;

import io.swagger.annotations.ApiModelProperty;

public class FollowersModel {
    long id;
    @ApiModelProperty(value = "关注userID的人")
    long followerUserId;
    long userId;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getFollowerUserId() {
        return followerUserId;
    }

    public void setFollowerUserId(long followerUserId) {
        this.followerUserId = followerUserId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}
