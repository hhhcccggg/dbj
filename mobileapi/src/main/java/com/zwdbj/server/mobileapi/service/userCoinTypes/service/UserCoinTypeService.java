package com.zwdbj.server.mobileapi.service.userCoinTypes.service;

import com.zwdbj.server.mobileapi.service.userCoinTypes.mapper.IUserCoinTypeMapper;
import com.zwdbj.server.mobileapi.service.userCoinTypes.model.UserCoinTypeModel;
import com.zwdbj.server.utility.common.UniqueIDCreater;
import com.zwdbj.server.utility.common.shiro.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class UserCoinTypeService {
    @Autowired
    IUserCoinTypeMapper userCoinTypeMapper;
    public UserCoinTypeModel getUserCoinType(String type){
        long userId = JWTUtil.getCurrentId();
        boolean isExist = this.userCoinTypeIsExist(userId,type);
        if (!isExist){
            this.greatUserCoinType(userId,type);
        }
        UserCoinTypeModel userCoinTypeModel = this.userCoinTypeMapper.getUserCoinType(userId,type);
        return userCoinTypeModel;
    }

    @Transactional
    public int  greatUserCoinType(long userId,String type){
        long id  = UniqueIDCreater.generateID();
        int result = this.userCoinTypeMapper.greatUserCoinType(id,userId,type);
        return result;

    }

    @Transactional(readOnly = true)
    public boolean userCoinTypeIsExist(long userId,String type){
        int result = this.userCoinTypeMapper.userCoinTypeIsExist(userId,type);
        return result!=0;
    }

}
