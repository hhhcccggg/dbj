package com.zwdbj.server.adminserver.service.push.model;

import java.io.Serializable;

public class PushXGIOSMessage implements Serializable {
    private String aps;
    private PushXGExtraMessage custom;
    private String title;
    private String content;

    public String getAps() {
        return aps;
    }

    public void setAps(String aps) {
        this.aps = aps;
    }

    public PushXGExtraMessage getCustom() {
        return custom;
    }

    public void setCustom(PushXGExtraMessage custom) {
        this.custom = custom;
    }

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

}
