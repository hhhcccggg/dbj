package com.zwdbj.server.adminserver.service.push.model;

import java.io.Serializable;

public class PushIosDevice implements Serializable {
    private String aps;
    private PushXGExtraMessage custom;

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
}
