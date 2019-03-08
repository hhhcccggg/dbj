package com.zwdbj.server.common.easemob.api;

import com.zwdbj.server.common.easemob.common.EaseMobToken;
import com.zwdbj.server.common.easemob.common.EaseMobTokenManager;
import com.zwdbj.server.config.settings.AppSettingConfigs;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EaseMobUser {
    @Autowired
    private EaseMobTokenManager easeMobTokenManager;
    private Logger logger = LoggerFactory.getLogger(EaseMobUser.class);
    @Autowired
    private AppSettingConfigs appSettingConfigs;
    protected final OkHttpClient client = new OkHttpClient();

    public boolean register(String username,String password) {
        EaseMobToken token = this.easeMobTokenManager.token();
        if (token ==null) {
            logger.error("注册用户失败:>>获取环信token失败");
            return false;
        }
        String url = String.format("%s/%s/%s/users",
                this.appSettingConfigs.getEasemobConfigs().getHttpbase(),
                this.appSettingConfigs.getEasemobConfigs().getOrgName(),
                this.appSettingConfigs.getEasemobConfigs().getAppName());
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Authorization","Bearer "+token.getAccess_token())
                .post(RequestBody.create(MediaType.parse("application/json"),
                        "[{ \"username\": \""+username+"\", \"password\": \""+password+"\"}]"))
                .build();
        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                return true;
            } else {
                logger.error("创建环信用户失败>>"+response.message());
                return false;
            }
        } catch (Exception ex) {
            logger.error("创建环信用户失败>>:"+ex.getMessage());
            return false;
        }
    }
}