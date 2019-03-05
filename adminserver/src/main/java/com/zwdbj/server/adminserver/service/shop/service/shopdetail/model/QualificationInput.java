package com.zwdbj.server.adminserver.service.shop.service.shopdetail.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "店铺资质审核上传信息")
public class QualificationInput {

    @ApiModelProperty(value = "需要审核的主体对象,ID身份证,CORP营业执照,BusinessID:经营许可证")
    private String identifyId;
    @ApiModelProperty(value = "标题")
    private String title;
    @ApiModelProperty(value = "主体对象的编码")
    private String keyId;
    @ApiModelProperty(value = "数据内容(多个内容用,隔开)")
    private String reviewData;
    @ApiModelProperty(value = "审核状态,前端上传默认为1")
    private int status;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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
}
