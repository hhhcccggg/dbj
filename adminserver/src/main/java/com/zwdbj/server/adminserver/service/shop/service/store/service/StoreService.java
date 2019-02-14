package com.zwdbj.server.adminserver.service.shop.service.store.service;

import com.zwdbj.server.adminserver.service.shop.service.store.model.StoreInfo;
import com.zwdbj.server.utility.model.ServiceStatusInfo;

public interface StoreService {

    /**
     * 根据legalSubjectId查询
     *
     * @param legalSubjectId
     * @return
     */
    ServiceStatusInfo<Long> selectByLegalSubjectId(long legalSubjectId);

    ServiceStatusInfo<StoreInfo> selectByStoreId(long storeId);
}
