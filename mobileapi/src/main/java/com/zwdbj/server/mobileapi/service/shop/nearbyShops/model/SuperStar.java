package com.zwdbj.server.mobileapi.service.shop.nearbyShops.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "代言人")
public class SuperStar {
    @ApiModelProperty(value = "代言人用户id")
    long userId;
    @ApiModelProperty(value = "代言人店铺id")
    long storeId;
    @ApiModelProperty(value = "昵称")
    String userName;
    @ApiModelProperty(value = "视频数量")
    int videoCounts;
    @ApiModelProperty(value = "头像")
    String avatarUrl;

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getStoreId() {
        return storeId;
    }

    public void setStoreId(long storeId) {
        this.storeId = storeId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getVideoCounts() {
        return videoCounts;
    }

    public void setVideoCounts(int videoCounts) {
        this.videoCounts = videoCounts;
    }
}
