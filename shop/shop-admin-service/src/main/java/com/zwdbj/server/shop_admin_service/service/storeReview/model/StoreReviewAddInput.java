package com.zwdbj.server.shop_admin_service.service.storeReview.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(
        description = "增加店铺的认证资料"
)
public class StoreReviewAddInput {
    @ApiModelProperty("认证资料标识(如身份证，营业执照等等的标识)")
    String identifyId;
    @ApiModelProperty("认证资料的名字(如身份证，营业执照等等)")
    String title;
    @ApiModelProperty("认证资料的数据(如身份证的照片，营业执照的照片等等)")
    String reviewData;
    @ApiModelProperty("商户(线下门店)ID")
    long businessSellerId;

    public StoreReviewAddInput() {
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

    public long getBusinessSellerId() {
        return this.businessSellerId;
    }

    public void setBusinessSellerId(long businessSellerId) {
        this.businessSellerId = businessSellerId;
    }
}
