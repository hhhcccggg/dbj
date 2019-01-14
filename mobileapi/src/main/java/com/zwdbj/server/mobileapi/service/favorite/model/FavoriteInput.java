package com.zwdbj.server.mobileapi.service.favorite.model;

import com.zwdbj.server.mobileapi.service.favorite.common.TargetType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "收藏")
public class FavoriteInput {
    private long targetId;
    @ApiModelProperty("LAGALSUBJECT:商家 STORE:店铺 PRODUCTSKU:商品")
    private TargetType targetType;
    @ApiModelProperty("名称")
    private String title;
    @ApiModelProperty("缩略图")
    private String imageUrl;
    @ApiModelProperty("价格 店铺、商家忽略")
    private long price;
    @ApiModelProperty(value = "用户id",hidden = true)
    private long userId;

    public long getTargetId() {
        return targetId;
    }

    public void setTargetId(long targetId) {
        this.targetId = targetId;
    }

    public TargetType getTargetType() {
        return targetType;
    }

    public void setTargetType(TargetType targetType) {
        this.targetType = targetType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}
