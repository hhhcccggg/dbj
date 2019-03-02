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
    @ApiModelProperty(value = "该用户是否收藏,true为已经收藏，false为没收藏")
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


}
