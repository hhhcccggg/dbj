package com.zwdbj.server.adminserver.service.video.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "视频打赏详情")
public class VideoTipDetails {
    @ApiModelProperty(value = "id")
    Long id;
    @ApiModelProperty(value = "videoId")
    Long videoId;
    @ApiModelProperty(value = "userId")
    Long userId;
    @ApiModelProperty(value = "用户昵称")
    String nickName;
    @ApiModelProperty(value = "打赏金币数")
    int tipCoin;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getVideoId() {
        return videoId;
    }

    public void setVideoId(Long videoId) {
        this.videoId = videoId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public int getTipCoin() {
        return tipCoin;
    }

    public void setTipCoin(int tipCoin) {
        this.tipCoin = tipCoin;
    }
}
