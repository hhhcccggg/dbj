package com.zwdbj.server.adminserver.service.userTenant.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "查询时的条件")
public class UserTenantSearchInput {
    @ApiModelProperty("空为查询所有")
    String keyWords;
    @ApiModelProperty("查询的开始时间")
    String startTime;
    @ApiModelProperty("查询的结束时间")
    String endTime;

    public String getKeyWords() {
        return keyWords;
    }

    public void setKeyWords(String keyWords) {
        this.keyWords = keyWords;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
