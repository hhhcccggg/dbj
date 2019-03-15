package com.zwdbj.server.adminserver.service.shop.service.customerComments.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

@ApiModel(description = "客户评价")
public class CommentInfo {
    @ApiModelProperty(value = "评论id")
    long id;
    @ApiModelProperty(value = "评价内容")
    String contentTxt;
    @ApiModelProperty(value = "评分")
    float rate;
    @ApiModelProperty(value = "点赞数")
    long heartCount;
    @ApiModelProperty(value = "用户名")
    String userName;
    @ApiModelProperty(value = "用户id")
    long userId;
    @ApiModelProperty(value = "发布时间")
    Date createTime;
    @ApiModelProperty(value = "被评论目标资源名")
    String resourceOwnerName;
    @ApiModelProperty(value = "被评论目标资源id")
    long resourceOwnerId;
    @ApiModelProperty(value = "是否回复")
    boolean refCommentOrNot;
    @ApiModelProperty(value = "评论回复")
    RefComment refComment;
    @ApiModelProperty(value = "评论关联的媒体资源类型,VIDEO IMAGE")
    String type;
    @ApiModelProperty(value = "资源id")
    long dataId;
    @ApiModelProperty(value = "媒体资源内容")
    String dataContent;
    @ApiModelProperty(value = "视频第一帧")
    String coverImageUrl;

    public boolean isRefCommentOrNot() {
        return refCommentOrNot;
    }

    public void setRefCommentOrNot(boolean refCommentOrNot) {
        this.refCommentOrNot = refCommentOrNot;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getContentTxt() {
        return contentTxt;
    }

    public void setContentTxt(String contentTxt) {
        this.contentTxt = contentTxt;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    public long getHeartCount() {
        return heartCount;
    }

    public void setHeartCount(long heartCount) {
        this.heartCount = heartCount;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getResourceOwnerName() {
        return resourceOwnerName;
    }

    public void setResourceOwnerName(String resourceOwnerName) {
        this.resourceOwnerName = resourceOwnerName;
    }

    public long getResourceOwnerId() {
        return resourceOwnerId;
    }

    public void setResourceOwnerId(long resourceOwnerId) {
        this.resourceOwnerId = resourceOwnerId;
    }

    public RefComment getRefComment() {
        return refComment;
    }

    public void setRefComment(RefComment refComment) {
        this.refComment = refComment;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getDataId() {
        return dataId;
    }

    public void setDataId(long dataId) {
        this.dataId = dataId;
    }

    public String getDataContent() {
        return dataContent;
    }

    public void setDataContent(String dataContent) {
        this.dataContent = dataContent;
    }

    public String getCoverImageUrl() {
        return coverImageUrl;
    }

    public void setCoverImageUrl(String coverImageUrl) {
        this.coverImageUrl = coverImageUrl;
    }
}
