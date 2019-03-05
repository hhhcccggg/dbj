package com.zwdbj.server.mobileapi.service.messageCenter.model;

import io.swagger.annotations.ApiModelProperty;

public class MessageInfoDetailDto {
    @ApiModelProperty(value = "消息的创造者id")
    protected long creatorUserId;
    @ApiModelProperty(value = "消息的创造者昵称")
    protected String creatorUserName;
    @ApiModelProperty(value = "消息的创造者头像")
    protected String creatorUserUrl;
    @ApiModelProperty(value = "视频的id 或者宠物的id")
    protected long refId;
    @ApiModelProperty(value = "视频的title 或者宠物的昵称")
    protected String title;
    @ApiModelProperty(value = "文本消息")
    protected String msgContent;
    @ApiModelProperty(value = "消息状态 0:未读1：已读2:删除")
    protected int status;
    @ApiModelProperty(value = "0:系统消息,1:点赞类2:粉丝类3:评论6:打赏")
    protected int messageType;
    @ApiModelProperty(value = "1:点赞视频2:点赞宠物")
    protected int videoOrPet;
    @ApiModelProperty(value = "打赏的小饼干数")
    protected int coins;

    @ApiModelProperty(value = "消息关联的链接地址")
    protected String refUrl;
    @ApiModelProperty(value = "消息数据内容")
    protected String dataContent;

    public String getCreatorUserUrl() {
        return creatorUserUrl;
    }

    public void setCreatorUserUrl(String creatorUserUrl) {
        this.creatorUserUrl = creatorUserUrl;
    }

    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    public int getVideoOrPet() {
        return videoOrPet;
    }

    public void setVideoOrPet(int videoOrPet) {
        this.videoOrPet = videoOrPet;
    }

    public long getCreatorUserId() {
        return creatorUserId;
    }

    public void setCreatorUserId(long creatorUserId) {
        this.creatorUserId = creatorUserId;
    }

    public String getCreatorUserName() {
        return creatorUserName;
    }

    public void setCreatorUserName(String creatorUserName) {
        this.creatorUserName = creatorUserName;
    }

    public long getRefId() {
        return refId;
    }

    public void setRefId(long refId) {
        this.refId = refId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMsgContent() {
        return msgContent;
    }

    public void setMsgContent(String msgContent) {
        this.msgContent = msgContent;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getMessageType() {
        return messageType;
    }

    public void setMessageType(int messageType) {
        this.messageType = messageType;
    }

    public String getRefUrl() {
        return refUrl;
    }

    public void setRefUrl(String refUrl) {
        this.refUrl = refUrl;
    }

    public String getDataContent() {
        return dataContent;
    }

    public void setDataContent(String dataContent) {
        this.dataContent = dataContent;
    }
}
