package com.zwdbj.server.shop_admin_service.setting.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "营业时间")
public class OpeningHour {
    @ApiModelProperty("周几营业")
    int day;
    @ApiModelProperty("开门时间,当前时间到该日0点的秒数")
    int openTime;
    @ApiModelProperty("打烊时间")
    int cloesTime;

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getOpenTime() {
        return openTime;
    }

    public void setOpenTime(int openTime) {
        this.openTime = openTime;
    }

    public int getCloesTime() {
        return cloesTime;
    }

    public void setCloesTime(int cloesTime) {
        this.cloesTime = cloesTime;
    }
}
