package com.zwdbj.server.mobileapi.easemob.common;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zwdbj.server.config.settings.AppSettingConfigs;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class EaseMobTokenManager {
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private AppSettingConfigs appSettingConfigs;
    private String easeMobTokenCacheKey = "easemob_token";
    protected final OkHttpClient client = new OkHttpClient();
    static Logger logger = LoggerFactory.getLogger(EaseMobTokenManager.class);

    public EaseMobToken token() {
        boolean isGetToken = false;
        if (this.redisTemplate.hasKey(easeMobTokenCacheKey)) {
            try {
                EaseMobToken easeMobToken = (EaseMobToken) this.redisTemplate.opsForValue().get(easeMobTokenCacheKey);
                if (easeMobToken != null) {
                    return easeMobToken;
                }
            } catch (Exception ex) {
                logger.error(ex.getStackTrace().toString());
                logger.error(ex.getMessage());
            }
            isGetToken = true;
        } else {
            isGetToken = true;
        }
        if (isGetToken) {
            EaseMobToken token = tokenByHttp();
            this.redisTemplate.opsForValue().set(easeMobTokenCacheKey,token,token.getExpires_in()-600,TimeUnit.SECONDS);
            return token;
        }
        return null;
    }

    public EaseMobToken tokenByHttp() {
        String url = String.format("%s/%s/%s/token",
                this.appSettingConfigs.getEasemobConfigs().getHttpbase(),
                this.appSettingConfigs.getEasemobConfigs().getOrgName(),
                this.appSettingConfigs.getEasemobConfigs().getAppName());
        Request request = new Request.Builder()
                .url(url)
                .post(RequestBody.create(MediaType.parse("application/json"),
                        "{\"grant_type\": \"client_credentials\"," +
                                "\"client_id\": \""+this.appSettingConfigs.getEasemobConfigs().getClientid()+"\"," +
                                "\"client_secret\": \""+this.appSettingConfigs.getEasemobConfigs().getClientSecrect()+"\"}"))
                .build();
        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                String bodyJSON = response.body().string();
                JSONObject jsonObject = JSON.parseObject(bodyJSON);
                EaseMobToken token = new EaseMobToken();
                token.setAccess_token(jsonObject.getString("access_token"));
                token.setExpires_in(jsonObject.getLong("expires_in"));
                token.setApplication(jsonObject.getString("application"));
                return token;
            } else {
                logger.error("获取环信TOKEN失败:>>"+response.message());
                return null;
            }
        } catch (Exception ex) {
            logger.error("获取环信TOKEN失败:>>"+ex.getMessage());
            return null;
        }

    }

}
