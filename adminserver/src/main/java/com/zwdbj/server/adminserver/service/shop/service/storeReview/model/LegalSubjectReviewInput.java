package com.zwdbj.server.adminserver.service.shop.service.storeReview.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "单个资料的审核")
public class LegalSubjectReviewInput {
    @ApiModelProperty(value = "0正常1：审核中2：拒接")
    private int status;
    @ApiModelProperty(value = "未通过原因,仅审核未通过时填写")
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
