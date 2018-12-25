package com.zwdbj.server.shopapi.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AppConfig {
    @Value("${app.push.pushEnvironment}")
    private String pushEnv;
    @Value("${app.swagger.enabled}")
    private boolean swaggerEnabled;
    @Value("${app.sms.send-interval}")
    private int smsSendInterval;
    @Value("${app.sms.send-max-count-day}")
    private int smsSendMaxCountDay;
    @Value("${app.sms.code-expire-time}")
    private int smsCodeExpireTime;
    @Value("${app.sms.send-open}")
    private boolean sendOpen;
    @Value("${app.aliyun.access-key}")
    private String aliyunAccessKey;
    @Value("${app.aliyun.access-secrect}")
    private String aliyunAccessSecrect;
    @Value("${app.easemob.org-name}")
    private String easemobOrgName;
    @Value("${app.easemob.app-name}")
    private String easemobAppName;
    @Value("${app.easemob.clientid}")
    private String easemobClientId;
    @Value("${app.easemob.client-secrect}")
    private String easemobClientSecrect;
    @Value("${app.easemob.httpbase}")
    private String easemobHttpBase;
    @Value("${app.xg.service-url}")
    private String xgServiceUrl;
    @Value("${app.xg.android-appid}")
    private String xgAndroidAppId;
    @Value("${app.xg.android-secrect}")
    private String xgAndroidSecrect;
    @Value("${app.xg.ios-appid}")
    private String xgIOSAppId;
    @Value("${app.xg.ios-secrect}")
    private String xgIOSSecrect;
    @Value("${app.h5.mobile-url}")
    private String h5MobileUrl;

    public String getPushEnv() {
        return pushEnv;
    }

    public void setPushEnv(String pushEnv) {
        this.pushEnv = pushEnv;
    }

    public boolean isSwaggerEnabled() {
        return swaggerEnabled;
    }

    public void setSwaggerEnabled(boolean swaggerEnabled) {
        this.swaggerEnabled = swaggerEnabled;
    }

    public int getSmsSendInterval() {
        return smsSendInterval;
    }

    public void setSmsSendInterval(int smsSendInterval) {
        this.smsSendInterval = smsSendInterval;
    }

    public int getSmsSendMaxCountDay() {
        return smsSendMaxCountDay;
    }

    public void setSmsSendMaxCountDay(int smsSendMaxCountDay) {
        this.smsSendMaxCountDay = smsSendMaxCountDay;
    }

    public int getSmsCodeExpireTime() {
        return smsCodeExpireTime;
    }

    public void setSmsCodeExpireTime(int smsCodeExpireTime) {
        this.smsCodeExpireTime = smsCodeExpireTime;
    }

    public boolean isSendOpen() {
        return sendOpen;
    }

    public void setSendOpen(boolean sendOpen) {
        this.sendOpen = sendOpen;
    }

    public String getAliyunAccessKey() {
        return aliyunAccessKey;
    }

    public void setAliyunAccessKey(String aliyunAccessKey) {
        this.aliyunAccessKey = aliyunAccessKey;
    }

    public String getAliyunAccessSecrect() {
        return aliyunAccessSecrect;
    }

    public void setAliyunAccessSecrect(String aliyunAccessSecrect) {
        this.aliyunAccessSecrect = aliyunAccessSecrect;
    }


    public String getEasemobOrgName() {
        return easemobOrgName;
    }

    public void setEasemobOrgName(String easemobOrgName) {
        this.easemobOrgName = easemobOrgName;
    }

    public String getEasemobAppName() {
        return easemobAppName;
    }

    public void setEasemobAppName(String easemobAppName) {
        this.easemobAppName = easemobAppName;
    }

    public String getEasemobClientId() {
        return easemobClientId;
    }

    public void setEasemobClientId(String easemobClientId) {
        this.easemobClientId = easemobClientId;
    }

    public String getEasemobClientSecrect() {
        return easemobClientSecrect;
    }

    public void setEasemobClientSecrect(String easemobClientSecrect) {
        this.easemobClientSecrect = easemobClientSecrect;
    }

    public String getEasemobHttpBase() {
        return easemobHttpBase;
    }

    public void setEasemobHttpBase(String easemobHttpBase) {
        this.easemobHttpBase = easemobHttpBase;
    }

    public String getXgServiceUrl() {
        return xgServiceUrl;
    }

    public void setXgServiceUrl(String xgServiceUrl) {
        this.xgServiceUrl = xgServiceUrl;
    }

    public String getXgAndroidAppId() {
        return xgAndroidAppId;
    }

    public void setXgAndroidAppId(String xgAndroidAppId) {
        this.xgAndroidAppId = xgAndroidAppId;
    }

    public String getXgAndroidSecrect() {
        return xgAndroidSecrect;
    }

    public void setXgAndroidSecrect(String xgAndroidSecrect) {
        this.xgAndroidSecrect = xgAndroidSecrect;
    }

    public String getXgIOSAppId() {
        return xgIOSAppId;
    }

    public void setXgIOSAppId(String xgIOSAppId) {
        this.xgIOSAppId = xgIOSAppId;
    }

    public String getXgIOSSecrect() {
        return xgIOSSecrect;
    }

    public void setXgIOSSecrect(String xgIOSSecrect) {
        this.xgIOSSecrect = xgIOSSecrect;
    }

    public String getH5MobileUrl() {
        return h5MobileUrl;
    }

    public void setH5MobileUrl(String h5MobileUrl) {
        this.h5MobileUrl = h5MobileUrl;
    }
}
