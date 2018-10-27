package com.zwdbj.server.adminserver.model;

import java.util.Date;

public class BaseModel<T> {
    T id;
    Date createTime;
    boolean isDeleted;
    boolean deleteTime;

    public T getId() {
        return id;
    }

    public void setId(T id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public boolean isDeleteTime() {
        return deleteTime;
    }

    public void setDeleteTime(boolean deleteTime) {
        this.deleteTime = deleteTime;
    }
}
