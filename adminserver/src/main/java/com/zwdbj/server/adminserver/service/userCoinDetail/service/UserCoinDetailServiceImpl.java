package com.zwdbj.server.adminserver.service.userCoinDetail.service;

import com.zwdbj.server.adminserver.service.userCoinDetail.mapper.UserCoinDetailMapper;
import com.zwdbj.server.adminserver.service.userCoinDetail.model.UserCoinDetail;
import com.zwdbj.server.utility.model.ServiceStatusInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserCoinDetailServiceImpl implements UserCoinDetailService {


    @Resource
    private UserCoinDetailMapper userCoinDetailMapper;

    @Override
    public ServiceStatusInfo<List<UserCoinDetail>> searchAll() {
        List<UserCoinDetail> result = null;
        try {
            result = this.userCoinDetailMapper.searchAll();
            return new ServiceStatusInfo<>(0, "", result);
        } catch (Exception e) {
            return new ServiceStatusInfo<>(1, "查询用户金币明细失败", result);
        }
    }

    @Override
    public ServiceStatusInfo<UserCoinDetail> searchByUserId(Long userId) {
        UserCoinDetail result = null;
        try {
            result = this.userCoinDetailMapper.searchByUserId(userId);
            return new ServiceStatusInfo<>(0, "", result);
        } catch (Exception e) {
            return new ServiceStatusInfo<>(1, "通过用户id查询用户金币明细失败", result);
        }
    }
}
