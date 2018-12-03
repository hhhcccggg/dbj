package com.zwdbj.server.shop_admin_service.storeReview.service;

import com.zwdbj.server.shop_admin_service.storeReview.model.BusinessSellerReviewModel;
import com.zwdbj.server.shop_admin_service.storeReview.model.StoreReviewAddInput;
import com.zwdbj.server.utility.model.ServiceStatusInfo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface StoreReviewService {
    List<BusinessSellerReviewModel> findAllStoreReviews();
    ServiceStatusInfo<Integer> modifyStoreReview(long id,StoreReviewAddInput input);
    ServiceStatusInfo<Integer> addStoreReview(StoreReviewAddInput input);
    ServiceStatusInfo<Integer> deleteStoreReview(long id);
    ServiceStatusInfo<Integer> notRealDeleteStoreReview(long id);
    ServiceStatusInfo<BusinessSellerReviewModel> getStoreReviewById(long businessSellerId);

}
