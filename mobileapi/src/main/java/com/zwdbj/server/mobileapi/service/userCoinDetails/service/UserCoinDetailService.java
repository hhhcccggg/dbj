package com.zwdbj.server.mobileapi.service.userCoinDetails.service;

import com.zwdbj.server.mobileapi.service.userAssets.service.UserAssetService;
import com.zwdbj.server.mobileapi.service.userCoinDetails.mapper.IUserCoinDetailsMapper;
import com.zwdbj.server.mobileapi.service.userCoinDetails.model.UserCoinDetailAddInput;
import com.zwdbj.server.mobileapi.service.userCoinDetails.model.UserCoinDetailsModel;
import com.zwdbj.server.utility.common.UniqueIDCreater;
import com.zwdbj.server.utility.common.shiro.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserCoinDetailService {
    @Autowired
    IUserCoinDetailsMapper userCoinDetailsMapper;
    @Autowired
    UserAssetService userAssetService;

    @Transactional(readOnly = true)
    public List<UserCoinDetailsModel> getUserCoinDetails(){
        long userId = JWTUtil.getCurrentId();
        List<UserCoinDetailsModel> userCoinDetailsModels = this.userCoinDetailsMapper.getUserCoinDetails(userId);
        return userCoinDetailsModels;
    }

    @Transactional
    public int addUserCoinDetail(UserCoinDetailAddInput input){
        long userId = JWTUtil.getCurrentId();
        long id = UniqueIDCreater.generateID();
        int result = this.userCoinDetailsMapper.addUserCoinDetail(id,userId,input);
        if (result==1){
            result = this.userAssetService.updateUserAsset(input.getNum(),0L);
        }
        return result;
    }


}
