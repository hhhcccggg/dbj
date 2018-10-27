package com.zwdbj.server.adminserver.service.user.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "用户相关关注情况")
public class UserFollowInfoDto {
    @ApiModelProperty(value = "是否已关注对方")
    boolean isFollowed;
    @ApiModelProperty(value = "对方是否已关注我")
    boolean isMyFollower;

    public boolean isFollowed() {
        return isFollowed;
    }

    public void setFollowed(boolean followed) {
        isFollowed = followed;
    }

    public boolean isMyFollower() {
        return isMyFollower;
    }

    public void setMyFollower(boolean myFollower) {
        isMyFollower = myFollower;
    }
}
