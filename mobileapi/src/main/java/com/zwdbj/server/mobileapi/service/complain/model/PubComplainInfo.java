package com.zwdbj.server.mobileapi.service.complain.model;

public class PubComplainInfo extends PubComplainInput {

    long id;
    /**
     * 0：默认1：匿名2：七牛
     */
    int fromTypeId;
    /**
     * 举报者
     */
    long fromUserId;

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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
