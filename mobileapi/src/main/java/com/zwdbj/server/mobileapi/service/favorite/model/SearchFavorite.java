package com.zwdbj.server.mobileapi.service.favorite.model;

import com.zwdbj.server.mobileapi.service.favorite.common.TargetType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "搜索收藏")
public class SearchFavorite {

    @ApiModelProperty("LAGALSUBJECT:商家 STORE:店铺 PRODUCTSKU:商品")
    private TargetType targetType;
    @ApiModelProperty(value = "用户iD",hidden = true)
    private long userId;

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
