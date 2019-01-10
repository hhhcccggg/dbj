package com.zwdbj.server.mobileapi.service.shop.comments.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "用户评价概览")
public class UserComments {
    @ApiModelProperty("用户评价评价分")
    float rate;
    @ApiModelProperty("用户评论数")
    long countComments;

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    public long getCountComments() {
        return countComments;
    }

    public void setCountComments(long countComments) {
        this.countComments = countComments;
    }
}
