package com.zwdbj.server.mobileapi.service.pet.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

@ApiModel(description = "更新宠物信息模型")
public class UpdatePetModelInput {
    @ApiModelProperty(value = "编辑宠物的时候，设置宠物的ID，新增时传0")
    long id;
    @ApiModelProperty(value = "设置上传到七牛的key",required = true)
    String avatar;
    @ApiModelProperty(value = "宠物昵称")
    String nickName;
    @ApiModelProperty(value = "性别：0：未知1:GG 2:MM 3:保密")
    int sex;
    @ApiModelProperty(value = "宠物分类")
    long categoryId;
    @ApiModelProperty(value = "宠物生日,格式：2018-12-21 00:00:00")
    Date birthday;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }
}
