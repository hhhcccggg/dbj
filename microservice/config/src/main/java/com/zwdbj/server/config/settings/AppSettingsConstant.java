package com.zwdbj.server.config.settings;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

public class AppSettingsConstant {
    private static String REDIS_PHONE_CODE_KEY = "phoneCode";
    public static String getRedisPhoneCodeKey(String phone) {
        return REDIS_PHONE_CODE_KEY + "_" + phone;
    }
    
    private static String REDIS_PHONE_CODE_CFG_KEY = "phoneCodeCfg";
    public static String getRedisPhoneCodeCfgKey(String phone) {
        return REDIS_PHONE_CODE_CFG_KEY + "_" + phone;
    }
    
    private static String REDIS_USER_FAN_KEY = "userFans";
    public static String getRedisUserFanKey(Long userId){
        return REDIS_USER_FAN_KEY+"_"+userId;
    }
    
    private static String REDIS_USER_FOCUSE_KEY = "userFocuses";
    public static String getRedisUserFocusesKey(Long userId){
        return REDIS_USER_FOCUSE_KEY+"_"+userId;
    }

    // 有赞
    public static final String REDIS_YOUZAN_SERVER_TOKEN_KEY="youzanservertokenkey";
    private static String REDIS_YOUZAN_USER_TOKEN_KEY="youzanusertoken";
    public static String getRedisYouzanUserTokenKey(long userId) {
        return REDIS_YOUZAN_USER_TOKEN_KEY+"_"+userId;
    }

    // 推荐
    public static final String REDIS_VIDEO_RECOMMEND_KEY = "videoRecommend";
    public static final String REDIS_VIDEO_WEIGHT_KEY = "videoWeight";

    //视频权重
    protected static String REDIS_VIDEO_WEIGHT_TASK_KEY = "video_weight_key_";
    public static String getRedisVideoWeightTaskKey(long id) {
        return REDIS_VIDEO_WEIGHT_TASK_KEY + String.valueOf(id);
    }
    // 计算权重时间间隔，单位秒
    public static final int VIDEO_WEIGHT_CALCULATE_INTERVAL = 3*60;
}
