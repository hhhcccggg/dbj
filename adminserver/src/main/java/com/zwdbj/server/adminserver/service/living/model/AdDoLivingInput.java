package com.zwdbj.server.adminserver.service.living.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "处理直播的输入字段")
public class AdDoLivingInput {
    @ApiModelProperty(value = "用户的id")
    Long userId;
    @ApiModelProperty(value = "终止直播的原因")
    String rejectMsg;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }


    public String getRejectMsg() {
        return rejectMsg;
    }

    public void setRejectMsg(String rejectMsg) {
        this.rejectMsg = rejectMsg;
    }
}
