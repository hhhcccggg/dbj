package com.zwdbj.server.adminserver.service.setting.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel(description = "APP推送设置")
public class AppPushSettingModel implements Serializable {
    @ApiModelProperty(value = "评论推送开关")
    private boolean commentIsOpen;
    @ApiModelProperty(value = "点赞推送开关")
    private boolean heartIsOpen;
    @ApiModelProperty(value = "新粉丝推送开关")
    private boolean newFollowerIsOpen;
    @ApiModelProperty(value = "关注的人发布新视频推送开关")
    private boolean myFollowedPubNewVideoIsOpen;
    @ApiModelProperty(value = "关注的人直播推送开关")
    private boolean myFollowedLivingIsOpen;
    @ApiModelProperty(value = "系统通知推送开关")
    private boolean systemIsOpen;

    public boolean isCommentIsOpen() {
        return commentIsOpen;
    }

    public void setCommentIsOpen(boolean commentIsOpen) {
        this.commentIsOpen = commentIsOpen;
    }

    public boolean isHeartIsOpen() {
        return heartIsOpen;
    }

    public void setHeartIsOpen(boolean heartIsOpen) {
        this.heartIsOpen = heartIsOpen;
    }

    public boolean isNewFollowerIsOpen() {
        return newFollowerIsOpen;
    }

    public void setNewFollowerIsOpen(boolean newFollowerIsOpen) {
        this.newFollowerIsOpen = newFollowerIsOpen;
    }

    public boolean isMyFollowedPubNewVideoIsOpen() {
        return myFollowedPubNewVideoIsOpen;
    }

    public void setMyFollowedPubNewVideoIsOpen(boolean myFollowedPubNewVideoIsOpen) {
        this.myFollowedPubNewVideoIsOpen = myFollowedPubNewVideoIsOpen;
    }

    public boolean isMyFollowedLivingIsOpen() {
        return myFollowedLivingIsOpen;
    }

    public void setMyFollowedLivingIsOpen(boolean myFollowedLivingIsOpen) {
        this.myFollowedLivingIsOpen = myFollowedLivingIsOpen;
    }

    public boolean isSystemIsOpen() {
        return systemIsOpen;
    }

    public void setSystemIsOpen(boolean systemIsOpen) {
        this.systemIsOpen = systemIsOpen;
    }
}
