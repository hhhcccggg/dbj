package com.zwdbj.server.mobileapi.service.shop.comments.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

@ApiModel(value = "商品用户评论")
public class ShopComments implements Serializable {
    @ApiModelProperty(value = "用户id")
    long userId;
    @ApiModelProperty(value = "用户名")
    String userName;
    @ApiModelProperty(value = "用户头像")
    String avatarUrl;
    @ApiModelProperty(value = "评论内容")
    String contentTxt;
    @ApiModelProperty(value = "点赞数")
    long heartCount;
    @ApiModelProperty(value = "评论时间")
    Date createTime;
    @ApiModelProperty(value = "被评论目标资源，如商品id")
    long resourceOwnerId;
    @ApiModelProperty(value = "被评论目标资源名称")
    String resourceName;
    @ApiModelProperty(value = "0:针对视频评论1:针对下线商家服务评论2：针对商家产品的评论")
    int resourceTypeId;
    @ApiModelProperty(value = "评分")
    float rate;

    @ApiModelProperty(value = "时间格式化")
    String createTimeForMat;

    public String getCreateTimeForMate() {

        if (this.createTime == null) {
            return "";
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return simpleDateFormat.format(createTime);
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }


    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getContentTxt() {
        return contentTxt;
    }

    public void setContentTxt(String contentTxt) {
        this.contentTxt = contentTxt;
    }

    public long getHeartCount() {
        return heartCount;
    }

    public void setHeartCount(long heartCount) {
        this.heartCount = heartCount;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public long getResourceOwnerId() {
        return resourceOwnerId;
    }

    public void setResourceOwnerId(long resourceOwnerId) {
        this.resourceOwnerId = resourceOwnerId;
    }

    public int getResourceTypeId() {
        return resourceTypeId;
    }

    public void setResourceTypeId(int resourceTypeId) {
        this.resourceTypeId = resourceTypeId;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }
}
