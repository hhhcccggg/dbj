package com.zwdbj.server.adminserver.service.push.model;


import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * 离线推送的消息
 */
public class PushMessage implements Serializable {
    private long pushId;
    private String msgContent;
    private String refUrl;
    private PushXGExtraMessage extraData;
    private String title;
    @ApiModelProperty(value = "消息类型0:系统消息,1:点赞类2:粉丝类3:评论4:关注人发布视频5:关注人发布直播")
    private int type;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public long getPushId() {
        return pushId;
    }

    public void setPushId(long pushId) {
        this.pushId = pushId;
    }

    public String getMsgContent() {
        return msgContent;
    }

    public void setMsgContent(String msgContent) {
        this.msgContent = msgContent;
    }

    public String getRefUrl() {
        return refUrl;
    }

    public void setRefUrl(String refUrl) {
        this.refUrl = refUrl;
    }

    public PushXGExtraMessage getExtraData() {
        return extraData;
    }

    public void setExtraData(PushXGExtraMessage extraData) {
        this.extraData = extraData;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
