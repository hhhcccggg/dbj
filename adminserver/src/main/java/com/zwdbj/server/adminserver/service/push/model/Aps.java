package com.zwdbj.server.adminserver.service.push.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Aps implements Serializable {
    @JsonProperty(value = "alert", required = true)
    private Alert alert;

    @JsonProperty(value = "badge_type", required = true)
    private int badge_type;

    public Alert getAlert() {
        return alert;
    }

    public void setAlert(Alert alert) {
        this.alert = alert;
    }

    public int getBadge_type() {
        return badge_type;
    }

    public void setBadge_type(int badge_type) {
        this.badge_type = badge_type;
    }
}
