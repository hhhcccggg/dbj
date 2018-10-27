package com.zwdbj.server.mobileapi.service.setting.service;

import com.zwdbj.server.mobileapi.service.setting.model.AppPushSettingModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class SettingService {
    @Autowired
    private RedisTemplate redisTemplate;
    private static String appPushSettingCacheHashKey = "setting_push_hash_cache_key";
    public AppPushSettingModel get(long userId) {
        boolean isExist = this.redisTemplate.opsForHash().hasKey(appPushSettingCacheHashKey,String.valueOf(userId));
        if (isExist) {
            AppPushSettingModel settingModel = (AppPushSettingModel)this.redisTemplate.opsForHash().get(appPushSettingCacheHashKey,String.valueOf(userId));
            return settingModel;
        }
        AppPushSettingModel defaultPushSetting = defaultPushSetting();
        this.redisTemplate.opsForHash().put(appPushSettingCacheHashKey,String.valueOf(userId),defaultPushSetting);
        return defaultPushSetting;
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
        return innerModel;
    }
}
