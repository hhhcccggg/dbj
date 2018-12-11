package com.zwdbj.server.mobileapi.service.userAssets.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "可选充值金币配置列表")
public class BuyCoinConfigModel {
    @ApiModelProperty(value = "id")
    Long id;
    @ApiModelProperty(value = "金币")
    int coins;
    @ApiModelProperty(value = "金币对应的人民币，单位分")
    int rmbs;
    @ApiModelProperty(value = "标题")
    String title;
    @ApiModelProperty(value = "排序")
    int orderIndex;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    public int getRmbs() {
        return rmbs;
    }

    public void setRmbs(int rmbs) {
        this.rmbs = rmbs;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getOrderIndex() {
        return orderIndex;
    }

    public void setOrderIndex(int orderIndex) {
        this.orderIndex = orderIndex;
    }
}
