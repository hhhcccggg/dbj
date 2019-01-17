package com.zwdbj.server.mobileapi.service.wxMiniProgram.task.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

@ApiModel(description = "用户金币任务的model")
public class TaskModel {
    @ApiModelProperty("用户id")
    private long userId;
    @ApiModelProperty("任务id")
    private String id;
    @ApiModelProperty(value = "任务创建时间")
    private Date createTime;
    @ApiModelProperty(value = "任务类型:  NEWUSER:新手任务,DAILY:日常任务,INVITATION:邀请任务")
    private String type;
    @ApiModelProperty(value = "任务名称")
    private String title;
    @ApiModelProperty(value = "任务完成获得金币")
    private int coins;
    @ApiModelProperty(value = "任务描述")
    private String desc;
    @ApiModelProperty(value = "状态  NONE   DOING:进行中  DONE:完成   null时为未完成")
    private String state;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }


    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
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
