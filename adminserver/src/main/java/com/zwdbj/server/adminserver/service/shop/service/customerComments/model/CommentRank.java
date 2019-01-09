package com.zwdbj.server.adminserver.service.shop.service.customerComments.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "评分级别统计")
public class CommentRank {
    @ApiModelProperty(value = "评分级别 5:完美 4:非常满意 3:不错 2:还行 1:无力吐槽")
    float rate;
    @ApiModelProperty(value = "数量")
    int counts;

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    public int getCounts() {
        return counts;
    }

    public void setCounts(int counts) {
        this.counts = counts;
    }
}
