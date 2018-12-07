package com.zwdbj.server.adminserver.service.userAssets.service;


import com.zwdbj.server.adminserver.service.userAssets.mapper.UserAssetsMapper;
import com.zwdbj.server.adminserver.service.userAssets.model.UserAssets;
import com.zwdbj.server.utility.model.ServiceStatusInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service

public class UserAssetsServiceImpl implements UserAssetsService {

    @Resource
    private UserAssetsMapper userAssetsMapper;

    @Override
    public ServiceStatusInfo<UserAssets> searchByUserId(Long userId) {
        UserAssets result = null;
        try {
            result = this.userAssetsMapper.searchByUserId(userId);
            return new ServiceStatusInfo<>(0, "", result);

        } catch (Exception e) {
            return new ServiceStatusInfo<>(1, "查询用户资产失败"+e.getMessage(), result);
        }
    }

    @Override
    public ServiceStatusInfo<List<UserAssets>> searchAll() {
        List<UserAssets> result = null;
        try {
            result = this.userAssetsMapper.searchAll();
            return new ServiceStatusInfo<>(0, "", result);
        } catch (Exception e) {
            return new ServiceStatusInfo<>(1, "查询所有用户资产失败"+e.getMessage(), result);
        }
    }
}
