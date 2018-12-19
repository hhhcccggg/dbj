package com.zwdbj.server.adminserver.service.push.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;

import java.io.Serializable;

public class Message implements Serializable {
    @JsonProperty("title")
    @ApiModelProperty(value = "消息标题",required = true)
    @ApiParam(required = true)
    private String title;

    @JsonProperty("content")
    @ApiModelProperty(value = "消息内容",required = true)
    private String content;

    @JsonProperty("accept_time")
//    private ArrayList<AcceptTimePair> accept_time;
    private Object accept_time;

    @JsonProperty("android")
    private PushXGAndroidMessage android;

    @JsonProperty("ios")
    private PushXGIOSMessage ios;

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

    public Object getAccept_time() {
        return accept_time;
    }

    public void setAccept_time(Object accept_time) {
        this.accept_time = accept_time;
    }

    public PushXGAndroidMessage getAndroid() {
        return android;
    }

    public void setAndroid(PushXGAndroidMessage android) {
        this.android = android;
    }

    public PushXGIOSMessage getIos() {
        return ios;
    }

    public void setIos(PushXGIOSMessage ios) {
        this.ios = ios;
    }
}
