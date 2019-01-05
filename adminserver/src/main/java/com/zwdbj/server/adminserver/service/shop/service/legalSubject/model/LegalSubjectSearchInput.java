package com.zwdbj.server.adminserver.service.shop.service.legalSubject.model;

import io.swagger.annotations.ApiModelProperty;

public class LegalSubjectSearchInput {
    @ApiModelProperty("商家的状态:-1>>所有；0：正常1：关闭")
    int status;
    @ApiModelProperty("默认为空，可输入商家名称，商家法人姓名，商家联系人的姓名和手机号码查询")
    String keyWords;
    @ApiModelProperty("商家的类型 P:个人；B:企业,默认为空")
    String type;
    @ApiModelProperty("查询的开始时间")
    String startTime;
    @ApiModelProperty("查询的结束时间")
    String endTime;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getKeyWords() {
        return keyWords;
    }

    public void setKeyWords(String keyWords) {
        this.keyWords = keyWords;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
