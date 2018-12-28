package com.zwdbj.server.shop_admin_service.setting.service;

import com.zwdbj.server.shop_admin_service.setting.model.ExtraServices;
import com.zwdbj.server.shop_admin_service.setting.model.OpeningHour;
import com.zwdbj.server.shop_admin_service.setting.model.ServiceScopes;
import com.zwdbj.server.utility.model.ServiceStatusInfo;

import java.util.List;

public interface SettingService {
    ServiceStatusInfo<OpeningHour> selectOpenHour(long offlineStoreId);

    ServiceStatusInfo<List<ServiceScopes>> selectServiceScopes(long offlineStoreId);

    ServiceStatusInfo<List<ExtraServices>> selectExtraServices(long offlineStoreId);

    ServiceStatusInfo<Long> updateOpenHour(long offlineStoreId, OpeningHour openingHour);


}
