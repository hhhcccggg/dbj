package com.zwdbj.server.adminserver.service.push.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PushXGAndroidMessage implements Serializable {
    @ApiModelProperty(notes = "设置点击通知栏之后的行为，默认为打开app")
    private ClickAction action;
    @JsonProperty(value = "custom_content")
    private String custom_content;

    public ClickAction getAction() {
        return action;
    }

    public void setAction(ClickAction action) {
        this.action = action;
    }

    public String getCustom_content() {
        return custom_content;
    }

    public void setCustom_content(String custom_content) {
        this.custom_content = custom_content;
    }
}
