package com.zwdbj.server.adminserver.service.push.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class PushXGMessage implements Serializable {

    private String audience_type;
    private String platform;
    private Object message;
    private String message_type;
    private Object account_list;
    @JsonProperty("environment")
    @ApiModelProperty(value = "用户指定推送环境，仅限iOS平台推送使用")
    private Environment environment = Environment.product;
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

    public Environment getEnvironment() {
        return environment;
    }

    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    public int getBadge_type() {
        return badge_type;
    }

    public void setBadge_type(int badge_type) {
        this.badge_type = badge_type;
    }
}
