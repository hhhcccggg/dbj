package com.zwdbj.server.adminserver.service.shop.service.shopdetail.service;

import com.zwdbj.server.adminserver.service.category.model.StoreServiceCategory;
import com.zwdbj.server.adminserver.service.shop.service.shopdetail.model.OpeningHours;
import com.zwdbj.server.adminserver.service.shop.service.shopdetail.model.QualificationInput;
import com.zwdbj.server.adminserver.service.shop.service.shopdetail.model.StoreDto;
import com.zwdbj.server.utility.model.ServiceStatusInfo;
import com.zwdbj.server.adminserver.service.shop.service.shopdetail.model.LocationInfo;
import java.util.List;

public interface ShopDetailService {
    ServiceStatusInfo<StoreDto> findStoreDetail(long legalSubjectId);

    ServiceStatusInfo<List<OpeningHours>> findOpeningHours(long legalSubjectId);

    ServiceStatusInfo<Long> modifyOpeningHours(List<OpeningHours> list);

    ServiceStatusInfo<Long> addOpeningHours(List<OpeningHours> list);

    ServiceStatusInfo<LocationInfo> showLocation(long legalSubjectId);

    ServiceStatusInfo<List<StoreServiceCategory>> findExtraService(long legalSubjectId);

    ServiceStatusInfo<List<StoreServiceCategory>> findAllExtraService(long legalSubjectId);

    ServiceStatusInfo<Long> modifyExtraService(long legalSubjectId, List<StoreServiceCategory> list);

    ServiceStatusInfo<List<StoreServiceCategory>> findServiceScope(long legalSubjectId);

    ServiceStatusInfo<Long> modifyServiceScopes(long legalSubjectId, List<StoreServiceCategory> list);

    ServiceStatusInfo<Long> uploadCheck(QualificationInput qualificationInput, long legalSubjectId);

    ServiceStatusInfo<Long> modifylocation(LocationInfo info);
}
