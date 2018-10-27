package com.zwdbj.server.adminserver.service.living.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "创建直播")
public class CreateLivingInput {
    @ApiModelProperty(value = "直播信息描述")
    String title;
    @ApiModelProperty(value = "直播封面，这里上传七牛上传后的key")
    String coverUrl;
    @ApiModelProperty(value = "关联的宠物，多个宠物id使用英文,隔开")
    String linkPets;
    float longitude;
    float latitude;
    @ApiModelProperty(value = "经纬度解析后的中文地址")
    String address;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public String getLinkPets() {
        return linkPets;
    }

    public void setLinkPets(String linkPets) {
        this.linkPets = linkPets;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
