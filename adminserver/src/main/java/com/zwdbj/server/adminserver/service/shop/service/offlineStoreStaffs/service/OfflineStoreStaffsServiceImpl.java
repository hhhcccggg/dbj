package com.zwdbj.server.adminserver.service.shop.service.offlineStoreStaffs.service;

import com.zwdbj.server.adminserver.service.shop.service.offlineStoreStaffs.mapper.OfflineStoreStaffsMapper;
import com.zwdbj.server.adminserver.service.shop.service.offlineStoreStaffs.model.IsSuperStar;
import com.zwdbj.server.adminserver.service.shop.service.offlineStoreStaffs.model.OfflineStoreStaffs;
import com.zwdbj.server.adminserver.service.shop.service.offlineStoreStaffs.model.SearchStaffInfo;
import com.zwdbj.server.utility.common.UniqueIDCreater;
import com.zwdbj.server.utility.model.ServiceStatusInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
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

    public ServiceStatusInfo<List<OfflineStoreStaffs>> searchStaffs(SearchStaffInfo searchStaffInfo, long legalSubjectId) {
        List<OfflineStoreStaffs> result = new ArrayList<>();
        try {
            if (searchStaffInfo.isSuperStar()) {
                result = mapper.searchSuperStar(legalSubjectId, searchStaffInfo.getSearch());

            }
            result = mapper.searchStaffs(legalSubjectId, searchStaffInfo.getSearch());
            return new ServiceStatusInfo<>(0, "", result);
        } catch (Exception e) {
            return new ServiceStatusInfo<>(1, "搜索失败" + e.getMessage(), null);
        }


    }

    public ServiceStatusInfo<Long> setSuperStar(IsSuperStar isSuperStar, long legalSubjectId) {
        Long result = 0L;
        try {
            if (isSuperStar.isSuperStar()) {
                long id = UniqueIDCreater.generateID();
                result = mapper.setSuperStar(id, legalSubjectId, isSuperStar.getStaffId());
            }
            result = mapper.cancelSuperStar(isSuperStar.getStaffId(), legalSubjectId);
            return new ServiceStatusInfo<>(0, "", result);
        } catch (Exception e) {
            return new ServiceStatusInfo<>(1, "设置/取消代言人失败" + e.getMessage(), result);
        }

    }


}
