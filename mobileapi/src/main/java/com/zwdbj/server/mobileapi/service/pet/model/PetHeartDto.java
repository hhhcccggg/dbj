package com.zwdbj.server.mobileapi.service.pet.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "宠物点赞的返回结果")
public class PetHeartDto {
    @ApiModelProperty(value = "宠物id")
    long petId;
    @ApiModelProperty(value = "该用户是否点赞 true为已经点赞，false为没点赞")
    boolean isHeart;

    public long getPetId() {
        return petId;
    }

    public void setPetId(long petId) {
        this.petId = petId;
    }

    public boolean isHeart() {
        return isHeart;
    }

    public void setHeart(boolean heart) {
        isHeart = heart;
    }
}
