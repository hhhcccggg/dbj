package com.zwdbj.server.mobileapi.service.video.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "首页查看视频")
public class VideoMainInput {

    @ApiModelProperty(value = "分页字段,如第一次可不传,")
    String scroll_id;
    @ApiModelProperty(value = "RECOMMEND //推荐  NEARBY      //附近",required = true)
    VideoMainType type;
    @ApiModelProperty(value = "经度")
    String longitude;
    @ApiModelProperty(value = "纬度")
    String latitude;
    @ApiModelProperty(value = "分类ID")
    Long category;

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getScroll_id() {
        return scroll_id;
    }

    public void setScroll_id(String scroll_id) {
        this.scroll_id = scroll_id;
    }

    public VideoMainType getType() {
        return type;
    }

    public void setType(VideoMainType type) {
        this.type = type;
    }

    public Long getCategory() {
        return category;
    }

    public void setCategory(Long category) {
        this.category = category;
    }
}
