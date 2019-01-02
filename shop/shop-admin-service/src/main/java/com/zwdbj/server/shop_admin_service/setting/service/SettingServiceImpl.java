package com.zwdbj.server.shop_admin_service.setting.service;

import com.zwdbj.server.shop_admin_service.setting.mapper.SettingMapper;
import com.zwdbj.server.shop_admin_service.setting.model.ExtraServices;
import com.zwdbj.server.shop_admin_service.setting.model.OpeningHour;
import com.zwdbj.server.shop_admin_service.setting.model.ServiceScopes;
import com.zwdbj.server.utility.model.ServiceStatusInfo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class SettingServiceImpl implements SettingService {

    @Autowired
    private SettingMapper settingMapper;

    @Override
    public ServiceStatusInfo<OpeningHour> selectOpenHour(long offlineStoreId) {
        OpeningHour result = null;
        try {
            result = settingMapper.selectOpenHour(offlineStoreId);
            return new ServiceStatusInfo<>(0, "", result);
        } catch (Exception e) {
            return new ServiceStatusInfo<>(1, "查询营业时间失败" + e.getMessage(), result);
        }
    }

    @Override
    public ServiceStatusInfo<List<ServiceScopes>> selectServiceScopes(long offlineStoreId) {
        List<ServiceScopes> result = null;

        try {
            result = settingMapper.selectServiceScopes(offlineStoreId);
            return new ServiceStatusInfo<>(0, "", result);
        } catch (Exception e) {
            return new ServiceStatusInfo<>(1, "查询服务范围失败" + e.getMessage(), null);
        }
    }

    @Override
    public ServiceStatusInfo<List<ExtraServices>> selectExtraServices(long offlineStoreId) {
        List<ExtraServices> result = null;
        try {
            result = settingMapper.selectExtraServices(offlineStoreId);
            return new ServiceStatusInfo<>(0, "", result);
        } catch (Exception e) {
            return new ServiceStatusInfo<>(1, "查询额外服务失败" + e.getMessage(), null);
        }
    }

    @Override
    public ServiceStatusInfo<Long> updateOpenHour(long offlineStoreId, OpeningHour openingHour) {
        Long result = 0L;
        try {
            result = settingMapper.updateOpenHour(offlineStoreId, openingHour);
            return new ServiceStatusInfo<>(0, "", result);
        } catch (Exception e) {
            return new ServiceStatusInfo<>(1, "修改营业时间失败" + e.getMessage(), null);
        }
    }
}
