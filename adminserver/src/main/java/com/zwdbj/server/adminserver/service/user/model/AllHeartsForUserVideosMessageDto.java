package com.zwdbj.server.adminserver.service.user.model;

import com.zwdbj.server.adminserver.utility.DateTimeFriendly;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

@ApiModel(description = "我的短视频被点赞的消息提醒模型")
public class AllHeartsForUserVideosMessageDto {
    @ApiModelProperty(value = "点赞ID")
    long id;
    @ApiModelProperty(value = "点赞用户")
    long userId;
    @ApiModelProperty(value = "被点赞的视频")
    long videoId;
    @ApiModelProperty(value = "视频的作者")
    long ownUserId;
    @ApiModelProperty(value = "点赞用户的昵称")
    String nickName;
    @ApiModelProperty(value = "点赞用户的头像")
    String avatarUrl;
    @ApiModelProperty(value = "被赞的视频的标题")
    String title;
    @ApiModelProperty("点赞的时间")
    Date createTime;
    @ApiModelProperty(value = "时间友好显示，比如：5分前，1小时前。")
    protected String createTimeFormat;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getVideoId() {
        return videoId;
    }

    public void setVideoId(long videoId) {
        this.videoId = videoId;
    }

    public long getOwnUserId() {
        return ownUserId;
    }

    public void setOwnUserId(long ownUserId) {
        this.ownUserId = ownUserId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
        this.setCreateTimeFormat(DateTimeFriendly.friendlyShow(this.createTime));
    }

    public String getCreateTimeFormat() {
        return createTimeFormat;
    }

    public void setCreateTimeFormat(String createTimeFormat) {
        this.createTimeFormat = createTimeFormat;
    }
}
