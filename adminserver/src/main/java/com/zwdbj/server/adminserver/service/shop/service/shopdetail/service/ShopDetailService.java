package com.zwdbj.server.adminserver.service.shop.service.shopdetail.service;

import com.zwdbj.server.adminserver.service.category.model.StoreServiceCategory;
import com.zwdbj.server.adminserver.service.shop.service.shopdetail.model.*;
import com.zwdbj.server.basemodel.model.ServiceStatusInfo;

import java.util.List;

public interface ShopDetailService {
    ServiceStatusInfo<StoreDto> findStoreDetail(long legalSubjectId);


    ServiceStatusInfo<Long> modifyOpeningHours(List<OpeningHours> list, long legalSubjectId);


    ServiceStatusInfo<LocationInfo> showLocation(long legalSubjectId);


    ServiceStatusInfo<Long> modifyExtraService(long storeId, long legalSubjectId, List<StoreServiceCategory> list);


    ServiceStatusInfo<Long> modifyServiceScopes(long storeId, long legalSubjectId, List<StoreServiceCategory> list);

    ServiceStatusInfo<Long> uploadCheck(QualificationInput qualificationInput, long legalSubjectId);

    ServiceStatusInfo<Long> modifylocation(LocationInfo info, long storeId);

    ServiceStatusInfo<ShopInfo> storeDetailInfo(long legalSubjectId);

    ServiceStatusInfo<Long> modifyStoreImage(StoreImage storeImage, long legalSubjectId);
}
