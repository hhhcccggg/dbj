package com.zwdbj.server.shop_admin_service.service.shopdetail.service;

import com.zwdbj.server.shop_admin_service.service.shopdetail.model.StoreServiceCategory;
import com.zwdbj.server.shop_admin_service.service.shopdetail.model.OpeningHours;
import com.zwdbj.server.shop_admin_service.service.shopdetail.model.StoreDto;
import com.zwdbj.server.utility.model.ServiceStatusInfo;

import java.util.List;

public interface ShopDetailService {
    ServiceStatusInfo<StoreDto> findStoreDetail(long storeId);

    ServiceStatusInfo<List<OpeningHours>> findOpeningHours(long storeId);

    ServiceStatusInfo<Long> modifyOpeningHours(List<OpeningHours> list);

    ServiceStatusInfo<Long> addOpeningHours(long storeId, List<OpeningHours> list);

    ServiceStatusInfo<String> showLocation(long storeId);

    ServiceStatusInfo<List<StoreServiceCategory>> findExtraService(long stordId);

    ServiceStatusInfo<List<StoreServiceCategory>> findAllExtraService(long stordId);

    ServiceStatusInfo<Long> modifyExtraService(long storeId, List<StoreServiceCategory> list);

    ServiceStatusInfo<List<StoreServiceCategory>> findServiceScope(long storeId);

    ServiceStatusInfo<Long> modifyServiceScopes(long storeId, List<StoreServiceCategory> list);
}
