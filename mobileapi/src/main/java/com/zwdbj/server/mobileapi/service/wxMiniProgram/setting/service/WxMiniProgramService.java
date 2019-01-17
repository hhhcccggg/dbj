package com.zwdbj.server.mobileapi.service.wxMiniProgram.setting.service;

import com.zwdbj.server.utility.model.ServiceStatusInfo;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class WxMiniProgramService {
    private Logger logger = LoggerFactory.getLogger(WxMiniProgramService.class);
    protected final OkHttpClient client = new OkHttpClient();
    public ServiceStatusInfo<Object> getOpenIdByCode(String code){
        String url = String.format("https://api.weixin.qq.com/sns/jscode2session?appid=%s&secret=%s&js_code=%s&grant_type=authorization_code",
                "wx07f6eadb69a4b1f5","60a46fd8a1ec9e35fed165f1a6f70861",code);
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                String bodyJSON = response.body().string();
                //JSONObject jsonObject = JSON.parseObject(bodyJSON);
                /*int errcode = jsonObject.getInteger("errcode");
                String msg = jsonObject.getString("errmsg");*/
                //logger.info("errcode:"+errcode+",errmsg:"+msg);
                logger.info(bodyJSON);
                return new ServiceStatusInfo<>(0,"",bodyJSON);
            } else {
                return new ServiceStatusInfo<>(1,response.message(),"");
            }
        }
        catch (Exception ex) {
            return new ServiceStatusInfo<>(1,ex.getMessage(),"");
        }
    }
}
