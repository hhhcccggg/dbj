package com.zwdbj.server.mobileapi.service.adBanner.moder;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "平台，页面类型")
public class AdBannerInput {
    @ApiModelProperty(value = "不同地方的banner MINIAPP_HOME:微信小程序首页 APP_O2O_HOME:app周边首页 ")
    Type type;
    @ApiModelProperty(value = "平台类型 IOS:苹果ANDROID:安卓ALL:所有")
    Platform platform;

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
}
