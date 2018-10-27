package com.zwdbj.server.adminserver.service.comment.model;

import com.zwdbj.server.adminserver.model.BaseModel;

public class CommentModel extends BaseModel<Long> {
    long userId;
    boolean isOwner;
    String contentTxt;
    long refCommentId;
    long heartCount;
    long resourceOwnerId;
    int resourceTypeId;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public boolean isOwner() {
        return isOwner;
    }

    public void setOwner(boolean owner) {
        isOwner = owner;
    }

    public String getContentTxt() {
        return contentTxt;
    }

    public void setContentTxt(String contentTxt) {
        this.contentTxt = contentTxt;
    }

    public long getRefCommentId() {
        return refCommentId;
    }

    public void setRefCommentId(long refCommentId) {
        this.refCommentId = refCommentId;
    }

    public long getHeartCount() {
        return heartCount;
    }

    public void setHeartCount(long heartCount) {
        this.heartCount = heartCount;
    }

    public long getResourceOwnerId() {
        return resourceOwnerId;
    }

    public void setResourceOwnerId(long resourceOwnerId) {
        this.resourceOwnerId = resourceOwnerId;
    }

    public int getResourceTypeId() {
        return resourceTypeId;
    }

    public void setResourceTypeId(int resourceTypeId) {
        this.resourceTypeId = resourceTypeId;
    }
}
