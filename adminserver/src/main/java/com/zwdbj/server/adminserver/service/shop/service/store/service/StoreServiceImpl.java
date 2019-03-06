package com.zwdbj.server.adminserver.service.shop.service.store.service;

import com.zwdbj.server.adminserver.middleware.mq.ESUtil;
import com.zwdbj.server.adminserver.middleware.mq.QueueUtil;
import com.zwdbj.server.adminserver.service.shop.service.legalSubject.service.ILegalSubjectService;
import com.zwdbj.server.adminserver.service.shop.service.offlineStoreExtraServices.model.OfflineStoreExtraServices;
import com.zwdbj.server.adminserver.service.shop.service.offlineStoreExtraServices.service.OfflineStoreExtraServicesServiceImpl;
import com.zwdbj.server.adminserver.service.shop.service.offlineStoreOpeningHour.model.OfflineStoreOpeningHours;
import com.zwdbj.server.adminserver.service.shop.service.offlineStoreOpeningHour.service.OfflineStoreOpeningHoursServiceImpl;
import com.zwdbj.server.adminserver.service.shop.service.offlineStoreServiceScopes.model.OfflineStoreServiceScopes;
import com.zwdbj.server.adminserver.service.shop.service.offlineStoreServiceScopes.service.OfflineStoreServiceScopesServiceImpl;
import com.zwdbj.server.adminserver.service.shop.service.products.model.StoreProduct;
import com.zwdbj.server.adminserver.service.shop.service.products.service.ProductService;
import com.zwdbj.server.adminserver.service.shop.service.store.mapper.IStoreMapper;
import com.zwdbj.server.adminserver.service.shop.service.store.model.ReviewStoreInput;
import com.zwdbj.server.adminserver.service.shop.service.store.model.StoreInfo;
import com.zwdbj.server.adminserver.service.shop.service.store.model.StoreSearchInput;
import com.zwdbj.server.adminserver.service.shop.service.store.model.StoreSimpleInfo;
import com.zwdbj.server.adminserver.service.shop.service.storeReview.model.BusinessSellerReviewModel;
import com.zwdbj.server.adminserver.service.shop.service.storeReview.service.StoreReviewService;
import com.zwdbj.server.basemodel.model.ServiceStatusInfo;
import com.zwdbj.server.probuf.middleware.mq.QueueWorkInfoModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class StoreServiceImpl implements StoreService {

    @Autowired
    private IStoreMapper iStoreMapper;
    @Autowired
    private OfflineStoreExtraServicesServiceImpl extraServicesService;
    @Autowired
    private OfflineStoreOpeningHoursServiceImpl openingHoursService;
    @Autowired
    private OfflineStoreServiceScopesServiceImpl serviceScopesService;
    @Autowired
    private ProductService productServiceImpl;
    @Autowired
    ILegalSubjectService legalSubjectServiceImpl;
    @Autowired
    StoreReviewService storeReviewServiceImpl;

    @Override
    public ServiceStatusInfo<StoreSimpleInfo> selectByLegalSubjectId(long legalSubjectId) {
        try {
            StoreSimpleInfo id = iStoreMapper.selectByLegalSubjectId(legalSubjectId);
            return new ServiceStatusInfo<>(0, "", id);
        } catch (Exception e) {
            return new ServiceStatusInfo<>(1, "查询失败" + e.getMessage(), null);
        }
    }

    @Override
    public ServiceStatusInfo<List<StoreSimpleInfo>> searchStore(StoreSearchInput input) {
        try {
            List<StoreSimpleInfo> storeSimpleInfos = this.iStoreMapper.searchStore(input);
            for (StoreSimpleInfo info : storeSimpleInfos) {
                ServiceStatusInfo<List<String>> serviceScopes = serviceScopesService.selectCateNameByofflineStoreId(info.getId());
                if (serviceScopes.getData() != null && serviceScopes.getData().size() != 0)
                    info.setServiceScopes(serviceScopes.getData().toString());
            }
            return new ServiceStatusInfo<>(0, "", storeSimpleInfos);
        } catch (Exception e) {
            return new ServiceStatusInfo<>(1, "查询失败" + e.getMessage(), null);
        }
    }

    @Override
    public ServiceStatusInfo<StoreInfo> selectByStoreId(long storeId) {
        StoreInfo storeInfo = null;
        try {
            storeInfo = iStoreMapper.selectByStoreId(storeId);
            if (storeInfo == null) return new ServiceStatusInfo<>(1, "查询失败", null);
            List<OfflineStoreExtraServices> extraServices = extraServicesService.selectByofflineStoreId(storeId).getData();
            List<OfflineStoreOpeningHours> openingHours = openingHoursService.select(storeId).getData();
            List<OfflineStoreServiceScopes> serviceScopes = serviceScopesService.selectByofflineStoreId(storeId).getData();
            List<StoreProduct> storeProducts = productServiceImpl.selectProductByStoreId(storeId).getData();
            List<BusinessSellerReviewModel> businessSellerReviewModels = this.storeReviewServiceImpl.getStoreReviewById(storeInfo.getLegalSubjectId()).getData();
            if (openingHours != null && openingHours.size() != 0)
                storeInfo.setOpeningHours(openingHours);
            if (storeProducts != null && storeProducts.size() != 0)
                storeInfo.setStoreProducts(storeProducts);
            if (extraServices != null && extraServices.size() != 0)
                storeInfo.setExtraServices(extraServices);
            if (serviceScopes != null && serviceScopes.size() != 0)
                storeInfo.setServiceScopes(serviceScopes);
            if (businessSellerReviewModels != null && businessSellerReviewModels.size() != 0)
                storeInfo.setBusinessSellerReviewModels(businessSellerReviewModels);
            return new ServiceStatusInfo<>(0, "", storeInfo);
        } catch (Exception e) {
            return new ServiceStatusInfo<>(1, "查询失败" + e.getMessage(), storeInfo);
        }
    }

    public long selectTenantId(long legalSubjectId) {
        return iStoreMapper.selectTenantId(legalSubjectId);

    }

    @Override
    @Transactional
    public ServiceStatusInfo<Integer> updateStoreStatus(long storeId, long legalSubjectId, int state) {
        int result = this.iStoreMapper.updateStoreStatus(storeId, state);
        if (result == 0) return new ServiceStatusInfo<>(1, "店铺更新失败了", result);
        int s = this.legalSubjectServiceImpl.updateStatusById(legalSubjectId, state);
        if (s == 0) return new ServiceStatusInfo<>(1, "商家更新失败了", result);
        ESUtil.QueueWorkInfoModelSend(storeId, "shop", "u");
        return new ServiceStatusInfo<>(0, "", s);
    }

    @Override
    @Transactional
    public ServiceStatusInfo<Integer> reviewStore(long storeId, long legalSubjectId, ReviewStoreInput input) {
        int a = 0;
        //审核所有的需要审核的资料
        a = this.storeReviewServiceImpl.reviewStore(legalSubjectId, input).getData();

        //审核store
        a = this.iStoreMapper.reviewStore(storeId, input.isReviewOrNot());

        if (a == 0) return new ServiceStatusInfo<>(1, "店铺审核失败", 0);
        //审核 legalSubject
        a = this.legalSubjectServiceImpl.verityUnReviewedLegalSubject(legalSubjectId, input).getData();
        if (a == 0) return new ServiceStatusInfo<>(1, "商家审核失败", 0);

        ESUtil.QueueWorkInfoModelSend(storeId, "shop", "u");

        return new ServiceStatusInfo<>(0, "审核成功", a);
    }

    @Override
    public long selectStoreIdByLegalSubjectId(long legalSubjectId) {
        return iStoreMapper.selectStoreIdByLegalSubjectId(legalSubjectId);
    }
}