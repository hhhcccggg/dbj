package com.zwdbj.server.adminserver.service.push.model;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class PushXGIOSMessage implements Serializable {

    private PushIosDevice ios;
    private String title;
    private String content;
    @ApiModelProperty(value = "消息类型0:系统消息,1:点赞类2:粉丝类3:评论4:关注人发布视频5:关注人发布直播")
    private int type;
    private String environment;

    public String getEnvironment() {
        return environment;
    }

    public void setEnvironment(String environment) {
        this.environment = environment;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

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

    public PushIosDevice getIos() {
        return ios;
    }

    public void setIos(PushIosDevice ios) {
        this.ios = ios;
    }
}
