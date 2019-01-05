package com.zwdbj.server.adminserver.service.user.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

@ApiModel(value = "员工列表")
public class UserShopSelectInput {

    long id;

    @ApiModelProperty("是否已经认证:-1>>所有；0：未认证1：已认证")
    int isReviewed;

    @ApiModelProperty("是否是代言人")
    boolean represent;

    @ApiModelProperty("名称")
    String username;

    @ApiModelProperty("手机号码")
    String phone;

    @ApiModelProperty("添加时间")
    Date createTime;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getIsReviewed() {
        return isReviewed;
    }

    public void setIsReviewed(int isReviewed) {
        this.isReviewed = isReviewed;
    }

    public boolean isRepresent() {
        return represent;
    }

    public void setRepresent(boolean represent) {
        this.represent = represent;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
