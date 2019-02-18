package com.zwdbj.server.adminserver.service.shop.service.offlineStoreStaffs.service;

import com.zwdbj.server.adminserver.QueueUtil;
import com.zwdbj.server.adminserver.service.shop.service.offlineStoreStaffs.mapper.OfflineStoreStaffsMapper;
import com.zwdbj.server.adminserver.service.shop.service.offlineStoreStaffs.model.OfflineStoreStaffs;
import com.zwdbj.server.probuf.middleware.mq.QueueWorkInfoModel;
import com.zwdbj.server.utility.common.UniqueIDCreater;
import com.zwdbj.server.utility.model.ServiceStatusInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class OfflineStoreStaffsServiceImpl implements OfflineStoreStaffsService {

    @Resource
    private OfflineStoreStaffsMapper mapper;

    @Override
    public ServiceStatusInfo<Long> create(OfflineStoreStaffs offlineStoreStaffs) {
        Long id = UniqueIDCreater.generateID();
        Long result = 0L;
        try {
            result = mapper.create(id, offlineStoreStaffs);
            QueueUtil.sendQueue(offlineStoreStaffs.getStoreId(), QueueWorkInfoModel.QueueWorkModifyShopInfo.OperationEnum.UPDATE);
            return new ServiceStatusInfo<>(0, "", result);
        } catch (Exception e) {
            return new ServiceStatusInfo<>(1, "创建门店代言人失败" + e.getMessage(), result);
        }
    }

    @Override
    public ServiceStatusInfo<Long> update(OfflineStoreStaffs offlineStoreStaffs) {
        Long result = 0L;
        try {
            result = mapper.update(offlineStoreStaffs);
            QueueUtil.sendQueue(offlineStoreStaffs.getStoreId(), QueueWorkInfoModel.QueueWorkModifyShopInfo.OperationEnum.UPDATE);
            return new ServiceStatusInfo<>(0, "", result);
        } catch (Exception e) {
            return new ServiceStatusInfo<>(1, "修改门店代言人失败" + e.getMessage(), result);
        }
    }

    @Override
    public ServiceStatusInfo<Long> deleteById(Long id) {
        Long result = 0L;
        try {
            result = mapper.deleteById(id);
            QueueUtil.sendQueue(id, QueueWorkInfoModel.QueueWorkModifyShopInfo.OperationEnum.UPDATE);
            return new ServiceStatusInfo<>(0, "", result);
        } catch (Exception e) {
            return new ServiceStatusInfo<>(1, "删除门店代言人失败" + e.getMessage(), result);
        }
    }

    @Override
    public ServiceStatusInfo<List<OfflineStoreStaffs>> selectStaffs(Long legalSubjectId) {
        List<OfflineStoreStaffs> result = null;
        try {
            result = mapper.selectStaffs(legalSubjectId);
            for (OfflineStoreStaffs o : result) {
                Date createTime = mapper.selectSuperStarCreateTime(o.getStoreId(), o.getUserId());
                if (createTime != null) {
                    o.setCreateTime(createTime);
                    o.setSuperStar(true);
                }
            }
            return new ServiceStatusInfo<>(0, "", result);
        } catch (Exception e) {
            return new ServiceStatusInfo<>(1, "通过店铺id查询门店员工或代言人失败" + e.getMessage(), result);
        }
    }

    @Override
    public ServiceStatusInfo<List<OfflineStoreStaffs>> selectSuperStar() {
        List<OfflineStoreStaffs> list = null;
        try {
//            list = mapper.select();
            return new ServiceStatusInfo<>(0, "", list);
        } catch (Exception e) {
            return new ServiceStatusInfo<>(1, "查询门店代言人失败" + e.getMessage(), list);
        }
    }

//    public ServiceStatusInfo<List<OfflineStoreStaffs>> searchStaffs(SearchStaffInfo searchStaffInfo, long legalSubjectId) {
//
//    }


}
