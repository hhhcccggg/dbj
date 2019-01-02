package com.zwdbj.server.shop_admin_service.homepage.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

@ApiModel(value = "视频趋势")
public class VideoTrend {
    @ApiModelProperty(value = "数量")
    int counts;
    @ApiModelProperty(value = "日期")
    Date createTime;

    public int getCounts() {
        return counts;
    }

    public void setCounts(int counts) {
        this.counts = counts;
    }

    public Date getDate() {
        return createTime;
    }

    public void setDate(Date date) {
        this.createTime = date;
    }
}
