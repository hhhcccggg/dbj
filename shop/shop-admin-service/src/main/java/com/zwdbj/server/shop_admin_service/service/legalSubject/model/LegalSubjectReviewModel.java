package com.zwdbj.server.shop_admin_service.service.legalSubject.model;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

@Api(description = "主体需要审核的资料")
public class LegalSubjectReviewModel {
    @ApiModelProperty(value = "id")
    private long id;
    @ApiModelProperty(value = "审核的资料创建时间")
    private Date createTime;
    @ApiModelProperty(value = "需要审核的主体对象,ID:身份证,CORP:营业执照")
    private String identifyId;
    @ApiModelProperty(value = "需要审核的资料的标题")
    private String title;
    @ApiModelProperty(value = "需要审核的主体对象的编码")
    private String keyId;
    @ApiModelProperty(value = "多个相同类型的数据内容用 , 隔开")
    private String reviewData;
    @ApiModelProperty(value = "状态:0正常1：审核中2：拒接")
    private int status;
    @ApiModelProperty(value = "审核说明")
    private String reviewMsg;
    @ApiModelProperty(value = "商户Id")
    private long legalSubjectId;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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

    public String getReviewMsg() {
        return reviewMsg;
    }

    public void setReviewMsg(String reviewMsg) {
        this.reviewMsg = reviewMsg;
    }

    public long getLegalSubjectId() {
        return legalSubjectId;
    }

    public void setLegalSubjectId(long legalSubjectId) {
        this.legalSubjectId = legalSubjectId;
    }
}
