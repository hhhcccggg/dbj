package com.zwdbj.server.shop_admin_service.offlineStoreExtraServices.service;

import com.zwdbj.server.shop_admin_service.offlineStoreExtraServices.mapper.OfflineStoreExtraServicesMapper;
import com.zwdbj.server.shop_admin_service.offlineStoreExtraServices.model.OfflineStoreExtraServices;
import com.zwdbj.server.utility.common.UniqueIDCreater;
import com.zwdbj.server.utility.model.ServiceStatusInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional
public class OfflineStoreExtraServicesServiceImpl implements OfflineStoreExtraServicesService {

    @Resource
    private OfflineStoreExtraServicesMapper mapper;

    @Override
    public ServiceStatusInfo<Long> create(OfflineStoreExtraServices offlineStoreExtraServices) {
        Long result = 0L;
        Long id= UniqueIDCreater.generateID();
        try {
            result = mapper.create(id,offlineStoreExtraServices);
            return new ServiceStatusInfo<>(0, "", result);
        } catch (Exception e) {
            return new ServiceStatusInfo<>(1, "创建门店其他服务失败" + e.getMessage(), result);
        }


    }

    @Override
    public ServiceStatusInfo<Long> update(OfflineStoreExtraServices offlineStoreExtraServices) {
        Long result = 0L;
        try {
            result = mapper.update(offlineStoreExtraServices);
            return new ServiceStatusInfo<>(0, "", result);
        } catch (Exception e) {
            return new ServiceStatusInfo<>(1, "修改门店其他服务失败" + e.getMessage(), result);
        }

    }

    @Override
    public ServiceStatusInfo<Long> deleteById(Long id) {
        Long result = 0L;
        try {
            result = mapper.deleteById(id);
            return new ServiceStatusInfo<>(0, "", result);
        } catch (Exception e) {
            return new ServiceStatusInfo<>(1, "删除门店其他服务失败" + e.getMessage(), result);
        }

    }

    @Override
    public ServiceStatusInfo<List<OfflineStoreExtraServices>> select() {
        List<OfflineStoreExtraServices> result = null;
        try {
            result = mapper.select();
            return new ServiceStatusInfo<>(0, "", result);
        } catch (Exception e) {
            return new ServiceStatusInfo<>(1, "查询所有门店其他服务失败" + e.getMessage(), result);
        }

    }

    @Override
    public ServiceStatusInfo<OfflineStoreExtraServices> selectById(Long id) {
        OfflineStoreExtraServices result = null;
        try {
            result = mapper.selectById(id);
            return new ServiceStatusInfo<>(0, "", result);
        } catch (Exception e) {
            return new ServiceStatusInfo<>(1, "通过id查询门店其他服务失败" + e.getMessage(), result);
        }
    }
}
