package com.zwdbj.server.shop_admin_service.offlineStoreOpeningHour.service;

import com.zwdbj.server.shop_admin_service.offlineStoreOpeningHour.mapper.OfflineStoreOpeningHoursMapper;
import com.zwdbj.server.shop_admin_service.offlineStoreOpeningHour.model.OfflineStoreOpeningHours;
import com.zwdbj.server.utility.common.UniqueIDCreater;
import com.zwdbj.server.utility.model.ServiceStatusInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional
public class OfflineStoreOpeningHoursServiceImpl implements OfflineStoreOpeningHoursService {


    @Resource
    private OfflineStoreOpeningHoursMapper offlineStoreOpeningHourMapper;

    @Override
    public ServiceStatusInfo<Long> create(OfflineStoreOpeningHours offlineStoreOpeningHours) {
        Long id = UniqueIDCreater.generateID();
        Long result = 0L;
        try {
            result = offlineStoreOpeningHourMapper.create(id, offlineStoreOpeningHours);
            return new ServiceStatusInfo<>(0, "", result);
        } catch (Exception e) {
            return new ServiceStatusInfo<>(1, "创建营业数据失败"+e.getMessage(), result);
        }
    }

    @Override
    public ServiceStatusInfo<Long> deleteById(Long id) {
        Long result = 0L;
        try {
            result = offlineStoreOpeningHourMapper.deleteById(id);
            return new ServiceStatusInfo<>(0, "", result);
        } catch (Exception e) {
            return new ServiceStatusInfo<>(1, "删除营业数据失败"+e.getMessage(), result);
        }
    }

    @Override
    public ServiceStatusInfo<Long> update(OfflineStoreOpeningHours offlineStoreOpeningHours) {
        Long result = 0L;
        try {
            result = offlineStoreOpeningHourMapper.update(offlineStoreOpeningHours);
            return new ServiceStatusInfo<>(0, "", result);
        } catch (Exception e) {
            return new ServiceStatusInfo<>(1, "修改营业数据失败"+e.getMessage(), result);
        }
    }

    @Override
    public ServiceStatusInfo<List<OfflineStoreOpeningHours>> select() {
        List<OfflineStoreOpeningHours> list = null;
        try {
            list = offlineStoreOpeningHourMapper.select();
            return new ServiceStatusInfo(0, "", list);
        } catch (Exception e) {
            return new ServiceStatusInfo(1, "查询营业数据失败"+e.getMessage(), list);
        }
    }

    @Override
    public ServiceStatusInfo<OfflineStoreOpeningHours> selectById(Long id) {
        OfflineStoreOpeningHours offlineStoreOpeningHours = null;
        try {
            offlineStoreOpeningHours = offlineStoreOpeningHourMapper.selectById(id);
            return new ServiceStatusInfo(0, "", offlineStoreOpeningHours);
        } catch (Exception e) {
            return new ServiceStatusInfo(1, "通过id查询营业数据失败"+e.getMessage(), null);
        }

    }
}
