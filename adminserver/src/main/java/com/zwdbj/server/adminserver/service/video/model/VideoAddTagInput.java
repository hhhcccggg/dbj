package com.zwdbj.server.adminserver.service.video.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "视频添加主题的参数")
public class VideoAddTagInput {
    @ApiModelProperty(value = "主题的id")
    private long tagId;
    @ApiModelProperty(value = "主题的名字")
    private String name;

    public long getTagId() {
        return tagId;
    }

    public void setTagId(long tagId) {
        this.tagId = tagId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
