package com.zwdbj.server.adminserver.service.shop.service.customerComments.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

@ApiModel(description = "评论的回复")
public class RefComment {
    @ApiModelProperty(value = "回复的评论id")
    long refCommentId;

    @ApiModelProperty(value = "用户名")
    String userName;
    @ApiModelProperty(value = "用户id")
    long userId;
    @ApiModelProperty(value = "发布时间")
    Date createTime;
    @ApiModelProperty(value = "回复内容")
    String refContentTxt;

    public long getRefCommentId() {
        return refCommentId;
    }

    public void setRefCommentId(long refCommentId) {
        this.refCommentId = refCommentId;
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

    public String getRefContentTxt() {
        return refContentTxt;
    }

    public void setRefContentTxt(String refContentTxt) {
        this.refContentTxt = refContentTxt;
    }
}
