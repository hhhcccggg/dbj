package com.zwdbj.server.adminserver.service.shop.service.store.model;

import com.zwdbj.server.adminserver.service.shop.service.storeReview.model.BusinessSellerReviewModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@ApiModel
public class StoreDetailDto extends StoreInfo {
    @ApiModelProperty(value = "店铺资质的图片")
    private List<BusinessSellerReviewModel> businessSellerReviewModels;

    public List<BusinessSellerReviewModel> getBusinessSellerReviewModels() {
        return businessSellerReviewModels;
    }

    public void setBusinessSellerReviewModels(List<BusinessSellerReviewModel> businessSellerReviewModels) {
        this.businessSellerReviewModels = businessSellerReviewModels;
    }
}
