package com.zwdbj.server.adminserver.service.adBanner.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

@ApiModel(description = "adbanner信息")
public class AdBannerInfo {
    @ApiModelProperty(value = "id")
    private long id;
    @ApiModelProperty(value = "名称")
    private String title;
    @ApiModelProperty(value = "图片地址")
    private String imageUrl;
    @ApiModelProperty(value = "关联的h5网页")
    private String refUrl;
    @ApiModelProperty(value = "不同地方的banner MINIAPP_HOME:微信小程序首页 APP_O2O_HOME:app周边首页 DISCOUNT_APP:app优惠折扣 COIN_TASK_APP:app金币任务 ")
    private String type;
    @ApiModelProperty(value = "平台类型 IOS:苹果ANDROID:安卓ALL:所有")
    private String platform;
    @ApiModelProperty(value = "状态 ONLINE:上线 OFFLINE:下线")
    private String state;
    @ApiModelProperty(value = "创建时间")
    private Date CreateTime;
    @ApiModelProperty(value = "banner的展示范围")
    private String exhibitionScope;
    @ApiModelProperty("banner活动的开始时间")
    private String startTime;
    @ApiModelProperty("banner活动的结束时间")
    private String endTime;

    public String getExhibitionScope() {
        return exhibitionScope;
    }

    public void setExhibitionScope(String exhibitionScope) {
        this.exhibitionScope = exhibitionScope;
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

    public Date getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(Date createTime) {
        CreateTime = createTime;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
