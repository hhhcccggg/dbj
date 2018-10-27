package com.zwdbj.server.adminserver.service.user.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

@ApiModel(description = "被举报用户的详细举报信息输出字段")
public class AdUserComplainInfoDto {
    @ApiModelProperty(value = "举报者的userName")
    String userName;
    @ApiModelProperty(value = "举报原因")
    String complainReason;
    @ApiModelProperty(value = "举报截图")
    String snapshotUrl;
    @ApiModelProperty(value = "创建时间")
    Date createTime;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getComplainReason() {
        return complainReason;
    }

    public void setComplainReason(String complainReason) {
        this.complainReason = complainReason;
    }

    public String getSnapshotUrl() {
        return snapshotUrl;
    }

    public void setSnapshotUrl(String snapshotUrl) {
        this.snapshotUrl = snapshotUrl;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
