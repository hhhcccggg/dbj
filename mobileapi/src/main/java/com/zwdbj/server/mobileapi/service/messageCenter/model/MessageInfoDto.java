package com.zwdbj.server.mobileapi.service.messageCenter.model;

import com.zwdbj.server.mobileapi.utility.DateTimeFriendly;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

public class MessageInfoDto {
    protected long id;
    protected Date createTime;
    protected long creatorUserId;
    @ApiModelProperty(value = "文本消息")
    protected String msgContent;
    @ApiModelProperty(value = "该消息关联的数据体，JSON格式，暂时不可用")
    protected String dataContent;
    @ApiModelProperty(value = "该消息关联的H5网页")
    protected String refUrl;
    protected String createTimeValue;

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
        this.setCreateTimeValue(DateTimeFriendly.friendlyShow(this.createTime));
    }

    public long getCreatorUserId() {
        return creatorUserId;
    }

    public void setCreatorUserId(long creatorUserId) {
        this.creatorUserId = creatorUserId;
    }

    public String getMsgContent() {
        return msgContent;
    }

    public void setMsgContent(String msgContent) {
        this.msgContent = msgContent;
    }

    public String getDataContent() {
        return dataContent;
    }

    public void setDataContent(String dataContent) {
        this.dataContent = dataContent;
    }

    public String getRefUrl() {
        return refUrl;
    }

    public void setRefUrl(String refUrl) {
        this.refUrl = refUrl;
    }

    public String getCreateTimeValue() {
        return createTimeValue;
    }

    public void setCreateTimeValue(String createTimeValue) {
        this.createTimeValue = createTimeValue;
    }
}
