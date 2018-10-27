package com.zwdbj.server.adminserver.service.living.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

@ApiModel(description = "直播举报数据")
public class AdLivingComplainInfoDto {
    @ApiModelProperty(value = "举报信息ID")
    long id;
    @ApiModelProperty(value = "创建的时间")
    Date createTime;
    @ApiModelProperty(value = "举报来源")
    int fromTypeId;
    @ApiModelProperty(value = "举报用户ID")
    long fromUserId;
    @ApiModelProperty(value = "举报用户昵称")
    String nickName;
    @ApiModelProperty(value = "举报用户名称")
    String userName;
    @ApiModelProperty(value = "被举报直播ID")
    Long toResId;
    @ApiModelProperty(value = "被举报直播标题")
    String title;
    @ApiModelProperty(value = "被举报类型ID")
    Long toResTypeId;
    @ApiModelProperty(value = "举报原因ID")
    Long reasonId;
    @ApiModelProperty(value = "举报原因")
    String complainReason;
    @ApiModelProperty(value = "举报描述")
    String description;
    @ApiModelProperty(value = "截图url")
    String snapshotUrl;
    @ApiModelProperty(value = "举报次数")
    int  complainCount;

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
        this.createTime = createTime;
    }

    public int getFromTypeId() {
        return fromTypeId;
    }

    public void setFromTypeId(int fromTypeId) {
        this.fromTypeId = fromTypeId;
    }

    public long getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(long fromUserId) {
        this.fromUserId = fromUserId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getToResId() {
        return toResId;
    }

    public void setToResId(Long toResId) {
        this.toResId = toResId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getToResTypeId() {
        return toResTypeId;
    }

    public void setToResTypeId(Long toResTypeId) {
        this.toResTypeId = toResTypeId;
    }

    public Long getReasonId() {
        return reasonId;
    }

    public void setReasonId(Long reasonId) {
        this.reasonId = reasonId;
    }

    public String getComplainReason() {
        return complainReason;
    }

    public void setComplainReason(String complainReason) {
        this.complainReason = complainReason;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSnapshotUrl() {
        return snapshotUrl;
    }

    public void setSnapshotUrl(String snapshotUrl) {
        this.snapshotUrl = snapshotUrl;
    }

    public int getComplainCount() {
        return complainCount;
    }

    public void setComplainCount(int complainCount) {
        this.complainCount = complainCount;
    }
}
