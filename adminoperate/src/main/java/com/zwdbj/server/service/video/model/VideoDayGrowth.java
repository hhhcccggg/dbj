package com.zwdbj.server.service.video.model;

import java.util.Date;

public class VideoDayGrowth {
    Date createTime;
    int growthed;

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public int getGrowthed() {
        return growthed;
    }

    public void setGrowthed(int growthed) {
        this.growthed = growthed;
    }

}
