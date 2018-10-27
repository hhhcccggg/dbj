package com.zwdbj.server.adminserver.service.video.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "审核视频字段")
public class AdVideoVerityInput {
    @ApiModelProperty(value = "视频的状态,-1所有,0:通过,1:未审核,2:拒绝")
    int status;
    @ApiModelProperty("拒绝原因")
    String rejectMsg;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getRejectMsg() {
        return rejectMsg;
    }

    public void setRejectMsg(String rejectMsg) {
        this.rejectMsg = rejectMsg;
    }
}
