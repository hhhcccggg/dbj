package com.zwdbj.server.mobileapi.service.userAssets.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


@ApiModel(description = "更改用户金币的明细状态")
public class UserCoinDetailModifyInput {
    @ApiModelProperty(value = "id")
    long id;
    @ApiModelProperty(value = "TASK:任务;PAY:充值;INCOME:收益;OTHER:其他")
    String type;
    @ApiModelProperty(value = "SUCCESS:成功；FAILED：失败; PROCESSING：处理中")
    String status;
    @ApiModelProperty(value = "状态原因说明")
    String statusMsg;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusMsg() {
        return statusMsg;
    }

    public void setStatusMsg(String statusMsg) {
        this.statusMsg = statusMsg;
    }
}
