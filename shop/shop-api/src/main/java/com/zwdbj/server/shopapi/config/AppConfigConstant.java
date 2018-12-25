package com.zwdbj.server.shopapi.config;
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
        AppConfigConstant.EASEMOB_APP_NAME=config.getEasemobAppName();
        AppConfigConstant.EASEMOB_ORG_NAME=config.getEasemobOrgName();
        AppConfigConstant.EASEMOB_CLIENTID=config.getEasemobClientId();
        AppConfigConstant.EASEMOB_CLIENTSECRECT=config.getEasemobClientSecrect();
        AppConfigConstant.EASEMOB_HTTP_BASE=config.getEasemobHttpBase();
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


    // 环信
    public static String EASEMOB_ORG_NAME;
    public static String EASEMOB_APP_NAME;
    public static String EASEMOB_CLIENTID;
    public static String EASEMOB_CLIENTSECRECT;
    public static String EASEMOB_HTTP_BASE;



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
