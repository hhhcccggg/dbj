package com.zwdbj.server.mobileapi.service.user.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "用户相互关注情况查询")
public class UserFollowInfoSearchInput {
    @ApiModelProperty(value = "代表我")
    long userId;
    @ApiModelProperty(value = "代表对方")
    long toUserId;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getToUserId() {
        return toUserId;
    }

    public void setToUserId(long toUserId) {
        this.toUserId = toUserId;
    }
}
