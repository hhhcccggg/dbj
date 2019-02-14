package com.zwdbj.server.adminserver.service.shop.service.store.service;

import com.zwdbj.server.adminserver.service.shop.service.discountCoupon.service.DiscountCouponServiceImpl;
import com.zwdbj.server.adminserver.service.shop.service.offlineStoreExtraServices.model.OfflineStoreExtraServices;
import com.zwdbj.server.adminserver.service.shop.service.offlineStoreExtraServices.service.OfflineStoreExtraServicesServiceImpl;
import com.zwdbj.server.adminserver.service.shop.service.offlineStoreOpeningHour.model.OfflineStoreOpeningHours;
import com.zwdbj.server.adminserver.service.shop.service.offlineStoreOpeningHour.service.OfflineStoreOpeningHoursServiceImpl;
import com.zwdbj.server.adminserver.service.shop.service.offlineStoreServiceScopes.model.OfflineStoreServiceScopes;
import com.zwdbj.server.adminserver.service.shop.service.offlineStoreServiceScopes.service.OfflineStoreServiceScopesServiceImpl;
import com.zwdbj.server.adminserver.service.shop.service.shopdetail.model.DiscountCoupon;
import com.zwdbj.server.adminserver.service.shop.service.store.mapper.IStoreMapper;
import com.zwdbj.server.adminserver.service.shop.service.store.model.StoreInfo;
import com.zwdbj.server.utility.model.ServiceStatusInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    private DiscountCouponServiceImpl discountCouponService;

    @Override
    public ServiceStatusInfo<Long> selectByLegalSubjectId(long legalSubjectId) {
        try {
            Long id = iStoreMapper.selectByLegalSubjectId(legalSubjectId);
            return new ServiceStatusInfo<>(0, "", id);
        } catch (Exception e) {
            return new ServiceStatusInfo<>(1, "查询失败" + e.getMessage(), null);
        }
    }

    @Override
    public ServiceStatusInfo<StoreInfo> selectByStoreId(long storeId) {
        try {
            StoreInfo storeInfo = iStoreMapper.selectByStoreId(storeId);
            ServiceStatusInfo<List<OfflineStoreExtraServices>> extraServices = extraServicesService.selectByofflineStoreId(storeId);
            ServiceStatusInfo<List<OfflineStoreOpeningHours>> openingHours = openingHoursService.select(storeId);
            ServiceStatusInfo<List<OfflineStoreServiceScopes>> serviceScopes = serviceScopesService.selectByofflineStoreId(storeId);
//            ServiceStatusInfo<List<DiscountCoupon>> disCountCoupon=discountCouponService.
            return new ServiceStatusInfo<>(0, "", storeInfo);
        } catch (Exception e) {
            return new ServiceStatusInfo<>(1, "查询失败" + e.getMessage(), null);
        }
    }
}