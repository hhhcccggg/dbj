package com.zwdbj.server.adminserver.service.shop.service.offlineStoreOpeningHour.service;

import com.zwdbj.server.adminserver.service.shop.service.offlineStoreOpeningHour.model.OfflineStoreOpeningHours;
import com.zwdbj.server.utility.model.ServiceStatusInfo;

import java.util.List;

public interface OfflineStoreOpeningHoursService {

    ServiceStatusInfo<Long> create(OfflineStoreOpeningHours OfflineStoreOpeningHour);

    ServiceStatusInfo<Long> deleteById(Long id);

    ServiceStatusInfo<Long> update(OfflineStoreOpeningHours OfflineStoreOpeningHour);

    ServiceStatusInfo<List<OfflineStoreOpeningHours>> select();

    ServiceStatusInfo<OfflineStoreOpeningHours> selectById(Long id);


}
