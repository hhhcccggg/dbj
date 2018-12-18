package com.zwdbj.server.mobileapi.service.video.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "视频打赏详情")
public class VideoPlayTourDto {
    @ApiModelProperty(value = "视频被打赏次数")
    private long tipCount;
    @ApiModelProperty(value = "用户金币总数")
    private long coins;

    public long getTipCount() {
        return tipCount;
    }

    public void setTipCount(long tipCount) {
        this.tipCount = tipCount;
    }

    public long getCoins() {
        return coins;
    }

    public void setCoins(long coins) {
        this.coins = coins;
    }
}
