package com.zwdbj.server.mobileapi.service.video.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "首页查看视频")
public class VideoMainInput {

    @ApiModelProperty(value = "分页字段,如第一次可不传,")
    String scroll_id;
    @ApiModelProperty(value = "RECOMMEND //推荐  NEARBY      //附近",required = true)
    VideoMainType type;
    @ApiModelProperty(value = "坐标位置;如:30.6377966390,104.1077166796")
    String location;
    @ApiModelProperty(value = "分类ID")
    Long category;

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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Long getCategory() {
        return category;
    }

    public void setCategory(Long category) {
        this.category = category;
    }
}
