package com.zwdbj.server.mobileapi.service.userAssets.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "充值金币的数量和状态")
public class UserAssetNumAndStatus {
    @ApiModelProperty(value = "id")
    long id;
    @ApiModelProperty(value = "userId")
    long userId;
    @ApiModelProperty(value = "num")
    int num;
    @ApiModelProperty(value = "status")
    String status;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
