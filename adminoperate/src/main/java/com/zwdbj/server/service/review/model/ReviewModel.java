package com.zwdbj.server.service.review.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel(description = "审核中间表的字段")
public class ReviewModel implements Serializable {
    @ApiModelProperty(value = "id")
    Long id;
    @ApiModelProperty(value = "七牛key")
    String  resContent;
    @ApiModelProperty(value = "0:七牛图片1:七牛视频")
    int resType;
    @ApiModelProperty(value = "关联数据ID")
    Long dataId;
    @ApiModelProperty(value = "关联数据相对应的表 0:用户头像1:宠物头像2:短视频")
    int dataType;
    @ApiModelProperty(value = "审核结果的类型 pass:通过block:拒绝revie:需要人工介入")
    String reviewResultType;
    @ApiModelProperty(value = "没有通过的原因")
    String reviewResultContent;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getResContent() {
        return resContent;
    }

    public void setResContent(String resContent) {
        this.resContent = resContent;
    }

    public int getResType() {
        return resType;
    }

    public void setResType(int resType) {
        this.resType = resType;
    }

    public Long getDataId() {
        return dataId;
    }

    public void setDataId(Long dataId) {
        this.dataId = dataId;
    }

    public int getDataType() {
        return dataType;
    }

    public void setDataType(int dataType) {
        this.dataType = dataType;
    }

    public String getReviewResultType() {
        return reviewResultType;
    }

    public void setReviewResultType(String reviewResultType) {
        this.reviewResultType = reviewResultType;
    }

    public String getReviewResultContent() {
        return reviewResultContent;
    }

    public void setReviewResultContent(String reviewResultContent) {
        this.reviewResultContent = reviewResultContent;
    }
}
