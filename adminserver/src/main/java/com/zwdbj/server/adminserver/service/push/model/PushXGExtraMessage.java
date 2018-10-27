package com.zwdbj.server.adminserver.service.push.model;

public class PushXGExtraMessage {
    /**
     * 消息类型0:系统消息,1:点赞类2:粉丝类3:评论4:关注人发布视频5:关注人发布直播
     */
    private int messageType;

    /**
     * 资源编号
     */
    private long resId;

    /**
     * 资源类型0：视频
     */
    private int resType;

    public int getMessageType() {
        return messageType;
    }

    public void setMessageType(int messageType) {
        this.messageType = messageType;
    }

    public long getResId() {
        return resId;
    }

    public void setResId(long resId) {
        this.resId = resId;
    }

    public int getResType() {
        return resType;
    }

    public void setResType(int resType) {
        this.resType = resType;
    }
}
