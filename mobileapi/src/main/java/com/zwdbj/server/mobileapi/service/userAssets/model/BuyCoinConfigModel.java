package com.zwdbj.server.mobileapi.service.userAssets.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "可选充值小饼干配置列表")
public class BuyCoinConfigModel {
    @ApiModelProperty(value = "id")
    Long id;
    @ApiModelProperty(value = "小饼干")
    int coins;
    @ApiModelProperty(value = "小饼干对应的人民币，单位分")
    int rmbs;
    @ApiModelProperty(value = "标题")
    String title;
    @ApiModelProperty(value = "排序")
    int orderIndex;
    @ApiModelProperty(value = "苹果内购，产品的id")
    String productId;
    @ApiModelProperty(value = "type平台类型：IOS:苹果，ANDROID：安卓, WECHATMINAPP：微信小程序")
    String type;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

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
