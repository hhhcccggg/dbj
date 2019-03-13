package com.zwdbj.server.adminserver.service.video.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "处理被举报视频")
public class AdVideoDoComplainInput {

    @ApiModelProperty(value = "处理方式,0:忽略,4:下架")
    int treatment;

    public int getTreatment() {
        return treatment;
    }

    public void setTreatment(int treatment) {
        this.treatment = treatment;
    }
}
