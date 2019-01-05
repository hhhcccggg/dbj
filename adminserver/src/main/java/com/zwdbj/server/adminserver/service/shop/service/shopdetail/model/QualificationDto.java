package com.zwdbj.server.adminserver.service.shop.service.shopdetail.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "店铺资质信息")
public class QualificationDto {
    @ApiModelProperty(value = "id")
    long id;
    @ApiModelProperty(value = "需要审核的主体对象")//ID身份证,CORP营业执照
            String identifyId;
    @ApiModelProperty(value = "标题")
    String title;
    @ApiModelProperty(value = "主体对象的编码")
    String keyId;
    @ApiModelProperty(value = "数据内容")//多个内容用,隔开
            String reviewData;
    @ApiModelProperty(value = "审核状态")//0正常,1审核中,2拒接
            int status;
    @ApiModelProperty(value = "拒接原因")
    String rejectMsg;
    @ApiModelProperty(value = "商户id")
    long legalSubjectId;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getIdentifyId() {
        return identifyId;
    }

    public void setIdentifyId(String identifyId) {
        this.identifyId = identifyId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getKeyId() {
        return keyId;
    }

    public void setKeyId(String keyId) {
        this.keyId = keyId;
    }

    public String getReviewData() {
        return reviewData;
    }

    public void setReviewData(String reviewData) {
        this.reviewData = reviewData;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getRejectMsg() {
        return rejectMsg;
    }

    public void setRejectMsg(String rejectMsg) {
        this.rejectMsg = rejectMsg;
    }

    public long getLegalSubjectId() {
        return legalSubjectId;
    }

    public void setLegalSubjectId(long legalSubjectId) {
        this.legalSubjectId = legalSubjectId;
    }
}


