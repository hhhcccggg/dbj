package com.zwdbj.server.adminserver.service.shop.service.offlineStoreOpeningHour.service;

import com.zwdbj.server.adminserver.middleware.mq.ESUtil;
import com.zwdbj.server.adminserver.service.shop.service.offlineStoreOpeningHour.mapper.OfflineStoreOpeningHoursMapper;
import com.zwdbj.server.adminserver.service.shop.service.offlineStoreOpeningHour.model.OfflineStoreOpeningHours;
import com.zwdbj.server.basemodel.model.ServiceStatusInfo;
import com.zwdbj.server.utility.common.UniqueIDCreater;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class OfflineStoreOpeningHoursServiceImpl implements OfflineStoreOpeningHoursService {

    @Autowired
    private ESUtil esUtil;
    @Resource
    private OfflineStoreOpeningHoursMapper offlineStoreOpeningHourMapper;

    @Transactional
    @Override
    public ServiceStatusInfo<Long> create(OfflineStoreOpeningHours offlineStoreOpeningHours) {
        Long id = UniqueIDCreater.generateID();
        Long result = 0L;
        try {
            result = offlineStoreOpeningHourMapper.create(id, offlineStoreOpeningHours);
            esUtil.QueueWorkInfoModelSend(offlineStoreOpeningHours.getStoreId(), "shop", "u");
            return new ServiceStatusInfo<>(0, "", result);
        } catch (Exception e) {
            return new ServiceStatusInfo<>(1, "创建营业时间失败" + e.getMessage(), result);
        }
    }

    @Transactional
    @Override
    public ServiceStatusInfo<Long> deleteById(long id, long storeId) {
        Long result = 0L;
        try {
            result = offlineStoreOpeningHourMapper.deleteById(id);
            esUtil.QueueWorkInfoModelSend(storeId, "shop", "u");
            return new ServiceStatusInfo<>(0, "", result);
        } catch (Exception e) {
            return new ServiceStatusInfo<>(1, "删除营业时间失败" + e.getMessage(), result);
        }
    }

    @Transactional
    @Override
    public ServiceStatusInfo<Long> update(OfflineStoreOpeningHours offlineStoreOpeningHours) {
        Long result = 0L;
        try {
            result = offlineStoreOpeningHourMapper.update(offlineStoreOpeningHours);
            esUtil.QueueWorkInfoModelSend(offlineStoreOpeningHours.getStoreId(), "shop", "u");
            return new ServiceStatusInfo<>(0, "", result);
        } catch (Exception e) {
            return new ServiceStatusInfo<>(1, "修改营业时间失败" + e.getMessage(), result);
        }
    }

    @Override
    public ServiceStatusInfo<List<OfflineStoreOpeningHours>> select() {
        List<OfflineStoreOpeningHours> list = null;
        try {
            list = offlineStoreOpeningHourMapper.select1();
            return new ServiceStatusInfo(0, "", list);
        } catch (Exception e) {
            return new ServiceStatusInfo(1, "查询营业时间失败" + e.getMessage(), list);
        }
    }

    @Override
    public ServiceStatusInfo<List<OfflineStoreOpeningHours>> select(long storeId) {
        List<OfflineStoreOpeningHours> list = null;
        try {
            list = offlineStoreOpeningHourMapper.select(storeId);
            return new ServiceStatusInfo(0, "", list);
        } catch (Exception e) {
            return new ServiceStatusInfo(1, "查询店铺营业时间失败" + e.getMessage(), list);
        }
    }

    @Override
    public ServiceStatusInfo<OfflineStoreOpeningHours> selectById(Long id) {
        OfflineStoreOpeningHours offlineStoreOpeningHours = null;
        try {
            offlineStoreOpeningHours = offlineStoreOpeningHourMapper.selectById(id);
            return new ServiceStatusInfo(0, "", offlineStoreOpeningHours);
        } catch (Exception e) {
            return new ServiceStatusInfo(1, "通过id时间营业数据失败" + e.getMessage(), null);
        }

    }
}
