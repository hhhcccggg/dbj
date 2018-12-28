package com.zwdbj.server.mobileapi.service.video.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "视频点赞后返回的数据")
public class VideoHeartStatusDto {
    @ApiModelProperty(value = "视频id")
    long videoId;
    @ApiModelProperty(value = "该用户是否点赞 true为已经点赞，false为没点赞")
    boolean isHeart;

    public long getVideoId() {
        return videoId;
    }

    public void setVideoId(long videoId) {
        this.videoId = videoId;
    }

    public boolean isHeart() {
        return isHeart;
    }

    public void setHeart(boolean heart) {
        isHeart = heart;
    }
}
