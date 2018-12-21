package com.zwdbj.server.adminserver.service.userAssets.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "用户金币明细")
public class UserCoinDetail {
    @ApiModelProperty(value = "id")
    Long id;
    @ApiModelProperty(value = "金币数")
    Integer num;
    @ApiModelProperty(value = "标题")
    String title;
    @ApiModelProperty(value = "附加数据")
    String extraData;

    @ApiModelProperty(value = "获得渠道")
    String type;

    @ApiModelProperty(value = "用户Id")
    Long userId;
    @ApiModelProperty(value = "状态: SUCCESS:成功；FAILED：失败; PROCESSING：处理中")
    String status;
    @ApiModelProperty(value = "状态原因说明")
    String statusMsg;

    @ApiModelProperty(value = "微信或者支付宝流水号")
    String tradeNo;
    @ApiModelProperty(value = "ta")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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
