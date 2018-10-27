package com.zwdbj.server.adminserver.service.complain.model;

import io.swagger.annotations.ApiModelProperty;

public class ComplainReasonListInput {
    @ApiModelProperty(value = "0：用户1：视频2：直播")
    int type;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
