package com.zwdbj.server.adminserver.service.homepage.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "热门标签")
public class AdFindHotTagsDto {
    @ApiModelProperty(value = "标签名")
    String tagName;
    @ApiModelProperty(value = "使用次数")
    Long resNumber;

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public Long getResNumber() {
        return resNumber;
    }

    public void setResNumber(Long resNumber) {
        this.resNumber = resNumber*43;
    }
}
