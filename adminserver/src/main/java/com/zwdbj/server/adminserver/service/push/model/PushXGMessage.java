package com.zwdbj.server.adminserver.service.push.model;

import java.io.Serializable;

public class PushXGMessage implements Serializable {

    private String audience_type;
    private String platform;
    private Object message;
    private String message_type;
    private Object account_list;
    private String environment;
    private int badge_type;

    public PushXGMessage() {
        this.message_type = "notify";
        this.badge_type=-2;
    }

    public String getAudience_type() {
        return audience_type;
    }

    public void setAudience_type(String audience_type) {
        this.audience_type = audience_type;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public Object getMessage() {
        return message;
    }

    public void setMessage(Object message) {
        this.message = message;
    }

    public String getMessage_type() {
        return message_type;
    }

    public void setMessage_type(String message_type) {
        this.message_type = message_type;
    }

    public Object getAccount_list() {
        return account_list;
    }

    public void setAccount_list(Object account_list) {
        this.account_list = account_list;
    }

    public String getEnvironment() {
        return environment;
    }

    public void setEnvironment(String environment) {
        this.environment = environment;
    }

    public int getBadge_type() {
        return badge_type;
    }

    public void setBadge_type(int badge_type) {
        this.badge_type = badge_type;
    }
}
