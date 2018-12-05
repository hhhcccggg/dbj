package com.zwdbj.server.shop_admin_service.offlineStoreStaffs.service;

import com.zwdbj.server.shop_admin_service.offlineStoreStaffs.mapper.OfflineStoreStaffsMapper;
import com.zwdbj.server.shop_admin_service.offlineStoreStaffs.model.OfflineStoreStaffs;
import com.zwdbj.server.utility.common.UniqueIDCreater;
import com.zwdbj.server.utility.model.ServiceStatusInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
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
            return new ServiceStatusInfo<>(0, "", result);
        } catch (Exception e) {
            return new ServiceStatusInfo<>(1, "删除门店代言人失败" + e.getMessage(), result);
        }
    }

    @Override
    public ServiceStatusInfo<OfflineStoreStaffs> selectById(Long id) {
        OfflineStoreStaffs offlineStoreStaffs = null;
        try {
            offlineStoreStaffs = mapper.selectById(id);
            return new ServiceStatusInfo<>(0, "", offlineStoreStaffs);
        } catch (Exception e) {
            return new ServiceStatusInfo<>(1, "通过id查询门店代言人失败" + e.getMessage(), offlineStoreStaffs);
        }
    }

    @Override
    public ServiceStatusInfo<List<OfflineStoreStaffs>> select() {
        List<OfflineStoreStaffs> list = null;
        try {
            list = mapper.select();
            return new ServiceStatusInfo<>(0, "", list);
        } catch (Exception e) {
            return new ServiceStatusInfo<>(1, "查询门店代言人失败" + e.getMessage(), list);
        }
    }
}
