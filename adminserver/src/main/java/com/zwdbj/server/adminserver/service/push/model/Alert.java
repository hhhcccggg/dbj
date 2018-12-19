package com.zwdbj.server.adminserver.service.push.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class Alert implements Serializable {
    @JsonProperty(value = "title", required = true)
    private String title;

    @JsonProperty(value = "subtitle")
    @ApiModelProperty(notes = "The localized subtitle, containing a secondary description of the reason for the alert.")
    private String subtitle;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }
}
