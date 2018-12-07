package com.zwdbj.server.adminserver.service.userCoinType.service;

import com.zwdbj.server.adminserver.service.userCoinType.mapper.UserCoinTypeMapper;
import com.zwdbj.server.adminserver.service.userCoinType.model.UserCoinType;
import com.zwdbj.server.utility.model.ServiceStatusInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service

public class UserCoinTypeServiceImpl implements UserCoinTypeService {
    @Resource
    private UserCoinTypeMapper userCoinTypeMapper;


    @Override
    public ServiceStatusInfo<List<UserCoinType>> searchAll() {
        List<UserCoinType> result = null;
        try {
            result = this.userCoinTypeMapper.searchAll();
            System.out.println(result);
            return new ServiceStatusInfo<>(0, "", result);
        } catch (Exception e) {
            return new ServiceStatusInfo<>(1, "分类型查询用户金币总额失败"+e.getMessage(), result);
        }
    }

    @Override
    public ServiceStatusInfo<UserCoinType> searchByUserId(Long userId) {
        UserCoinType result = null;
        try {
            result = this.userCoinTypeMapper.searchByUserId(userId);
            return new ServiceStatusInfo<>(0, "", result);
        } catch (Exception e) {
            return new ServiceStatusInfo<>(1, "通过用户id分类型查询用户金币总额失败"+e.getMessage(),result);
        }
    }
}
