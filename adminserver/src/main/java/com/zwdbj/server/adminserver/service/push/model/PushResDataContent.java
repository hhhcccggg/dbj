package com.zwdbj.server.adminserver.service.push.model;

/**
 * 每一个来自推送消息中携带的额外信息
 */
public class PushResDataContent {
    private String title;
    private long id;
    private int type;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
