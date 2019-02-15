package com.zwdbj.server.mobileapi.service.user.model;

import com.zwdbj.server.mobileapi.service.pet.model.PetModelDto;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

public class UserOnNearbyDto {

    @ApiModelProperty(value = "用户的id")
    private long userId;
    @ApiModelProperty(value = "用户的昵称")
    private String nickName;
    @ApiModelProperty(value = "用户的性别 0:未知,1男,2女,3保密")
    private int sex;
    @ApiModelProperty(value = "用户是否为认证医师")
    private String isDocter;
    @ApiModelProperty(value = "用户的宠物")
    private List<PetModelDto> petModelDtos;
    @ApiModelProperty(value = "用户的作品数")
    private int userVideoNums;
    @ApiModelProperty(value = "距离的距离长度 单位为米")
    private int distance;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getIsDocter() {
        return isDocter;
    }

    public void setIsDocter(String isDocter) {
        this.isDocter = isDocter;
    }

    public List<PetModelDto> getPetModelDtos() {
        return petModelDtos;
    }

    public void setPetModelDtos(List<PetModelDto> petModelDtos) {
        this.petModelDtos = petModelDtos;
    }

    public int getUserVideoNums() {
        return userVideoNums;
    }

    public void setUserVideoNums(int userVideoNums) {
        this.userVideoNums = userVideoNums;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }
}
