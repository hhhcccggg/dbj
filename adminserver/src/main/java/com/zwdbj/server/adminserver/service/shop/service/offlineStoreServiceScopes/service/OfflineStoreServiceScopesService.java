package com.zwdbj.server.adminserver.service.shop.service.offlineStoreServiceScopes.service;

import com.zwdbj.server.adminserver.service.shop.service.offlineStoreServiceScopes.model.OfflineStoreServiceScopes;
import com.zwdbj.server.basemodel.model.ServiceStatusInfo;

import java.util.List;

public interface OfflineStoreServiceScopesService {

    ServiceStatusInfo<Long> create(OfflineStoreServiceScopes offlineStoreServiceScopes, long legalSubjectId);

    ServiceStatusInfo<Long> update(OfflineStoreServiceScopes offlineStoreServiceScopes, long legalSubjectId);

    ServiceStatusInfo<Long> deleteById(long serviceScopeId, long legalSubjectId);

    ServiceStatusInfo<List<OfflineStoreServiceScopes>> select();

    ServiceStatusInfo<List<OfflineStoreServiceScopes>> selectByofflineStoreId(Long offlineStoreId);

    ServiceStatusInfo<List<String>> selectCateNameByofflineStoreId(Long offlineStoreId);


}
