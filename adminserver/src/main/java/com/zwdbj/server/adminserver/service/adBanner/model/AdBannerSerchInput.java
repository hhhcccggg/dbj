package com.zwdbj.server.adminserver.service.adBanner.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "adBanner搜索条件")
public class AdBannerSerchInput {
    @ApiModelProperty(value = "banner的展示范围")
    private String exhibitionScope;
    @ApiModelProperty(value = "搜索关键字")
    private String keyWords;
    @ApiModelProperty(value = "状态 ONLINE:上线 OFFLINE:下线")
    private String state;
    @ApiModelProperty("banner活动的开始时间")
    private String startTime;
    @ApiModelProperty("banner活动的结束时间")
    private String endTime;
    @ApiModelProperty(value = "此banner适用对象, 全部:ALL  新用户:NEWUSER    老用户:OLDUSER")
    private String applyUser;

    public String getExhibitionScope() {
        return exhibitionScope;
    }

    public void setExhibitionScope(String exhibitionScope) {
        this.exhibitionScope = exhibitionScope;
    }

    public String getKeyWords() {
        return keyWords;
    }

    public void setKeyWords(String keyWords) {
        this.keyWords = keyWords;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
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

    public String getApplyUser() {
        return applyUser;
    }

    public void setApplyUser(String applyUser) {
        this.applyUser = applyUser;
    }
}
