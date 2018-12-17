package com.zwdbj.server.mobileapi.service.video.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "打赏视频")
public class VideoPlayTourInput {
    @ApiModelProperty(value = "打赏的金币")
    int coins;
    @ApiModelProperty(value = "获得打赏的视频ID")
    long videoId;

    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    public long getVideoId() {
        return videoId;
    }

    public void setVideoId(long videoId) {
        this.videoId = videoId;
    }
}
