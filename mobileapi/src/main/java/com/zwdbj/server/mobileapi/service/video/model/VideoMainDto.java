package com.zwdbj.server.mobileapi.service.video.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@ApiModel(description = "首页视频返回对象")
public class VideoMainDto {

    @ApiModelProperty(value = "分页字段")
    String scroll_id;

    @ApiModelProperty(value = "视频数据")
    List<VideoMain> videoMains;

    public VideoMainDto(String scroll_id, List<VideoMain> videoMains) {
        this.scroll_id = scroll_id;
        this.videoMains = videoMains;
    }

    public VideoMainDto() {
    }

    public String getScroll_id() {
        return scroll_id;
    }

    public void setScroll_id(String scroll_id) {
        this.scroll_id = scroll_id;
    }

    public List<VideoMain> getVideoMains() {
        return videoMains;
    }

    public void setVideoMains(List<VideoMain> videoMains) {
        this.videoMains = videoMains;
    }

}
