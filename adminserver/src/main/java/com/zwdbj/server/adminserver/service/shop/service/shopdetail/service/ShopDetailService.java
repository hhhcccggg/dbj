package com.zwdbj.server.adminserver.service.shop.service.shopdetail.service;

import com.zwdbj.server.adminserver.service.category.model.StoreServiceCategory;
import com.zwdbj.server.adminserver.service.shop.service.shopdetail.model.OpeningHours;
import com.zwdbj.server.adminserver.service.shop.service.shopdetail.model.QualificationInput;
import com.zwdbj.server.adminserver.service.shop.service.shopdetail.model.StoreDto;
import com.zwdbj.server.utility.model.ServiceStatusInfo;
import com.zwdbj.server.adminserver.service.shop.service.shopdetail.model.LocationInfo;
import java.util.List;

public interface ShopDetailService {
    ServiceStatusInfo<StoreDto> findStoreDetail(long storeId);

    ServiceStatusInfo<List<OpeningHours>> findOpeningHours(long storeId);

    ServiceStatusInfo<Long> modifyOpeningHours(List<OpeningHours> list);

    ServiceStatusInfo<Long> addOpeningHours(long storeId, List<OpeningHours> list);

    ServiceStatusInfo<LocationInfo> showLocation(long storeId);

    ServiceStatusInfo<List<StoreServiceCategory>> findExtraService(long stordId);

    ServiceStatusInfo<List<StoreServiceCategory>> findAllExtraService(long stordId);

    ServiceStatusInfo<Long> modifyExtraService(long storeId, List<StoreServiceCategory> list);

    ServiceStatusInfo<List<StoreServiceCategory>> findServiceScope(long storeId);

    ServiceStatusInfo<Long> modifyServiceScopes(long storeId, List<StoreServiceCategory> list);

    ServiceStatusInfo<Long> uploadCheck(QualificationInput qualificationInput, long legalSubjectId);

    ServiceStatusInfo<Long> modifylocation(LocationInfo info, long storeId);
}
