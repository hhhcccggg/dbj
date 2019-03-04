package com.zwdbj.server.mobileapi.service.userAssets.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel(description = "视频打赏详情")
public class VideoTipDetails implements Serializable {
    @ApiModelProperty(value = "id")
    Long id;
    @ApiModelProperty(value = "videoId")
    Long videoId;
    @ApiModelProperty(value = "userId")
    Long userId;
    @ApiModelProperty(value = "用户昵称")
    String nickName;
    @ApiModelProperty(value = "视频的title")
    String title;
    @ApiModelProperty(value = "打赏小饼干数")
    int tipCoin;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

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
