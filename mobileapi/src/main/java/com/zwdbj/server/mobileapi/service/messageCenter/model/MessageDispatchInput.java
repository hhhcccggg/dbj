package com.zwdbj.server.mobileapi.service.messageCenter.model;

public class MessageDispatchInput {
    protected long id;
    protected long refMessageId;
    protected long receivedUserId;
    /**
     * 0:未读1：已读2:删除
     */
    protected int status;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getRefMessageId() {
        return refMessageId;
    }

    public void setRefMessageId(long refMessageId) {
        this.refMessageId = refMessageId;
    }

    public long getReceivedUserId() {
        return receivedUserId;
    }

    public void setReceivedUserId(long receivedUserId) {
        this.receivedUserId = receivedUserId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
