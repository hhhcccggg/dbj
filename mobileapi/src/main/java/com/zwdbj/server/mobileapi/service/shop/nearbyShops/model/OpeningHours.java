package com.zwdbj.server.mobileapi.service.shop.nearbyShops.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel(value = "营业时间")
public class OpeningHours implements Serializable {

    private static final long serialVersionUID = 1617780388792869686L;
    @ApiModelProperty(value = "开门时间")
    private int openTime;
    @ApiModelProperty(value = "打烊时间")
    private int closeTime;
    @ApiModelProperty(value = "星期几")
    private int day;
    @ApiModelProperty(value = "店铺id")
    private long storeId;

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
}
