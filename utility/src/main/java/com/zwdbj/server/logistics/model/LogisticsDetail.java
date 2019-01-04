package com.zwdbj.server.logistics.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel
public class LogisticsDetail implements Serializable {
    @ApiModelProperty(value = "时间")
    String time;
    @ApiModelProperty(value = "状态")
    String status;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
