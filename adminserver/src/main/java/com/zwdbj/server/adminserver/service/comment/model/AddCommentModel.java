package com.zwdbj.server.adminserver.service.comment.model;

public class AddCommentModel extends AddCommentInput {
    long userId;
    long id;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
