package com.zwdbj.server.mobileapi.service.adBanner.moder;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "平台，页面类型")
public class AdBannerInput {
    @ApiModelProperty(value = "不同地方的banner MINIAPP_HOME:微信小程序首页 APP_O2O_HOME:app周边首页 " +
            "DISCOUNT_APP:app优惠折扣 COIN_TASK_APP:app金币任务")
    private String type;
    @ApiModelProperty(value = "平台类型 IOS:苹果ANDROID:安卓ALL:所有")
    private String platform;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }
}
