package com.zwdbj.server.adminserver.service.user.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "查询运营用户时的输入字段")
public class AdMarketUserInput {
    @ApiModelProperty(value = "账号状态,-1:全部状态,0:正常状态,1:冻结状态")
    int status;
    @ApiModelProperty(value = "搜索的关键字:用户id/手机号")
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
