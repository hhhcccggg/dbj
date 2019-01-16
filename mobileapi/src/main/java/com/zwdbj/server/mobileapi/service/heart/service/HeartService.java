package com.zwdbj.server.mobileapi.service.heart.service;

import com.ecwid.consul.v1.ConsulClient;
import com.zwdbj.server.mobileapi.service.heart.mapper.IHeartMapper;
import com.zwdbj.server.mobileapi.service.heart.model.HeartModel;
import com.zwdbj.server.mobileapi.service.userAssets.model.UserCoinDetailAddInput;
import com.zwdbj.server.mobileapi.service.userAssets.service.UserAssetServiceImpl;
import com.zwdbj.server.utility.consulLock.unit.Lock;
import com.zwdbj.server.utility.model.ResponseCoin;
import com.zwdbj.server.utility.model.ServiceStatusInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HeartService {
    @Autowired
    IHeartMapper heartMapper;
    @Autowired
    UserAssetServiceImpl userAssetServiceImpl;

    public ServiceStatusInfo<Long> heart(long id, long userId, long resourceOwnerId, int type) {
        boolean isFirstHeart = this.isFirstHeart(userId);
        ResponseCoin coins = new ResponseCoin();
        if (isFirstHeart){
            this.userAssetServiceImpl.userIsExist(userId);
            UserCoinDetailAddInput userCoinDetailAddInput = new UserCoinDetailAddInput();
            userCoinDetailAddInput.setStatus("SUCCESS");
            userCoinDetailAddInput.setNum(2);
            userCoinDetailAddInput.setTitle("每天首次点赞获得小饼干"+2+"个");
            userCoinDetailAddInput.setType("TASK");
            this.userAssetServiceImpl.userPlayCoinTask(userCoinDetailAddInput,userId,"TASK",2);

            coins.setCoins(2);
            coins.setMessage("每天首次点赞获得小饼干2个");
        }
        long result = this.heartMapper.heart(id, userId, resourceOwnerId,type);
        return new ServiceStatusInfo<>(0, "点赞成功", result,coins);
    }

    public long unHeart(long userId,long resourceOwnerId) {
        return this.heartMapper.unHeart(userId, resourceOwnerId);
    }

    public HeartModel findHeart(long userId,long resourceOwnerId) {
        return this.heartMapper.findHeart(userId, resourceOwnerId);
    }
    public Long deleteVideoHeart(Long id){
        return this.heartMapper.deleteVideoHeart(id);
    }

    /**
     *当天是否用户首次点赞
     */
    public boolean isFirstHeart(long userId) {
        String key = "user_everydayTask_isFirstHeart:"+userId;
        ConsulClient consulClient = new ConsulClient("localhost", 8500);    // 创建与Consul的连接
        Lock lock = new Lock(consulClient, "mobileapi",  key);
        try {
            if (lock.lock(true, 500L, 1)){
                int result = this.heartMapper.isFirstHeart(userId);
                return result==0;
            }
        }catch (InterruptedException e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
        return false;
    }
}
