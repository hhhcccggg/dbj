package com.zwdbj.server.adminserver.service.shop.service.store.service;

import com.zwdbj.server.adminserver.service.shop.service.discountCoupon.model.DiscountCouponModel;
import com.zwdbj.server.adminserver.service.shop.service.discountCoupon.service.DiscountCouponServiceImpl;
import com.zwdbj.server.adminserver.service.shop.service.legalSubject.service.ILegalSubjectService;
import com.zwdbj.server.adminserver.service.shop.service.offlineStoreExtraServices.model.OfflineStoreExtraServices;
import com.zwdbj.server.adminserver.service.shop.service.offlineStoreExtraServices.service.OfflineStoreExtraServicesServiceImpl;
import com.zwdbj.server.adminserver.service.shop.service.offlineStoreOpeningHour.model.OfflineStoreOpeningHours;
import com.zwdbj.server.adminserver.service.shop.service.offlineStoreOpeningHour.service.OfflineStoreOpeningHoursServiceImpl;
import com.zwdbj.server.adminserver.service.shop.service.offlineStoreServiceScopes.model.OfflineStoreServiceScopes;
import com.zwdbj.server.adminserver.service.shop.service.offlineStoreServiceScopes.service.OfflineStoreServiceScopesServiceImpl;
import com.zwdbj.server.adminserver.service.shop.service.shopdetail.model.DiscountCoupon;
import com.zwdbj.server.adminserver.service.shop.service.shopdetail.model.DiscountCouponDetail;
import com.zwdbj.server.adminserver.service.shop.service.store.mapper.IStoreMapper;
import com.zwdbj.server.adminserver.service.shop.service.store.model.StoreInfo;
import com.zwdbj.server.adminserver.service.shop.service.store.model.StoreSearchInput;
import com.zwdbj.server.adminserver.service.shop.service.store.model.StoreSimpleInfo;
import com.zwdbj.server.utility.model.ServiceStatusInfo;
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
    private DiscountCouponServiceImpl discountCouponService;
    @Autowired
    ILegalSubjectService legalSubjectServiceImpl;

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
    public ServiceStatusInfo<List<StoreSimpleInfo>> searchStore(StoreSearchInput input) {
        try {
            List<StoreSimpleInfo> storeSimpleInfos = this.iStoreMapper.searchStore(input);
            for (StoreSimpleInfo info:storeSimpleInfos){
                ServiceStatusInfo<List<String>> serviceScopes = serviceScopesService.selectCateNameByofflineStoreId(info.getId());
                if (serviceScopes.getData()!=null || serviceScopes.getData().size()!=0)
                info.setServiceScopes(serviceScopes.getData().toString());
            }
            return new ServiceStatusInfo<>(0, "", storeSimpleInfos);
        }catch (Exception e){
            return new ServiceStatusInfo<>(1, "查询失败" + e.getMessage(), null);
        }
    }

    @Override
    public ServiceStatusInfo<StoreInfo> selectByStoreId(long storeId) {
        StoreInfo storeInfo = null;
        try {
            storeInfo = iStoreMapper.selectByStoreId(storeId);
            ServiceStatusInfo<List<OfflineStoreExtraServices>> extraServices = extraServicesService.selectByofflineStoreId(storeId);
            ServiceStatusInfo<List<OfflineStoreOpeningHours>> openingHours = openingHoursService.select(storeId);
            ServiceStatusInfo<List<OfflineStoreServiceScopes>> serviceScopes = serviceScopesService.selectByofflineStoreId(storeId);
            ServiceStatusInfo<List<DiscountCouponModel>> disCountCoupon = discountCouponService.selectByStoreId(storeId);
            storeInfo.setOpeningHours(openingHours.getData());
            storeInfo.setDiscountCoupons(disCountCoupon.getData());
            storeInfo.setExtraServices(extraServices.getData());
            storeInfo.setServiceScopes(serviceScopes.getData());
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
    public ServiceStatusInfo<Integer> updateStoreStatus(long storeId,long legalSubjectId, int state) {
        int result = this.iStoreMapper.updateStoreStatus(storeId,state);
        if (result==0)return new ServiceStatusInfo<>(1,"店铺更新失败了",result);
        int s = this.legalSubjectServiceImpl.updateStatusById(legalSubjectId,state);
        if (s==0)return new ServiceStatusInfo<>(1,"商家更新失败了",result);
        return new ServiceStatusInfo<>(0,"",s);
    }
}