package com.zwdbj.server.adminserver.service.video.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "搜索视频")
public class AdVideoVerityInfoInput {
    @ApiModelProperty(value = "视频状态，-1：所有，0：正常的,1:未审核,2:下架,3:待审核")
    int status;
    @ApiModelProperty(value = "关键字，可以查询标题")
    String keywords;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }
}
