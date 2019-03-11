package com.zwdbj.server.mobileapi.service.setting.service;

import com.zwdbj.server.mobileapi.service.setting.model.AppPushSettingModel;
import com.zwdbj.server.mobileapi.service.userDeviceTokens.model.UserDeviceTokenDto;
import com.zwdbj.server.mobileapi.service.userDeviceTokens.service.UserDeviceTokensService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class SettingService {
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private UserDeviceTokensService userDeviceTokensService;
    private static String appPushSettingCacheHashKey = "setting_push_hash_cache_key";
    public AppPushSettingModel get(long userId) {
        boolean isExist = this.redisTemplate.opsForHash().hasKey(appPushSettingCacheHashKey,String.valueOf(userId));
        AppPushSettingModel settingModel;
        if (isExist) {
            settingModel = (AppPushSettingModel)this.redisTemplate.opsForHash().get(appPushSettingCacheHashKey,String.valueOf(userId));
            return settingModel;
        }
        settingModel = defaultPushSetting();
        this.redisTemplate.opsForHash().put(appPushSettingCacheHashKey,String.valueOf(userId),settingModel);
        return settingModel;
    }
    protected AppPushSettingModel defaultPushSetting() {
        AppPushSettingModel appPushSettingModel = new AppPushSettingModel();
        appPushSettingModel.setCommentIsOpen(true);
        appPushSettingModel.setHeartIsOpen(true);
        appPushSettingModel.setMyFollowedLivingIsOpen(true);
        appPushSettingModel.setMyFollowedPubNewVideoIsOpen(true);
        appPushSettingModel.setSystemIsOpen(true);
        appPushSettingModel.setNewFollowerIsOpen(true);
        return appPushSettingModel;
    }
    public AppPushSettingModel set(AppPushSettingModel model,long userId) {
        AppPushSettingModel innerModel = model;
        if (innerModel == null) {
            innerModel = defaultPushSetting();
        }
        this.redisTemplate.opsForHash().put(appPushSettingCacheHashKey,String.valueOf(userId),innerModel);
        if (!model.isSystemIsOpen()){
            UserDeviceTokenDto deviceTokenDto = this.userDeviceTokensService.getDeviceTokenByUserId(userId);
            if (deviceTokenDto.getDeviceType().equals("ios"))
                this.redisTemplate.opsForZSet().remove("IOSSettingPush_All",String.valueOf(userId));
            if (deviceTokenDto.getDeviceType().equals("android"))
                this.redisTemplate.opsForZSet().remove("ANDROIDSettingPush_All",String.valueOf(userId));
        }
        return innerModel;
    }
}
