package com.zwdbj.server.adminserver.service.tag.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "查询视频标签基本信息字段")
public class AdVideoTagInput {
    @ApiModelProperty(value = "视频标签的状态,-1:全部,0:正常,1:停用")
    int status;
    @ApiModelProperty(value = "输入的关键字")
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
