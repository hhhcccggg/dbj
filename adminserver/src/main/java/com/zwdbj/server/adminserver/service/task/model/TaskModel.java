package com.zwdbj.server.adminserver.service.task.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

@ApiModel(description = "任务的model")
public class TaskModel {
    @ApiModelProperty("id")
    private String id;
    @ApiModelProperty(value = "任务创建时间")
    private Date createTime;
    @ApiModelProperty(value = "任务类型:  NEWUSER:新手任务,DAILY:日常任务,INVITATION:邀请任务")
    private String type;
    @ApiModelProperty(value = "任务名称")
    private String title;
    @ApiModelProperty(value = "任务完成获得小饼干")
    private int coins;
    @ApiModelProperty(value = "任务描述")
    private String desc;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
