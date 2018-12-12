package com.zwdbj.server.adminserver.service.userAssets.service;


import com.zwdbj.server.adminserver.service.userAssets.mapper.UserAssetsMapper;
import com.zwdbj.server.adminserver.service.userAssets.model.UserAssets;
import com.zwdbj.server.adminserver.service.userAssets.model.UserCoinDetail;
import com.zwdbj.server.adminserver.service.userAssets.model.UserCoinType;
import com.zwdbj.server.utility.model.ServiceStatusInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service

public class UserAssetsServiceImpl implements UserAssetsService {

    @Resource
    private UserAssetsMapper userAssetsMapper;



    @Override
    public ServiceStatusInfo<UserAssets> searchUserAssetsByUserId(Long userId) {
        UserAssets result = null;
        try {
            result = this.userAssetsMapper.searchUserAssetsByUserId(userId);
            return new ServiceStatusInfo<>(0, "", result);

        } catch (Exception e) {
            return new ServiceStatusInfo<>(1, "查询用户资产失败" + e.getMessage(), result);
        }
    }

    @Override
    public ServiceStatusInfo<List<UserAssets>> searchAllUserAssets() {
        List<UserAssets> result = null;
        try {
            result = this.userAssetsMapper.searchAllUserAssets();
            return new ServiceStatusInfo<>(0, "", result);
        } catch (Exception e) {
            return new ServiceStatusInfo<>(1, "查询所有用户资产失败" + e.getMessage(), result);
        }
    }

    @Override
    public ServiceStatusInfo<List<UserCoinDetail>> searchAllUserCoinDetail() {
        List<UserCoinDetail> result = null;
        try {
            result = this.userAssetsMapper.searchAllUserCoinDetail();
            return new ServiceStatusInfo<>(0, "", result);
        } catch (Exception e) {
            return new ServiceStatusInfo<>(1, "查询用户金币明细失败" + e.getMessage(), result);
        }
    }

    @Override
    public ServiceStatusInfo<List<UserCoinDetail>> searchUserCoinDetailByUserId(Long userId) {
        List<UserCoinDetail> result = null;
        try {
            result = this.userAssetsMapper.searchUserCoinDetailByUserId(userId);
            return new ServiceStatusInfo<>(0, "", result);
        } catch (Exception e) {
            return new ServiceStatusInfo<>(1, "通过用户id查询用户金币明细失败" + e.getMessage(), result);
        }
    }

    @Override
    public ServiceStatusInfo<List<UserCoinType>> searchAllUserCoinTyoe() {
        List<UserCoinType> result = null;
        try {
            result = this.userAssetsMapper.searchAllUserCoinTypes();
            System.out.println(result);
            return new ServiceStatusInfo<>(0, "", result);
        } catch (Exception e) {
            return new ServiceStatusInfo<>(1, "分类型查询用户金币总额失败" + e.getMessage(), result);
        }
    }

    @Override
    public ServiceStatusInfo<UserCoinType> searchUserCoinTypeByUserId(Long userId) {
        UserCoinType result = null;
        try {
            result = this.userAssetsMapper.searchUserCoinTpyesByUserId(userId);
            return new ServiceStatusInfo<>(0, "", result);
        } catch (Exception e) {
            return new ServiceStatusInfo<>(1, "通过用户id分类型查询用户金币总额失败" + e.getMessage(), result);
        }
    }
}
