package com.zwdbj.server.adminserver.service.adBanner.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "adBanner搜索条件")
public class AdBannerInput {
    @ApiModelProperty(value = "不同地方的banner MINIAPP_HOME:微信小程序首页 APP_O2O_HOME:app周边首页 " +
            "DISCOUNT_APP:app优惠折扣 COIN_TASK_APP:app金币任务")
    Type type;
    @ApiModelProperty(value = "平台类型  IOS:苹果ANDROID:安卓ALL:所有")
    Platform platform;
    @ApiModelProperty(value = "状态 ONLINE:上线 OFFLINE:下线")
    State state;

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Platform getPlatform() {
        return platform;
    }

    public void setPlatform(Platform platform) {
        this.platform = platform;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }
}
