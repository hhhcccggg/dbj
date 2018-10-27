package com.zwdbj.server.adminserver.service.living.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "今日直播输入字段")
public class AdTodayLivingInput {
    @ApiModelProperty(value = "直播状态 -1:全部,0:已结束,1:正在直播")
    int isLiving;
    @ApiModelProperty(value = "搜索的用户ID/手机号/昵称")
    String keywords;
    @ApiModelProperty(value = "直播的是否违规状态,-1:所有 0:正常 1:人工审核 2:违规")
    int status;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getIsLiving() {
        return isLiving;
    }

    public void setIsLiving(int isLiving) {
        this.isLiving = isLiving;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }
}
