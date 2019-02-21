package com.zwdbj.server.adminserver.service.shop.service.store.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "审核店铺")
public class ReviewStoreInput {
    @ApiModelProperty(value = "是否通过审核,true为通过")
    private boolean reviewOrNot;
    @ApiModelProperty(value = "未通过原因,仅审核未通过时填写")
    private String rejectMsg;

    public boolean isReviewOrNot() {
        return reviewOrNot;
    }

    public void setReviewOrNot(boolean reviewOrNot) {
        this.reviewOrNot = reviewOrNot;
    }

    public String getRejectMsg() {
        return rejectMsg;
    }

    public void setRejectMsg(String rejectMsg) {
        this.rejectMsg = rejectMsg;
    }
}
