package com.zwdbj.server.adminserver.service.push.model;

import java.io.Serializable;

public class PushXGAndroidMessage implements Serializable {
    private String title;
    private String content;
    private PushXGExtraMessage custom;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public PushXGExtraMessage getCustom() {
        return custom;
    }

    public void setCustom(PushXGExtraMessage custom) {
        this.custom = custom;
    }
}
