package com.zwdbj.server.adminserver.service.shop.service.storeReview.service;

import com.zwdbj.server.adminserver.service.shop.service.store.model.ReviewStoreInput;
import com.zwdbj.server.adminserver.service.shop.service.storeReview.model.BusinessSellerReviewModel;
import com.zwdbj.server.adminserver.service.shop.service.storeReview.model.StoreReviewAddInput;
import com.zwdbj.server.basemodel.model.ServiceStatusInfo;

import java.util.List;

public interface StoreReviewService {
    List<BusinessSellerReviewModel> findAllStoreReviews();
    ServiceStatusInfo<Integer> modifyStoreReview(long id, StoreReviewAddInput input);
    ServiceStatusInfo<Integer> addStoreReview(StoreReviewAddInput input);
    ServiceStatusInfo<Integer> deleteStoreReview(long id);

    /**
     * 审核属于legalSubjectId的所有资料
     * @param legalSubjectId
     * @param input
     * @return
     */
    ServiceStatusInfo<Integer> reviewStore(long legalSubjectId, ReviewStoreInput input);

    ServiceStatusInfo<Integer> notRealDeleteStoreReview(long id);
    ServiceStatusInfo<List<BusinessSellerReviewModel>> getStoreReviewById(long legalSubjectId);

}
