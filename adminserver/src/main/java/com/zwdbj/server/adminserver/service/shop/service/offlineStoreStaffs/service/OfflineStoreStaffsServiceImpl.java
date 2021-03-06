package com.zwdbj.server.adminserver.service.shop.service.offlineStoreStaffs.service;

import com.zwdbj.server.adminserver.service.shop.service.offlineStoreStaffs.mapper.OfflineStoreStaffsMapper;
import com.zwdbj.server.adminserver.service.shop.service.offlineStoreStaffs.model.*;
import com.zwdbj.server.adminserver.service.shop.service.store.service.StoreService;
import com.zwdbj.server.adminserver.service.user.mapper.IUserMapper;
import com.zwdbj.server.adminserver.service.user.service.UserService;
import com.zwdbj.server.basemodel.model.ServiceStatusInfo;
import com.zwdbj.server.tokencenter.model.AuthUser;
import com.zwdbj.server.usercommonservice.authuser.service.AuthUserManagerImpl;
import com.zwdbj.server.utility.common.UniqueIDCreater;
import com.zwdbj.server.utility.common.shiro.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
@Transactional
public class OfflineStoreStaffsServiceImpl implements OfflineStoreStaffsService {

    @Resource
    private OfflineStoreStaffsMapper mapper;
    @Resource
    UserService userService;
    @Resource
    private IUserMapper iUserMapper;
    @Resource
    private StoreService storeServiceImpl;
    @Autowired
    private AuthUserManagerImpl authUserManager;

    @Override
    public ServiceStatusInfo<Long> create(StaffInput staffInput, long legalSubjectId) {

        try {

            long tenantId = storeServiceImpl.selectTenantId(legalSubjectId);
            userService.greateUserByTenant(staffInput.getFullName(), staffInput.getPhone(), tenantId, 2, "店员");
            //创建员工成功后发送短信到员工手机号
            return new ServiceStatusInfo<>(0, "", 1L);
        } catch (Exception e) {
            return new ServiceStatusInfo<>(1, "创建门店员工/代言人失败" + e.getMessage(), 0L);
        }
    }

    @Override
    public ServiceStatusInfo<Long> update(ModifyStaff modifyStaff) {
        Long result = 0L;
        try {

            result = iUserMapper.updateStaffInfo(modifyStaff);
            return new ServiceStatusInfo<>(0, "修改员工信息成功", result);


        } catch (Exception e) {
            return new ServiceStatusInfo<>(1, "修改门店代言人失败" + e.getMessage(), result);
        }
    }

    /**
     * 删除员工或代言人
     *
     * @param userId
     * @param legalSubjectId
     * @param isSuperStar
     * @return
     */
    @Override
    public ServiceStatusInfo<Long> deleteById(long userId, long legalSubjectId, boolean isSuperStar) {
        Long result = 0L;


        result = mapper.cancelStaff(userId);
        if (isSuperStar) {
            long storeId = storeServiceImpl.selectByLegalSubjectId(legalSubjectId).getData().getId();
            result += mapper.cancelSuperStar(userId, storeId);
        }
        return new ServiceStatusInfo<>(0, "", result);

    }

    @Override
    public ServiceStatusInfo<Long> bulkDeleteStaffs(IsSuperStar[] isSuperStar, long legalSubjectId) {
        Long result = 0L;
        try {
            for (IsSuperStar is : isSuperStar) {
                result += deleteById(is.getUserId(), legalSubjectId, is.isSuperStar()).getData();
            }
            return new ServiceStatusInfo<>(0, "", result);
        } catch (Exception e) {
            return new ServiceStatusInfo<>(1, "批量删除门店员工/代言人失败" + e.getMessage(), result);
        }
    }


