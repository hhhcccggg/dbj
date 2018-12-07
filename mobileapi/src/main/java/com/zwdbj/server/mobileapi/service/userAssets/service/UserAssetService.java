package com.zwdbj.server.mobileapi.service.userAssets.service;

import com.zwdbj.server.mobileapi.service.userAssets.mapper.IUserAssetMapper;
import com.zwdbj.server.mobileapi.service.userAssets.model.UserAssetModel;
import com.zwdbj.server.utility.common.UniqueIDCreater;
import com.zwdbj.server.utility.common.shiro.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserAssetService {
    @Autowired
    IUserAssetMapper userAssetMapper;
    @Autowired
    private RedisTemplate redisTemplate;
    @Transactional
    public UserAssetModel getCoinsByUserId(){
        long userId = JWTUtil.getCurrentId();
        boolean isExist =  this.userAssetIsExistOrNot(userId);
        if (!isExist){
            this.greatUserAsset();
        }
        UserAssetModel userAssetModel= this.userAssetMapper.getCoinsByUserId(userId);
        //加入缓存
        String key = "USERASSET"+userAssetModel.getUserId();
        ValueOperations<String,UserAssetModel> operations = redisTemplate.opsForValue();
        operations.set(key,userAssetModel);
        return userAssetModel;
    }
    @Transactional
    public int updateUserAsset(long coins,long remainBalance){
        long userId = JWTUtil.getCurrentId();
        int result = this.userAssetMapper.updateUserAsset(userId,coins,remainBalance);
        if (result==1){
            this.getCoinsByUserId();
        }
        return result;
    }
    @Transactional
    public int greatUserAsset(){
        long id = UniqueIDCreater.generateID();
        long userId = JWTUtil.getCurrentId();
        int result = this.userAssetMapper.greatUserAsset(id,userId);
        return result;
    }
    @Transactional(readOnly = true)
    public boolean userAssetIsExistOrNot(long userId){
        int result = this.userAssetMapper.userAssetIsExistOrNot(userId);
        return result!=0;
    }


}
