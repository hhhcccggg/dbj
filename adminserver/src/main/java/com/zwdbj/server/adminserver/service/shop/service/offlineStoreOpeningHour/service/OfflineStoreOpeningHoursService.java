package com.zwdbj.server.adminserver.service.shop.service.offlineStoreOpeningHour.service;

import com.zwdbj.server.adminserver.service.shop.service.offlineStoreOpeningHour.model.OfflineStoreOpeningHours;
import com.zwdbj.server.basemodel.model.ServiceStatusInfo;

import java.util.List;

public interface OfflineStoreOpeningHoursService {

    ServiceStatusInfo<Long> create(OfflineStoreOpeningHours OfflineStoreOpeningHour);

    ServiceStatusInfo<Long> deleteById(long id,long storeId);

    ServiceStatusInfo<Long> update(OfflineStoreOpeningHours OfflineStoreOpeningHour);

    ServiceStatusInfo<List<OfflineStoreOpeningHours>> select();

    ServiceStatusInfo<OfflineStoreOpeningHours> selectById(Long id);

    ServiceStatusInfo<List<OfflineStoreOpeningHours>> select(long storeId);

}
