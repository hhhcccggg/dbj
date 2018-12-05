package com.zwdbj.server.shop_admin_service.offlineStoreServiceScopes.service;

import com.zwdbj.server.shop_admin_service.offlineStoreServiceScopes.mapper.OfflineStoreServiceScopesMapper;
import com.zwdbj.server.shop_admin_service.offlineStoreServiceScopes.model.OfflineStoreServiceScopes;
import com.zwdbj.server.utility.common.UniqueIDCreater;
import com.zwdbj.server.utility.model.ServiceStatusInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional
public class OfflineStoreServiceScopesServiceImpl implements OfflineStoreServiceScopesService {
    @Resource
    private OfflineStoreServiceScopesMapper mapper;

    @Override
    public ServiceStatusInfo<Long> create(OfflineStoreServiceScopes offlineStoreServiceScopes) {
        Long result = 0L;
        Long id = UniqueIDCreater.generateID();
        try {
            result = mapper.create(id, offlineStoreServiceScopes);
            return new ServiceStatusInfo<>(0, "", result);
        } catch (Exception e) {
            return new ServiceStatusInfo<>(1, "创建线下门店服务范围失败" + e.getMessage(), result);
        }

    }

    @Override
    public ServiceStatusInfo<Long> update(OfflineStoreServiceScopes offlineStoreServiceScopes) {

        Long result = 0L;

        try {
            result = mapper.update(offlineStoreServiceScopes);
            return new ServiceStatusInfo<>(0, "", result);
        } catch (Exception e) {
            return new ServiceStatusInfo<>(1, "修改线下门店服务范围失败" + e.getMessage(), result);
        }

    }




    @Override
    public ServiceStatusInfo<Long> deleteById(Long id) {

        Long result = 0L;

        try {
            result = mapper.deleteById(id);
            return new ServiceStatusInfo<>(0, "", result);
        } catch (Exception e) {
            return new ServiceStatusInfo<>(1, "删除线下门店服务范围失败" + e.getMessage(), result);
        }
    }

    @Override
    public ServiceStatusInfo<List<OfflineStoreServiceScopes>> select() {
        List<OfflineStoreServiceScopes> list = null;
        try {
            list = mapper.select();
            return new ServiceStatusInfo<>(0, "", list);
        } catch (Exception e) {
            return new ServiceStatusInfo<>(1, "查询所有线下门店服务范围失败" + e.getMessage(), null);

        }

    }

    @Override
    public ServiceStatusInfo<OfflineStoreServiceScopes> selectById(Long id) {
        OfflineStoreServiceScopes offlineStoreServiceScopes = null;
        try {
            offlineStoreServiceScopes = mapper.selectById(id);
            return new ServiceStatusInfo<>(0, "", offlineStoreServiceScopes);
        } catch (Exception e) {
            return new ServiceStatusInfo<>(1, "通过id查询线下门店服务范围失败" + e.getMessage(), null);
        }
    }

}
