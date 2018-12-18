package com.zwdbj.server.adminserver.service.push.model;

import java.io.Serializable;

public class PushDeviceType implements Serializable {
    private PushXGExtraMessage custom_content;

    public PushXGExtraMessage getCustom_content() {
        return custom_content;
    }

    public void setCustom_content(PushXGExtraMessage custom_content) {
        this.custom_content = custom_content;
    }
}
