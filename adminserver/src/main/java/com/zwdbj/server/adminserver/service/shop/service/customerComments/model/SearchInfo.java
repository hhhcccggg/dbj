package com.zwdbj.server.adminserver.service.shop.service.customerComments.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@ApiModel(description = "搜索评论的条件")
public class SearchInfo {
    @ApiModelProperty(value = "输入的查询关键字， 用户昵称")
    private String username;
    @ApiModelProperty(value = "回复状态，0：未回复，1：已回复")
    private int isReply;
    @ApiModelProperty(value = "输入的查询起始时间")
    private String startTime;
    @ApiModelProperty(value = "输入的查询结束时间")
    private String endTime;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getIsReply() {
        return isReply;
    }

    public void setIsReply(int isReply) {
        this.isReply = isReply;
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
