package com.zwdbj.server.mobileapi.service.complain.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "提交举报信息")
public class PubComplainInput {
    /**
     * 被举报的资源ID：这里是指短视频||直播||用户的ID
     */
    @ApiModelProperty(value = "被举报的资源ID：这里是指短视频||直播||用户的ID")
    long toResId;
    /**
     * 被举报的资源类型0：用户1：视频2：直播
     */
    @ApiModelProperty(value = "被举报的资源类型0：用户1：视频2：直播")
    long toResTypeId;
    @ApiModelProperty(value = "举报原因ID")
    long reasonId;
    String description;
    @ApiModelProperty(value = "截图快照，当前只是支持上传一张，此字段设置为七牛返回的KEY")
    String snapshotKey;

    public long getToResId() {
        return toResId;
    }

    public void setToResId(long toResId) {
        this.toResId = toResId;
    }

    public long getToResTypeId() {
        return toResTypeId;
    }

    public void setToResTypeId(long toResTypeId) {
        this.toResTypeId = toResTypeId;
    }

    public long getReasonId() {
        return reasonId;
    }

    public void setReasonId(long reasonId) {
        this.reasonId = reasonId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSnapshotKey() {
        return snapshotKey;
    }

    public void setSnapshotKey(String snapshotKey) {
        this.snapshotKey = snapshotKey;
    }
}
