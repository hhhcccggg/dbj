package com.zwdbj.server.mobileapi.service.favorite.model;

import com.zwdbj.server.mobileapi.service.favorite.common.TargetType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Min;

@ApiModel(description = "收藏")
public class FavoriteDto {

    @ApiModelProperty("商家 店铺 商品的ID")
    @Min(1)
    private long targetId;
    @ApiModelProperty("LAGALSUBJECT:商家 STORE:店铺 PRODUCTSKU:商品")
    private TargetType targetType;
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

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}
