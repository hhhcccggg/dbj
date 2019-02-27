package com.zwdbj.server.adminserver.service.shop.service.store.service;

import com.zwdbj.server.adminserver.service.shop.service.store.model.*;
import com.zwdbj.server.adminserver.service.shop.service.store.model.StoreInfo;
import com.zwdbj.server.adminserver.service.shop.service.store.model.StoreSearchInput;
import com.zwdbj.server.adminserver.service.shop.service.store.model.StoreSimpleInfo;
import com.zwdbj.server.utility.model.ServiceStatusInfo;

import java.util.List;

public interface StoreService {

    /**
     * 根据legalSubjectId查询
     *
     * @param legalSubjectId
     * @return
     */
    ServiceStatusInfo<StoreSimpleInfo> selectByLegalSubjectId(long legalSubjectId);

    ServiceStatusInfo<Integer> updateStoreStatus(long storeId, long legalSubjectId, int state);

    ServiceStatusInfo<Integer> reviewStore(long storeId, long legalSubjectId, ReviewStoreInput input);

    ServiceStatusInfo<StoreInfo> selectByStoreId(long storeId);

    long selectTenantId(long legalSubjectId);

    ServiceStatusInfo<List<StoreSimpleInfo>> searchStore(StoreSearchInput input);
}
