package com.zwdbj.server.adminserver.service.complain.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "查询举报字段")
public class AdFindComplainInput {

    @ApiModelProperty(value = "被举报者的状态,-1所有,1:正常,0:停用")
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
