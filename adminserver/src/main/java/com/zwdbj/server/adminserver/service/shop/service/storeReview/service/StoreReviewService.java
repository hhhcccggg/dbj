package com.zwdbj.server.adminserver.service.shop.service.storeReview.service;

import com.zwdbj.server.adminserver.service.shop.service.store.model.ReviewStoreInput;
import com.zwdbj.server.adminserver.service.shop.service.storeReview.model.BusinessSellerReviewModel;
import com.zwdbj.server.adminserver.service.shop.service.storeReview.model.StoreReviewAddInput;
import com.zwdbj.server.utility.model.ServiceStatusInfo;

import java.util.List;

public interface StoreReviewService {
    List<BusinessSellerReviewModel> findAllStoreReviews();
    ServiceStatusInfo<Integer> modifyStoreReview(long id, StoreReviewAddInput input);
    ServiceStatusInfo<Integer> addStoreReview(StoreReviewAddInput input);
    ServiceStatusInfo<Integer> deleteStoreReview(long id);
    ServiceStatusInfo<Integer> reviewStore(long legalSubjectId, ReviewStoreInput input);
    ServiceStatusInfo<Integer> notRealDeleteStoreReview(long id);
    ServiceStatusInfo<BusinessSellerReviewModel> getStoreReviewById(long businessSellerId);


}
