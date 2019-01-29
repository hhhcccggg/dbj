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
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.*;
import java.util.concurrent.TimeUnit;

@Service
public class HeartService {
    @Autowired
    IHeartMapper heartMapper;
    @Autowired
    UserAssetServiceImpl userAssetServiceImpl;
    @Autowired
    RedisTemplate redisTemplate;

    public ServiceStatusInfo<Long> heart(long id, long userId, long resourceOwnerId, int type) {
        long result = this.heartMapper.heart(id, userId, resourceOwnerId,type);
        boolean keyExist = this.redisTemplate.hasKey("user_everyDayTask_isFirstHeart:"+userId);
        ResponseCoin coins = null;
        if (!keyExist) {
            LocalTime midnight = LocalTime.MIDNIGHT;
            LocalDate today = LocalDate.now(ZoneId.of("Asia/Shanghai"));
            LocalDateTime todayMidnight = LocalDateTime.of(today, midnight);
            LocalDateTime tomorrowMidnight = todayMidnight.plusDays(1);
            long s = TimeUnit.NANOSECONDS.toSeconds(Duration.between(LocalDateTime.now(ZoneId.of("Asia/Shanghai")), tomorrowMidnight).toNanos());
            this.redisTemplate.opsForValue().set("user_everyDayTask_isFirstHeart:" + userId, userId+":hasFirstHeart", s, TimeUnit.SECONDS);
            this.userAssetServiceImpl.userIsExist(userId);
            UserCoinDetailAddInput input = new UserCoinDetailAddInput();
            input.setStatus("SUCCESS");
            input.setNum(2);
            input.setTitle("每日首次点赞获得小饼干" + 2 + "个");
            input.setType("TASK");
            this.userAssetServiceImpl.userPlayCoinTask(input, userId, "TASK", 2,"EVERYDAYFIRSTHEART","DONE");
            coins = new ResponseCoin();
            coins.setCoins(2);
            coins.setMessage("每天首次点赞获得小饼干2个");
        }
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

    /**
     * 查询的获赞量
     * type 0:默认，短视频点赞1:评论2:宠物
     */

    public long getHeartCountByResourceOwnerId(long petId,int type){
        return this.heartMapper.getHeartCountByResourceOwnerId(petId,type);
    }
}
