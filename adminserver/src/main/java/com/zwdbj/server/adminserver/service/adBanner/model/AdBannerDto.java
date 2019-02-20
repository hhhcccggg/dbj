package com.zwdbj.server.adminserver.service.adBanner.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "创建,修改adBanner")
public class AdBannerDto {
    @ApiModelProperty(value = "名称")
    private String title;
    @ApiModelProperty(value = "图片地址")
    private String imageUrl;
    @ApiModelProperty(value = "关联的h5网页")
    private String refUrl;
    @ApiModelProperty(value = "不同地方的banner MINIAPP_HOME:微信小程序首页 APP_O2O_HOME:app周边首页 DISCOUNT_APP:app优惠折扣 COIN_TASK_APP:app金币任务")
    private String type;
    @ApiModelProperty(value = "平台类型 IOS:苹果ANDROID:安卓ALL:所有")
    private String platform;
    @ApiModelProperty(value = "状态 ONLINE:上线 OFFLINE:下线")
    private State state;
    @ApiModelProperty("banner活动的开始时间")
    private String startTime;
    @ApiModelProperty("banner活动的结束时间")
    private String endTime;
    @ApiModelProperty(value = "banner的展示范围")
    private String exhibitionScope;
    @ApiModelProperty(value = "此banner适用对象, 全部:ALL  新用户:NEWUSER    老用户:OLDUSER")
    private String applyUser;

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

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getExhibitionScope() {
        return exhibitionScope;
    }

    public void setExhibitionScope(String exhibitionScope) {
        this.exhibitionScope = exhibitionScope;
    }

    public String getApplyUser() {
        return applyUser;
    }

    public void setApplyUser(String applyUser) {
        this.applyUser = applyUser;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getRefUrl() {
        return refUrl;
    }

    public void setRefUrl(String refUrl) {
        this.refUrl = refUrl;
    }


    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }
}
