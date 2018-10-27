package com.zwdbj.server.mobileapi.config;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

@Configuration
@Order(Integer.MIN_VALUE)
public class AppConfigConstant {
    public AppConfigConstant(AppConfig config) {
        AppConfigConstant.PUSH_ENV = config.getPushEnv();
        AppConfigConstant.SWAGGER_ENABLED=config.isSwaggerEnabled();
        AppConfigConstant.APP_SMS_SEND_OPEN=config.isSendOpen();
        AppConfigConstant.APP_SMS_CODE_EXPIRE_TIME=config.getSmsCodeExpireTime();
        AppConfigConstant.APP_SMS_SEND_INTERVAL=config.getSmsSendInterval();
        AppConfigConstant.APP_SMS_SEND_MAX_COUNT_DAY=config.getSmsSendMaxCountDay();
        AppConfigConstant.ALIYUN_ACCESS_KEY=config.getAliyunAccessKey();
        AppConfigConstant.ALIYUN_ACCESS_SECRECT=config.getAliyunAccessSecrect();
        AppConfigConstant.QINIU_LIVE_HUB_NAME=config.getQiniuLiveHubName();
        AppConfigConstant.QINIU_ACCESS_KEY=config.getQiniuAccessKey();
        AppConfigConstant.QINIU_ACCESS_SECRECT=config.getQiniuAccessSecrect();
        AppConfigConstant.QINIU_BUCKET_NAME=config.getQiniuBucketName();
        AppConfigConstant.QINIU_UPTOKEN_EXPIRETIME=config.getQiniuUptokenExpireTime();
        AppConfigConstant.QINIU_LIVE_PUBLISH_EXPIRETIME=config.getQiniuLivePublishExpireTime();
        AppConfigConstant.QINIU_LIVE_HLS_PLAY_DOMAIN=config.getQiniuLiveHLSPlayDomain();
        AppConfigConstant.QINIU_LIVE_RTMP_PLAY_DOMAIN=config.getQiniuLiveRTMPPlayDoamin();
        AppConfigConstant.QINIU_LIVE_RTMP_PUBLISH_DOMAIN=config.getQiniuLiveRTMPPublishDomain();
        AppConfigConstant.QINIU_BUCKET_DOMAIN=config.getQiniuBucketDomain();
        AppConfigConstant.EASEMOB_APP_NAME=config.getEasemobAppName();
        AppConfigConstant.EASEMOB_ORG_NAME=config.getEasemobOrgName();
        AppConfigConstant.EASEMOB_CLIENTID=config.getEasemobClientId();
        AppConfigConstant.EASEMOB_CLIENTSECRECT=config.getEasemobClientSecrect();
        AppConfigConstant.EASEMOB_HTTP_BASE=config.getEasemobHttpBase();
        AppConfigConstant.YOUZAN_BIND_SHOP_ID=config.getYouzanBindShopId();
        AppConfigConstant.YOUZAN_CLIENT_ID=config.getYouzanClientId();
        AppConfigConstant.YOUZAN_SECRECT=config.getYouzanSecrect();
        AppConfigConstant.XG_ANDROID_APPID=config.getXgAndroidAppId();
        AppConfigConstant.XG_ANDROID_SECRECT=config.getXgAndroidSecrect();
        AppConfigConstant.XG_IOS_APPID=config.getXgIOSAppId();
        AppConfigConstant.XG_IOS_SECRECT=config.getXgIOSSecrect();
        AppConfigConstant.XG_SERVICE_URL=config.getXgServiceUrl();
        AppConfigConstant.H5_MOBILE_URL=config.getH5MobileUrl();
    }
    // Redis相关配置
    /**
     * 手机验证码KEY
     */
    private static String REDIS_PHONE_CODE_KEY = "phoneCode";
    public static String getRedisPhoneCodeKey(String phone) {
        return REDIS_PHONE_CODE_KEY + "_" + phone;
    }
    /**
     * 一个手机号验证码发送相关配置KEY
     */
    private static String REDIS_PHONE_CODE_CFG_KEY = "phoneCodeCfg";
    public static String getRedisPhoneCodeCfgKey(String phone) {
        return REDIS_PHONE_CODE_CFG_KEY + "_" + phone;
    }

    /**
     * 用户的粉丝量相关KEY
     */
    private static String REDIS_USER_FAN_KEY = "userFans";
    public static String getRedisUserFanKey(Long userId){
        return REDIS_USER_FAN_KEY+"_"+userId;
    }

    /**
     * 用户的关注量相关KEY
     */
    private static String REDIS_USER_FOCUSE_KEY = "userFocuses";
    public static String getRedisUserFocusesKey(Long userId){
        return REDIS_USER_FOCUSE_KEY+"_"+userId;
    }

    // 有赞
    public static String REDIS_YOUZAN_SERVER_TOKEN_KEY="youzanservertokenkey";
    public static String REDIS_YOUZAN_USER_TOKEN_KEY="youzanusertoken";
    public static String getRedisYouzanUserTokenKey(long userId) {
        return REDIS_YOUZAN_USER_TOKEN_KEY+"_"+userId;
    }

    public static String PUSH_ENV;
    public static boolean SWAGGER_ENABLED;

    // 发送短信相关配置
    /**
     * 发送短信时间间隔：秒
     */
    public static int APP_SMS_SEND_INTERVAL;
    /**
     * 每日短信最多发送次数
     */
    public static int APP_SMS_SEND_MAX_COUNT_DAY;
    public static int APP_SMS_CODE_EXPIRE_TIME;
    public static boolean APP_SMS_SEND_OPEN;

    // 阿里云相关配置
    public static String ALIYUN_ACCESS_KEY;
    public static String ALIYUN_ACCESS_SECRECT;

    // 七牛
    public static String QINIU_ACCESS_KEY;
    public static String QINIU_ACCESS_SECRECT;
    public static String QINIU_BUCKET_NAME;
    public static String QINIU_BUCKET_DOMAIN;
    public static long QINIU_UPTOKEN_EXPIRETIME;
    public static String QINIU_LIVE_HUB_NAME;
    public static String QINIU_LIVE_RTMP_PUBLISH_DOMAIN;
    public static String QINIU_LIVE_RTMP_PLAY_DOMAIN;
    public static String QINIU_LIVE_HLS_PLAY_DOMAIN;
    public static int QINIU_LIVE_PUBLISH_EXPIRETIME;

    // 环信
    public static String EASEMOB_ORG_NAME;
    public static String EASEMOB_APP_NAME;
    public static String EASEMOB_CLIENTID;
    public static String EASEMOB_CLIENTSECRECT;
    public static String EASEMOB_HTTP_BASE;

    // 有赞
    public static String YOUZAN_CLIENT_ID;
    public static String YOUZAN_SECRECT;
    public static String YOUZAN_BIND_SHOP_ID;


    //信鸽
    public static String XG_SERVICE_URL;
    public static String XG_ANDROID_APPID;
    public static String XG_ANDROID_SECRECT;
    public static String XG_IOS_APPID;
    public static String XG_IOS_SECRECT;

    public static String H5_MOBILE_URL;
    //移动端网页相关配置
    public static String getShopListUrl(long id,String type) {
        return H5_MOBILE_URL+"/shop/#/userlist?resId="+id+"&resType="+type;
    }
    public static String getLiveAddShopUrl(long id) {
        return H5_MOBILE_URL+"/shop/#/home?resId="+id;
    }
    public static String getShareUrl(long id,String type) {
        return H5_MOBILE_URL+"/share/html/share.html?resId="+id+"&resType="+type;
    }


}
