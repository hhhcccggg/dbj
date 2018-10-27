package com.zwdbj.server.mobileapi.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "点赞")
public class HeartInput {
    @ApiModelProperty(value = "资源ID")
    long id;
    @ApiModelProperty(value = "true为点赞，false为取消点赞")
    boolean isHeart;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isHeart() {
        return isHeart;
    }

    public void setHeart(boolean heart) {
        isHeart = heart;
    }
}
