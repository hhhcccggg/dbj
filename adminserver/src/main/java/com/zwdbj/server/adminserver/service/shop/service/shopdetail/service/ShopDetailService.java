package com.zwdbj.server.adminserver.service.shop.service.shopdetail.service;

import com.zwdbj.server.adminserver.service.category.model.StoreServiceCategory;
import com.zwdbj.server.adminserver.service.shop.service.shopdetail.model.*;
import com.zwdbj.server.utility.model.ServiceStatusInfo;

import java.util.List;

public interface ShopDetailService {
    ServiceStatusInfo<StoreDto> findStoreDetail(long legalSubjectId);

    ServiceStatusInfo<List<OpeningHours>> findOpeningHours(long legalSubjectId);

    ServiceStatusInfo<Long> modifyOpeningHours(List<OpeningHours> list, long storeId, long legalSubjectId);

    ServiceStatusInfo<Long> addOpeningHours(List<OpeningHours> list, long storeId);

    ServiceStatusInfo<LocationInfo> showLocation(long legalSubjectId);

    ServiceStatusInfo<List<StoreServiceCategory>> findExtraService(long legalSubjectId);

    ServiceStatusInfo<List<StoreServiceCategory>> findAllExtraService(long legalSubjectId);

    ServiceStatusInfo<Long> modifyExtraService(long storeId, long legalSubjectId, List<StoreServiceCategory> list);

    ServiceStatusInfo<List<StoreServiceCategory>> findServiceScope(long legalSubjectId);

    ServiceStatusInfo<Long> modifyServiceScopes(long storeId, long legalSubjectId, List<StoreServiceCategory> list);

    ServiceStatusInfo<Long> uploadCheck(QualificationInput qualificationInput, long legalSubjectId);

    ServiceStatusInfo<Long> modifylocation(LocationInfo info, long storeId);

    ServiceStatusInfo<ShopInfo> storeDetailInfo(long legalSubjectId);
}