    @Override
    public ServiceStatusInfo<List<OfflineStoreStaffs>> searchStaffs(SearchStaffInfo searchStaffInfo, long legalSubjectId, long storeId) {
        List<OfflineStoreStaffs> result = new ArrayList<>();
        try {

            if (searchStaffInfo.getRange() == 0) {
                result = mapper.searchStaffs(legalSubjectId, searchStaffInfo, storeId);
                for (OfflineStoreStaffs offlineStoreStaffs : result) {
                    if (mapper.isSuperStar(offlineStoreStaffs.getId(), storeId) == 1) {
                        offlineStoreStaffs.setSuperStar(true);
                    }
                }
                return new ServiceStatusInfo<>(0, "", result);
            } else if (searchStaffInfo.getRange() == 1) {
                result = mapper.searchStaffs(legalSubjectId, searchStaffInfo, storeId);
                return new ServiceStatusInfo<>(0, "", result);
            } else if (searchStaffInfo.getRange() == 2) {
                result = mapper.searchSuperStar(storeId, searchStaffInfo.getSearch());
                for (OfflineStoreStaffs offlineStoreStaffs : result) {
                    offlineStoreStaffs.setSuperStar(true);
                }
                return new ServiceStatusInfo<>(0, "", result);
            } else {
                return new ServiceStatusInfo<>(1, "参数错误", null);
            }

        } catch (
                Exception e) {
            return new ServiceStatusInfo<>(1, "搜索失败" + e.getMessage(), null);
        }


    }

    @Override
    public ServiceStatusInfo<Long> setSuperStar(long userId, long legalSubjectId, boolean isSuperStar) {
        Long result = 0L;
        try {
            long storeId = storeServiceImpl.selectByLegalSubjectId(legalSubjectId).getData().getId();
            if (isSuperStar) {
                long id = UniqueIDCreater.generateID();

                result = mapper.setSuperStar(id, storeId, userId);
                return new ServiceStatusInfo<>(0, "", result);
            }
            result = mapper.cancelSuperStar(userId, storeId);
            return new ServiceStatusInfo<>(0, "", result);
        } catch (Exception e) {
            return new ServiceStatusInfo<>(1, "设置/取消代言人失败" + e.getMessage(), result);
        }

    }

    @Override
    public ServiceStatusInfo<Long> bulkSetSuperStar(long[] userIds, long legalSubjectId, boolean isSuperStar) {
        Long result = 0L;
        try {
            for (long userId : userIds) {
                result += setSuperStar(userId, legalSubjectId, isSuperStar).getData();
            }
            return new ServiceStatusInfo<>(0, "", result);
        } catch (Exception e) {
            return new ServiceStatusInfo<>(1, "批量设置/取消代言人失败" + e.getMessage(), result);
        }
    }

    @Override
    public ServiceStatusInfo<List<SuperStarInfo>> getSuperStarDetail(String search, String rank, String sort, long legalSubjectId) {
        List<SuperStarInfo> result = null;
        try {
            result = mapper.getSuperStarDetail(search, rank, sort, legalSubjectId);
            return new ServiceStatusInfo<>(0, "", result);
        } catch (Exception e) {
            return new ServiceStatusInfo<>(1, "获取代言人详情失败" + e.getMessage(), null);
        }
    }

    @Override
    public ServiceStatusInfo<SuperStarDto> videoListStaff(long userId) {
        SuperStarDto superStarDto = null;
        try {
            superStarDto = mapper.videoListStaff(userId);
            //查询员工 评论数

            return new ServiceStatusInfo<>(0, "", superStarDto);
        } catch (Exception e) {
            return new ServiceStatusInfo<>(1, "查询代言人作品列表个人信息失败" + e.getMessage(), null);
        }
    }

    @Override
    public ServiceStatusInfo<OfflineStoreStaffs> getOfflineStoreStaffsById(long id) {
        try{
            long userId = JWTUtil.getCurrentId();
            AuthUser authUser = authUserManager.get(String.valueOf(userId));
            long storeId = storeServiceImpl.selectByLegalSubjectId(authUser.getLegalSubjectId()).getData().getId();
            return new ServiceStatusInfo<>(0, "", mapper.selectStaffById(id, authUser.getTenantId(), storeId));
        }catch (Exception e){
            return new ServiceStatusInfo<>(1, "查询员工信息失败" + e.getMessage(), null);
        }

    }
}
