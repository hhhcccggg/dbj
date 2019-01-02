package com.zwdbj.server.shop_admin_service.service.shopdetail.service;

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
}
