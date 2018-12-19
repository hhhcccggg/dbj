package com.zwdbj.server.adminserver.service.push.model;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class ClickAction implements Serializable {
    @ApiModelProperty(notes = "MyActivityClassName")
    private String activity = "";

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }
}
