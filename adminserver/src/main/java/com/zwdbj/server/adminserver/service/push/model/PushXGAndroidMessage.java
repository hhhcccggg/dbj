package com.zwdbj.server.adminserver.service.push.model;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class PushXGAndroidMessage implements Serializable {
    private String title;
    private String content;
    private PushAndroidDevice android;
    @ApiModelProperty(value = "消息类型0:系统消息,1:点赞类2:粉丝类3:评论4:关注人发布视频5:关注人发布直播")
    private int type;
    @ApiModelProperty(notes = "设置点击通知栏之后的行为，默认为打开app")
    private ClickAction action;


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

    public PushAndroidDevice getAndroid() {
        return android;
    }

    public void setAndroid(PushAndroidDevice android) {
        this.android = android;
    }

    public ClickAction getAction() {
        return action;
    }

    public void setAction(ClickAction action) {
        this.action = action;
    }
}
