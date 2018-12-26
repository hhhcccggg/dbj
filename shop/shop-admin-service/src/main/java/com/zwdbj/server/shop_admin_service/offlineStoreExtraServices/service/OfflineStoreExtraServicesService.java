package com.zwdbj.server.shop_admin_service.offlineStoreExtraServices.service;

import com.zwdbj.server.shop_admin_service.offlineStoreExtraServices.model.OfflineStoreExtraServices;
import com.zwdbj.server.utility.model.ServiceStatusInfo;

import java.util.List;

public interface OfflineStoreExtraServicesService {
    ServiceStatusInfo<Long> create(OfflineStoreExtraServices offlineStoreExtraServices);

    ServiceStatusInfo<Long> update(OfflineStoreExtraServices offlineStoreExtraServices);

    ServiceStatusInfo<Long> deleteById(Long id);

    ServiceStatusInfo<List<OfflineStoreExtraServices>> select();

    ServiceStatusInfo<List<OfflineStoreExtraServices>> selectByofflineStoreId(Long offlineStoreId);


}
