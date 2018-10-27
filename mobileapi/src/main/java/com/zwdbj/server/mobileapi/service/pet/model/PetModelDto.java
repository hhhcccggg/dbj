package com.zwdbj.server.mobileapi.service.pet.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

@ApiModel(description = "宠物信息")
public class PetModelDto {
    @ApiModelProperty(value = "宠物编号")
    long id;
    @ApiModelProperty(value = "宠物头像")
    String avatar;
    @ApiModelProperty(value = "昵称")
    String nickName;
    @ApiModelProperty(value = "出生日期")
    Date birthday;
    @ApiModelProperty(value = "年龄")
    float year;
    @ApiModelProperty(value = "性别：0：未知1:GG 2:MM 3:保密")
    int sex;
    @ApiModelProperty(value = "分类/品种")
    long categoryId;
    String categoryName;
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getSex() {
        return sex;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }

    public float getYear() {
        Date nowDate = new Date();
        long diff = nowDate.getTime() - getBirthday().getTime();
        float yr = diff/(24*60*60*1000.0f)/365.0f;
        year = (float)Math.ceil(yr);
        return year;
    }

    public String getCategoryName() {
        if (getCategoryId() ==0) {
            categoryName = "未知";
        }
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
