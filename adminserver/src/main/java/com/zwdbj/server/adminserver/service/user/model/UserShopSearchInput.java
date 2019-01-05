package com.zwdbj.server.adminserver.service.user.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "商家员工搜索字段")
public class UserShopSearchInput {

    @ApiModelProperty(value = "用户认证状态,-1:全部,0:未认证,1:认证")
    int isReviewed;

    @ApiModelProperty(value = "代言人状态,-1:全部,0:否,1:是")
    int isRepresent;

    @ApiModelProperty(value = "搜索的用户昵称")
    String username;

    public int getIsRepresent() {
        return isRepresent;
    }

    public void setIsRepresent(int isRepresent) {
        this.isRepresent = isRepresent;
    }

    public int getIsReviewed() {
        return isReviewed;
    }

    public void setIsReviewed(int isReviewed) {
        this.isReviewed = isReviewed;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
