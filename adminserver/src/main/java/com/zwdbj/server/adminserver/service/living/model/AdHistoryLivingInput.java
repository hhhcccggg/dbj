package com.zwdbj.server.adminserver.service.living.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "历史直播")
public class AdHistoryLivingInput {
    @ApiModelProperty(value = "用户id/手机号/昵称")
    String keywords;

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }
}
