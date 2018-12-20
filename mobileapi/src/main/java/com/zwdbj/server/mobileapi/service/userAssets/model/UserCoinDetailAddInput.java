package com.zwdbj.server.mobileapi.service.userAssets.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel(description = "增加用户金币明细")
public class UserCoinDetailAddInput implements Serializable {
    @ApiModelProperty(value = "标题")
    String title;
    @ApiModelProperty(value = "金币数")
    int num;
    @ApiModelProperty(value = "附加数据")
    String extraData;
    @ApiModelProperty(value = "TASK:任务;PAY:充值;INCOME:收益;OTHER:其他")
    String type;
    @ApiModelProperty(value = "SUCCESS:成功；FAILED：失败; PROCESSING：处理中")
    String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public UserCoinDetailAddInput() {
        this.status = "PROCESSING";
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getExtraData() {
        return extraData;
    }

    public void setExtraData(String extraData) {
        this.extraData = extraData;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
