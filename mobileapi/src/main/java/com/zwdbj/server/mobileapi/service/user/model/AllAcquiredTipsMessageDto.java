package com.zwdbj.server.mobileapi.service.user.model;

import com.zwdbj.server.utility.common.DateTimeFriendly;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

@ApiModel(description = "获取的全部打赏")
public class AllAcquiredTipsMessageDto {
    @ApiModelProperty(value = "打赏ID")
    long id;
    @ApiModelProperty(value = "打赏用户")
    long userId;
    @ApiModelProperty(value = "被打赏的视频")
    long videoId;
    @ApiModelProperty(value = "视频的作者")
    long ownUserId;
    @ApiModelProperty(value = "视频获得的打赏小饼干")
    long tipCoin;
    @ApiModelProperty(value = "打赏用户的昵称")
    String nickName;
    @ApiModelProperty(value = "打赏用户的头像")
    String avatarUrl;
    @ApiModelProperty(value = "被打赏的视频的标题")
    String title;
    @ApiModelProperty("打赏的时间")
    Date createTime;
    @ApiModelProperty(value = "时间友好显示，比如：5分前，1小时前。")
    protected String createTimeFormat;

    public long getTipCoin() {
        return tipCoin;
    }

    public void setTipCoin(long tipCoin) {
        this.tipCoin = tipCoin;
    }

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
