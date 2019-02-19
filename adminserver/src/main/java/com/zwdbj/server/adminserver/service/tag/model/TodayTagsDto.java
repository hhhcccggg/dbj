package com.zwdbj.server.adminserver.service.tag.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel(description = "根据年月获得今日主题")
public class TodayTagsDto implements Serializable {
    @ApiModelProperty(value = "日期")
    private String date;
    @ApiModelProperty(value = "主题名称")
    private String name;
    @ApiModelProperty(value = "主题说明")
    private String desc;
    @ApiModelProperty(value = "主题参与人数")
    private long resNumber;
    @ApiModelProperty(value = "主题参与视频数")
    private long resVideoNumber;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public long getResNumber() {
        return resNumber;
    }

    public void setResNumber(long resNumber) {
        this.resNumber = resNumber;
    }

    public long getResVideoNumber() {
        return resVideoNumber;
    }

    public void setResVideoNumber(long resVideoNumber) {
        this.resVideoNumber = resVideoNumber;
    }
}
