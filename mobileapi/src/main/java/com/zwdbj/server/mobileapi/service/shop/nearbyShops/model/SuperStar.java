package com.zwdbj.server.mobileapi.service.shop.nearbyShops.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "代言人")
public class SuperStar {
    @ApiModelProperty(value = "代言人用户id")
    private long userId;
    @ApiModelProperty(value = "代言人店铺id")
    private long storeId;
    @ApiModelProperty(value = "昵称")
    private String nickName;
    @ApiModelProperty(value = "视频数量")
    private int videoCounts;
    @ApiModelProperty(value = "头像")
    private String avatarUrl;
    @ApiModelProperty(value = "认证资质")
    private String qualification;

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

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

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public int getVideoCounts() {
        return videoCounts;
    }

    public void setVideoCounts(int videoCounts) {
        this.videoCounts = videoCounts;
    }
}
