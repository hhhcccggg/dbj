package com.zwdbj.server.adminserver.service.user.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "关注用户信息")
public class FollowerUserInfoDto extends UserDetailInfoDto {
    @ApiModelProperty(value = "对方是否已关注我")
    boolean isFollowedMe;

    public boolean isFollowedMe() {
        return isFollowedMe;
    }

    public void setFollowedMe(boolean followedMe) {
        isFollowedMe = followedMe;
    }
}
