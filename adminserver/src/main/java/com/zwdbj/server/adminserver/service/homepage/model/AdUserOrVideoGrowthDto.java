package com.zwdbj.server.adminserver.service.homepage.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@ApiModel(description = "用户或者短视频增长量")
public class AdUserOrVideoGrowthDto {
    @ApiModelProperty(value = "用户或者短视频增长量")
    Long growthed;
    @ApiModelProperty(value = "创建时间")
    Date createTime;

    public Long getGrowthed() {
        return growthed;
    }

    public void setGrowthed(Long growthed) {
        this.growthed = growthed;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime=new Date(createTime.getTime()+28800000);
    }
}
