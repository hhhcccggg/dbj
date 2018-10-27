package com.zwdbj.server.mobileapi.service.user.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

@ApiModel(description = "修改用户资料模型")
public class UpdateUserInfoInput {
    @ApiModelProperty(value = "用户头像七牛Key值，头像不修改，则传空")
    String avatarKey;
    @ApiModelProperty(value = "昵称")
    String nickName;
    @ApiModelProperty(value = "性别>>0：未知1：男2：女3：保密")
    int sex;
    @ApiModelProperty(value = "出生年月>>格式按照2018-09-10 00:00:00")
    Date birthday;
    @ApiModelProperty(value = "所在城市名")
    String city;
    @ApiModelProperty(value = "所在城市经度")
    float longitude;
    @ApiModelProperty(value = "所在城市维度")
    float latitude;
    @ApiModelProperty("职位ID，当前忽略此字段，传0即可")
    int occupationId;
    @ApiModelProperty("情感状态ID，当前忽略此字段，传0即可")
    int loveStatusId;

    public String getAvatarKey() {
        return avatarKey;
    }

    public void setAvatarKey(String avatarKey) {
        this.avatarKey = avatarKey;
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

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public int getOccupationId() {
        return occupationId;
    }

    public void setOccupationId(int occupationId) {
        this.occupationId = occupationId;
    }

    public int getLoveStatusId() {
        return loveStatusId;
    }

    public void setLoveStatusId(int loveStatusId) {
        this.loveStatusId = loveStatusId;
    }
}
