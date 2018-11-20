package com.zwdbj.server.adminserver.service.homepage.model;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "后台首页新增量输出字段")
public class AdFindIncreasedDto {
    @ApiModelProperty(value = "新增用户数量")
    Long userNum;
    @ApiModelProperty(value = "新增短视频数量")
    Long videoNum;
    @ApiModelProperty(value = "待审核短视频")
    Long verifingVideoNum;
    @ApiModelProperty(value = "新增订单")
    Long orderNum;
    @ApiModelProperty(value = "日活")
    long dau;
    @ApiModelProperty(value = "月活")
    long mau;

    public long getDau() {
        return dau;
    }

    public void setDau(long dau) {
        this.dau = dau;
    }

    public long getMau() {
        return mau;
    }

    public void setMau(long mau) {
        this.mau = mau;
    }

    public Long getUserNum() {
        return userNum;
    }

    public void setUserNum(Long userNum) {
        this.userNum = userNum;
    }

    public Long getVideoNum() {
        return videoNum;
    }

    public void setVideoNum(Long videoNum) {
        this.videoNum = videoNum;
    }

    public Long getVerifingVideoNum() {
        return verifingVideoNum;
    }

    public void setVerifingVideoNum(Long verifingVideoNum) {
        this.verifingVideoNum = verifingVideoNum;
    }

    public Long getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Long orderNum) {
        this.orderNum = orderNum;
    }
}
