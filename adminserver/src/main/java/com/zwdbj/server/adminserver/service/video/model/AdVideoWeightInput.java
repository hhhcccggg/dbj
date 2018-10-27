package com.zwdbj.server.adminserver.service.video.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel(description = "推荐视频的权重")
public class AdVideoWeightInput implements Serializable {


    @ApiModelProperty(value = "推荐视频,播放量的权重")
    int playCount;
    @ApiModelProperty(value = "推荐视频,点赞量的权重")
    int heartCount;
    @ApiModelProperty(value = "推荐视频,分享量的权重")
    int shareCount;
    @ApiModelProperty(value = "推荐视频,评论量的权重")
    int commentCount;

    public int getPlayCount() {
        return playCount;
    }

    public void setPlayCount(int playCount) {
        this.playCount = playCount;
    }

    public int getHeartCount() {
        return heartCount;
    }

    public void setHeartCount(int heartCount) {
        this.heartCount = heartCount;
    }

    public int getShareCount() {
        return shareCount;
    }

    public void setShareCount(int shareCount) {
        this.shareCount = shareCount;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }
}
