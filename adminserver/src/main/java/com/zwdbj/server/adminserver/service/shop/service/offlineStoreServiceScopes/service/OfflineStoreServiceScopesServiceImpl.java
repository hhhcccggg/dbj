package com.zwdbj.server.adminserver.service.shop.service.offlineStoreServiceScopes.service;

import com.zwdbj.server.adminserver.middleware.mq.QueueUtil;
import com.zwdbj.server.adminserver.service.shop.service.offlineStoreServiceScopes.mapper.OfflineStoreServiceScopesMapper;
import com.zwdbj.server.adminserver.service.shop.service.offlineStoreServiceScopes.model.OfflineStoreServiceScopes;
import com.zwdbj.server.adminserver.service.shop.service.store.service.StoreService;
import com.zwdbj.server.probuf.middleware.mq.QueueWorkInfoModel;
import com.zwdbj.server.utility.common.UniqueIDCreater;
import com.zwdbj.server.basemodel.model.ServiceStatusInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service

public class OfflineStoreServiceScopesServiceImpl implements OfflineStoreServiceScopesService {
    @Resource
    private OfflineStoreServiceScopesMapper mapper;
    @Resource
    private StoreService storeService;

    @Transactional
    @Override
    public ServiceStatusInfo<Long> create(OfflineStoreServiceScopes offlineStoreServiceScopes, long legalSubjectId) {
        Long result = 0L;
        Long id = UniqueIDCreater.generateID();
        long storeId = storeService.selectStoreIdByLegalSubjectId(legalSubjectId);
        try {
            result = mapper.create(id, storeId, offlineStoreServiceScopes);
            QueueUtil.sendQueue(storeId, QueueWorkInfoModel.QueueWorkModifyShopInfo.OperationEnum.UPDATE);
            return new ServiceStatusInfo<>(0, "", result);
        } catch (Exception e) {
            return new ServiceStatusInfo<>(1, "创建线下门店服务范围失败" + e.getMessage(), result);
        }

    }

    @Transactional
    @Override
    public ServiceStatusInfo<Long> update(OfflineStoreServiceScopes offlineStoreServiceScopes, long legalSubjectId) {

        Long result = 0L;
        long storeId = storeService.selectStoreIdByLegalSubjectId(legalSubjectId);
        try {
            result = mapper.update(offlineStoreServiceScopes,storeId);
            QueueUtil.sendQueue(storeId, QueueWorkInfoModel.QueueWorkModifyShopInfo.OperationEnum.UPDATE);
            return new ServiceStatusInfo<>(0, "", result);
        } catch (Exception e) {
            return new ServiceStatusInfo<>(1, "修改线下门店服务范围失败" + e.getMessage(), result);
        }

    }


    @Transactional
    @Override
    public ServiceStatusInfo<Long> deleteById(long serviceScopeId, long legalSubjectId) {

        Long result = 0L;
        long storeId = storeService.selectStoreIdByLegalSubjectId(legalSubjectId);
        try {
            result = mapper.deleteById(serviceScopeId, storeId);
            QueueUtil.sendQueue(serviceScopeId, QueueWorkInfoModel.QueueWorkModifyShopInfo.OperationEnum.UPDATE);
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

    @Override
    public ServiceStatusInfo<List<String>> selectCateNameByofflineStoreId(Long offlineStoreId) {
        List<String> list = mapper.selectCateNameByofflineStoreId(offlineStoreId);
        return new ServiceStatusInfo<>(0, "", list);
    }
}
