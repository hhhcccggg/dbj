package com.zwdbj.server.shop_admin_service.service.storeReview.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

@ApiModel(
        description = "店铺认证资料&审核情况"
)
public class BusinessSellerReviewModel {
    @ApiModelProperty("id")
    long id;
    @ApiModelProperty("创建时间")
    Date createTime;
    @ApiModelProperty("认证资料标识(如身份证，营业执照等等的标识)")
    String identifyId;
    @ApiModelProperty("认证资料的名字(如身份证，营业执照等等)")
    String title;
    @ApiModelProperty("认证资料的数据(如身份证的照片，营业执照的照片等等)")
    String reviewData;
    @ApiModelProperty("审核的状态")
    int status;
    @ApiModelProperty("拒绝的原因")
    String rejectMsg;
    @ApiModelProperty("商户(线下门店)ID")
    long businessSellerId;

    public BusinessSellerReviewModel() {
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getIdentifyId() {
        return this.identifyId;
    }

    public void setIdentifyId(String identifyId) {
        this.identifyId = identifyId;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getReviewData() {
        return this.reviewData;
    }

    public void setReviewData(String reviewData) {
        this.reviewData = reviewData;
    }

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getRejectMsg() {
        return this.rejectMsg;
    }

    public void setRejectMsg(String rejectMsg) {
        this.rejectMsg = rejectMsg;
    }

    public long getBusinessSellerId() {
        return this.businessSellerId;
    }

    public void setBusinessSellerId(long businessSellerId) {
        this.businessSellerId = businessSellerId;
    }
}
