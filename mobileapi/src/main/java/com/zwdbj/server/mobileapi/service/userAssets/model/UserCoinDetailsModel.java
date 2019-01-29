package com.zwdbj.server.mobileapi.service.userAssets.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

@ApiModel(description = "用户小饼干明细")
public class UserCoinDetailsModel implements Serializable {
    @ApiModelProperty(value = "id")
    long id;
    @ApiModelProperty(value = "创建日期")
    Date createTime;
    @ApiModelProperty(value = "标题")
    String title;
    @ApiModelProperty(value = "小饼干数")
    int num;
    @ApiModelProperty(value = "附加数据")
    String extraData;
    @ApiModelProperty(value = "TASK:任务;PAY:充值;INCOME:收益;OTHER:其他 提现;ENCASH")
    String type;
    @ApiModelProperty(value = "userId")
    long userId;
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = new Date(createTime.getTime() - 28800000);
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

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
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
