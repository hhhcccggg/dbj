package com.zwdbj.server.adminserver.service.shop.service.offlineStoreServiceScopes.service;

import com.zwdbj.server.adminserver.QueueUtil;
import com.zwdbj.server.adminserver.service.shop.service.offlineStoreServiceScopes.mapper.OfflineStoreServiceScopesMapper;
import com.zwdbj.server.adminserver.service.shop.service.offlineStoreServiceScopes.model.OfflineStoreServiceScopes;
import com.zwdbj.server.probuf.middleware.mq.QueueWorkInfoModel;
import com.zwdbj.server.utility.common.UniqueIDCreater;
import com.zwdbj.server.utility.model.ServiceStatusInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service

public class OfflineStoreServiceScopesServiceImpl implements OfflineStoreServiceScopesService {
    @Resource
    private OfflineStoreServiceScopesMapper mapper;

    @Transactional
    @Override
    public ServiceStatusInfo<Long> create(OfflineStoreServiceScopes offlineStoreServiceScopes) {
        Long result = 0L;
        Long id = UniqueIDCreater.generateID();
        try {
            result = mapper.create(id, offlineStoreServiceScopes);
            QueueUtil.sendQueue(offlineStoreServiceScopes.getStoreId(), QueueWorkInfoModel.QueueWorkModifyShopInfo.OperationEnum.UPDATE);
            return new ServiceStatusInfo<>(0, "", result);
        } catch (Exception e) {
            return new ServiceStatusInfo<>(1, "创建线下门店服务范围失败" + e.getMessage(), result);
        }

    }

    @Transactional
    @Override
    public ServiceStatusInfo<Long> update(OfflineStoreServiceScopes offlineStoreServiceScopes) {

        Long result = 0L;

        try {
            result = mapper.update(offlineStoreServiceScopes);
            QueueUtil.sendQueue(offlineStoreServiceScopes.getStoreId(), QueueWorkInfoModel.QueueWorkModifyShopInfo.OperationEnum.UPDATE);
            return new ServiceStatusInfo<>(0, "", result);
        } catch (Exception e) {
            return new ServiceStatusInfo<>(1, "修改线下门店服务范围失败" + e.getMessage(), result);
        }

    }


    @Transactional
    @Override
    public ServiceStatusInfo<Long> deleteById(Long id) {

        Long result = 0L;

        try {
            result = mapper.deleteById(id);
            QueueUtil.sendQueue(id, QueueWorkInfoModel.QueueWorkModifyShopInfo.OperationEnum.UPDATE);
            return new ServiceStatusInfo<>(0, "", result);
        } catch (Exception e) {
            return new ServiceStatusInfo<>(1, "删除线下门店服务范围失败" + e.getMessage(), result);
        }
    }

    @Transactional
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
    public ServiceStatusInfo<List<OfflineStoreServiceScopes>> selectByofflineStoreId(Long offlineStoreId) {
        List<OfflineStoreServiceScopes> offlineStoreServiceScopes = null;
        try {
            offlineStoreServiceScopes = mapper.selectByofflineStoreId(offlineStoreId);
            return new ServiceStatusInfo<>(0, "", offlineStoreServiceScopes);
        } catch (Exception e) {
            return new ServiceStatusInfo<>(1, "通过id查询线下门店服务范围失败" + e.getMessage(), null);
        }
    }

}
