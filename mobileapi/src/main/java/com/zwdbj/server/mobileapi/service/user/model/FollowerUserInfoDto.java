package com.zwdbj.server.mobileapi.service.user.model;

import com.zwdbj.server.mobileapi.service.pet.model.PetModelDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@ApiModel(description = "关注用户信息")
public class FollowerUserInfoDto extends UserDetailInfoDto {
    @ApiModelProperty(value = "对方是否已关注我")
    protected boolean isFollowedMe;
    @ApiModelProperty(value = "宠物信息")
    protected List<PetModelDto> pets;

    public boolean isFollowedMe() {
        return isFollowedMe;
    }

    public void setFollowedMe(boolean followedMe) {
        isFollowedMe = followedMe;
    }

    public List<PetModelDto> getPets() {
        return pets;
    }

    public void setPets(List<PetModelDto> pets) {
        this.pets = pets;
    }
}
