package com.zwdbj.server.adminserver.service.shop.service.shopdetail.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "营业时间")
public class OpeningHours {

    /// <summary>
    /// 周几：1|2|3|4|5|6|7
    /// </summary>
    /// <value>The day.</value>
    @ApiModelProperty(value = "周几")
    int day;
    @ApiModelProperty(value = "店铺id")
    long storeId;
    /// <summary>
    /// 营业时间，单位秒
    /// </summary>
    /// <value>The open time.</value>
    @ApiModelProperty(value = "营业时间")
    int openTime;
    /// <summary>
    /// 下班时间，单位秒
    /// </summary>
    /// <value>The close time.</value>
    @ApiModelProperty(value = "下班时间")
    int closeTime;



    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public long getStoreId() {
        return storeId;
    }

    public void setStoreId(long storeId) {
        this.storeId = storeId;
    }

    public int getOpenTime() {
        return openTime;
    }

    public void setOpenTime(int openTime) {
        this.openTime = openTime;
    }

    public int getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(int closeTime) {
        this.closeTime = closeTime;
    }
}
