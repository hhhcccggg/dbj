package com.zwdbj.server.adminserver.service.push.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.ArrayList;

public class PushXGMessage implements Serializable {

    private static final long serialVersionUID = 3151474364488740298L;
    private String audience_type;
    private String platform;
    private Message message;
    private String message_type;
    private ArrayList<String> account_list;
    @JsonProperty("environment")
    @ApiModelProperty(value = "用户指定推送环境，仅限iOS平台推送使用")
    private Environment environment = Environment.product;
    private int badge_type;
    private String push_id;



    public PushXGMessage() {
        this.message_type = "notify";
        this.badge_type=-2;
    }

    public String getPush_id() {
        return push_id;
    }

    public void setPush_id(String push_id) {
        this.push_id = push_id;
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

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public String getMessage_type() {
        return message_type;
    }

    public void setMessage_type(String message_type) {
        this.message_type = message_type;
    }

    public ArrayList<String> getAccount_list() {
        return account_list;
    }

    public void setAccount_list(ArrayList<String> account_list) {
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
