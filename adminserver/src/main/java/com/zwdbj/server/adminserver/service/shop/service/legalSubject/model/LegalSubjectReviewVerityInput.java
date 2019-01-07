package com.zwdbj.server.adminserver.service.shop.service.legalSubject.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "审核商家需要审核的信息字段")
public class LegalSubjectReviewVerityInput {
    @ApiModelProperty(value = "商家需要审核的信息的状态：0正常，1：审核中，2：拒接")
    private int status;
    @ApiModelProperty("审核说明")
    private String rejectMsg;

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
