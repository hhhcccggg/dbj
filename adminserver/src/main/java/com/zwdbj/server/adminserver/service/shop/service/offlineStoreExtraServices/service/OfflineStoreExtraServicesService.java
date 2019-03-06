package com.zwdbj.server.adminserver.service.shop.service.offlineStoreExtraServices.service;

import com.zwdbj.server.adminserver.service.shop.service.offlineStoreExtraServices.model.OfflineStoreExtraServices;
import com.zwdbj.server.basemodel.model.ServiceStatusInfo;

import java.util.List;

public interface OfflineStoreExtraServicesService {
    ServiceStatusInfo<Long> create(OfflineStoreExtraServices offlineStoreExtraServices);

    ServiceStatusInfo<Long> update(OfflineStoreExtraServices offlineStoreExtraServices);

    ServiceStatusInfo<Long> deleteById(Long extraServiceId, long storeId);

    ServiceStatusInfo<List<OfflineStoreExtraServices>> select();

    ServiceStatusInfo<List<OfflineStoreExtraServices>> selectByofflineStoreId(Long offlineStoreId);


}
