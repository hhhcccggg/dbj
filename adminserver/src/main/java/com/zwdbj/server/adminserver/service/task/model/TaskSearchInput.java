package com.zwdbj.server.adminserver.service.task.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "查询任务所需字段")
public class TaskSearchInput {
    @ApiModelProperty(value = "任务类型:  NEWUSER:新手任务,DAILY:日常任务,INVITATION:邀请任务")
    private String type;
    @ApiModelProperty("查询的开始时间")
    private String startTime;
    @ApiModelProperty("查询的结束时间")
    private String endTime;
    @ApiModelProperty("任务名称,或者任务的id")
    private String keyWords;

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

    public String getKeyWords() {
        return keyWords;
    }

    public void setKeyWords(String keyWords) {
        this.keyWords = keyWords;
    }

}
