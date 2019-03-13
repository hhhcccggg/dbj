package com.zwdbj.server.adminserver.service.setting.service;

import com.zwdbj.server.adminserver.service.userDeviceTokens.service.UserDeviceTokensService;
import com.zwdbj.server.common.setting.model.AppPushSettingModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SettingService {
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private UserDeviceTokensService userDeviceTokensService;

    /**
     *
     * @param userId
     * @param type 0:系统消息,1:点赞类2:粉丝类3:评论4:关注人发布视频 6:视频打赏
     * @return
     */
    public boolean settingUse(long userId,int type){
        try {
            AppPushSettingModel settingModelthis =
                    (AppPushSettingModel)this.redisTemplate.opsForHash().get("setting_push_hash_cache_key",String.valueOf(userId));
            if (settingModelthis==null)return true;
            switch(type){
                case 0:return settingModelthis.isSystemIsOpen();
                case 1:return settingModelthis.isHeartIsOpen();
                case 2:return settingModelthis.isNewFollowerIsOpen();
                case 3:return settingModelthis.isCommentIsOpen();
                case 4:return settingModelthis.isMyFollowedPubNewVideoIsOpen();
                case 6:return false;
            }
        }catch (Exception e){
            this.redisTemplate.opsForHash().delete("setting_push_hash_cache_key",String.valueOf(userId));
            e.printStackTrace();
        }

        return true;
    }

    /**
     * 初始化用户的系统设置开关
     */
    public void initUserSetting(){
        if (!this.redisTemplate.hasKey("IOSSettingPush_All") || !this.redisTemplate.hasKey("ANDROIDSettingPush_All")){
        List<Long> iosUserId = this.userDeviceTokensService.getIosUserId();
        for (Long userId:iosUserId){
            this.redisTemplate.opsForZSet().add("IOSSettingPush_All", String.valueOf(userId), 0);
        }
        List<Long> androidUserId = this.userDeviceTokensService.getAndroidUserId();
        for (Long userId:androidUserId){
            this.redisTemplate.opsForZSet().add("ANDROIDSettingPush_All", String.valueOf(userId), 0);
        }
        }
}

}
