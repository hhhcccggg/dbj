package com.zwdbj.server.mobileapi.service.favorite.model;

import com.zwdbj.server.mobileapi.service.favorite.common.TargetType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Min;

@ApiModel(description = "收藏")
public class FavoriteInput {
    @ApiModelProperty("店铺 ID")
    @Min(1)
    private long targetId;
    @ApiModelProperty("LAGALSUBJECT:商家 STORE:店铺 PRODUCTSKU:商品,现在只有店铺")
    private TargetType targetType;
    @ApiModelProperty(value = "true为收藏，false为取消收藏")
    boolean isFavorite;

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

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

}
