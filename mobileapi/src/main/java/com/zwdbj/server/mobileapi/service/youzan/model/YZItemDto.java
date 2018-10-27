package com.zwdbj.server.mobileapi.service.youzan.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "有赞商品信息")
public class YZItemDto {
    @ApiModelProperty(value = "商品数字ID")
    long id;
    @ApiModelProperty(value = "商品别名")
    String alias;
    @ApiModelProperty(value = "商品标题")
    String title;
    @ApiModelProperty(value = "商品价格，单位分")
    int price;
    @ApiModelProperty(value = "上家编码，商家给商品设置的商家编码")
    String no;
    @ApiModelProperty(value = "总库存")
    int quantity;
    @ApiModelProperty(value = "运费类型，1是统一运费，2是运费模板")
    int postType;
    @ApiModelProperty(value = "运费，单位分。当postType=1时的运费")
    float postFee;
    @ApiModelProperty(value = "商品详情链接")
    String detailUrl;
    @ApiModelProperty(value = "商品划线价")
    String origin;
    @ApiModelProperty(value = "商品图片")
    String imageUrl;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getPostType() {
        return postType;
    }

    public void setPostType(int postType) {
        this.postType = postType;
    }

    public float getPostFee() {
        return postFee;
    }

    public void setPostFee(float postFee) {
        this.postFee = postFee;
    }

    public String getDetailUrl() {
        return detailUrl;
    }

    public void setDetailUrl(String detailUrl) {
        this.detailUrl = detailUrl;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
