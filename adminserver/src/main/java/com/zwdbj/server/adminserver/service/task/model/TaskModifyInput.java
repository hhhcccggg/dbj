package com.zwdbj.server.adminserver.service.task.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "修改任务所需字段")
public class TaskModifyInput {
    @ApiModelProperty(value = "任务名称")
    private String title;
    @ApiModelProperty(value = "任务完成获得小饼干")
    private int coins;
    @ApiModelProperty(value = "任务描述")
    private String desc;

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
