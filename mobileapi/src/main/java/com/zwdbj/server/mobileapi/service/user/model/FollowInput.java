package com.zwdbj.server.mobileapi.service.user.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "关注用户")
public class FollowInput {
    @ApiModelProperty(value = "true:关注，false:取消")
    boolean isFollow;
    @ApiModelProperty(value = "关注/取消的用户id")
    long userId;
    @ApiModelProperty(value = "默认为0:无直播间id,如有id则为直播间id")
    Long livingId;

    public boolean isFollow() {
        return isFollow;
    }

    public void setFollow(boolean follow) {
        isFollow = follow;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public Long getLivingId() {
        return livingId;
    }

    public void setLivingId(Long livingId) {
        this.livingId = livingId;
    }
}
