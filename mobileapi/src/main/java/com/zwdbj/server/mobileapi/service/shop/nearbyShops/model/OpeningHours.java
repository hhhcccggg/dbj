package com.zwdbj.server.mobileapi.service.shop.nearbyShops.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "营业时间")
public class OpeningHours {

    @ApiModelProperty(value = "开门时间")
    int openingTime;
    @ApiModelProperty(value = "打烊时间")
    int cloesTime;
    @ApiModelProperty(value = "星期几")
    int day;

    public int getOpeningTime() {
        return openingTime;
    }

    public void setOpeningTime(int openingTime) {
        this.openingTime = openingTime;
    }

    public int getCloesTime() {
        return cloesTime;
    }

    public void setCloesTime(int cloesTime) {
        this.cloesTime = cloesTime;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }
}
