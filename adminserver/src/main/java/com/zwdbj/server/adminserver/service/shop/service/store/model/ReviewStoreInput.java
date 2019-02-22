package com.zwdbj.server.adminserver.service.shop.service.store.model;

import io.swagger.annotations.ApiModelProperty;

public class ReviewStoreInput {
    @ApiModelProperty(value = "是否通过审核")
    private boolean reviewOrNot;
    @ApiModelProperty(value = "未通过审核的原因")
    private String reviewMsg;

    public boolean isReviewOrNot() {
        return reviewOrNot;
    }

    public void setReviewOrNot(boolean reviewOrNot) {
        this.reviewOrNot = reviewOrNot;
    }

    public String getReviewMsg() {
        return reviewMsg;
    }

    public void setReviewMsg(String reviewMsg) {
        this.reviewMsg = reviewMsg;
    }
}
