package com.zwdbj.server.adminserver.easemob.api;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zwdbj.server.adminserver.easemob.common.EaseMobToken;
import com.zwdbj.server.adminserver.easemob.common.EaseMobTokenManager;
import com.zwdbj.server.config.settings.AppSettingConfigs;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EaseMobChatRoom {
    protected final OkHttpClient client = new OkHttpClient();
    protected Logger logger = LoggerFactory.getLogger(EaseMobChatRoom.class);
    @Autowired
    protected EaseMobTokenManager easeMobTokenManager;
    @Autowired
    protected AppSettingConfigs appSettingConfigs;

    public String registerChatRoom(String title,String owner) {
        EaseMobToken token = this.easeMobTokenManager.token();
        if (token ==null) {
            logger.error("创建聊天室失败:>>获取环信token失败");
            return null;
        }
        String url = String.format("%s/%s/%s/chatrooms",
                this.appSettingConfigs.getEasemobConfigs().getHttpbase(),
                this.appSettingConfigs.getEasemobConfigs().getOrgName(),
                this.appSettingConfigs.getEasemobConfigs().getAppName());
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Authorization","Bearer "+token.getAccess_token())
                .post(RequestBody.create(MediaType.parse("application/json"),
                        "{ \"name\": \""+title+"\", \"description\": \"欢迎进入直播间，提倡绿色健康直播\",\"maxusers\": 5000, \"owner\": \""+owner+"\"}"))
                .build();
        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                String bodyJSON = response.body().string();
                JSONObject jsonObject = JSON.parseObject(bodyJSON);
                return jsonObject.getJSONObject("data").getString("id");
            } else {
                logger.error("创建聊天室失败>>"+response.message());
                return null;
            }
        } catch (Exception ex) {
            logger.error("创建聊天室失败>>:"+ex.getMessage());
            return null;
        }
    }
    public String  deleteChatRoom(String chatRoomId){
        EaseMobToken token = this.easeMobTokenManager.token();
        if (token ==null) {
            logger.error("删除聊天室失败:>>获取环信token失败");
            return null;
        }
        String url = String.format("%s/%s/%s/chatrooms/%s",
                this.appSettingConfigs.getEasemobConfigs().getHttpbase(),
                this.appSettingConfigs.getEasemobConfigs().getOrgName(),
                this.appSettingConfigs.getEasemobConfigs().getAppName(),
                chatRoomId);
        Request request = new Request.Builder()
                .url(url)
                .delete()
                .addHeader("Authorization","Bearer "+token.getAccess_token())
                .build();
        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                String bodyJSON = response.body().string();
                JSONObject jsonObject = JSON.parseObject(bodyJSON);
                return jsonObject.getJSONObject("data").getString("id");
            } else {
                logger.error("删除聊天室失败>>"+response.message());
                return null;
            }
        }catch (Exception e){
            logger.error("删除聊天室失败:"+e.getMessage());
            return  null;
        }
    }


    public String  getAffiliationsCount(String chatRoomId){
        EaseMobToken token = this.easeMobTokenManager.token();
        if (token ==null) {
            logger.error("删除聊天室失败:>>获取环信token失败");
            return null;
        }
        String url = String.format("%s/%s/%s/chatrooms/%s",
                this.appSettingConfigs.getEasemobConfigs().getHttpbase(),
                this.appSettingConfigs.getEasemobConfigs().getOrgName(),
                this.appSettingConfigs.getEasemobConfigs().getAppName(),
                chatRoomId);
        Request request = new Request.Builder()
                .url(url)
                .get()
                .addHeader("Authorization","Bearer "+token.getAccess_token())
                .build();
        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                String bodyJSON = response.body().string();
                JSONObject jsonObject = JSON.parseObject(bodyJSON);
                String count= jsonObject.getJSONArray("data").getJSONObject(0).getString("affiliations_count");
                return count;
            } else {
                logger.error("获取聊天室人数失败>>"+response.message());
                return "-1";
            }
        }catch (Exception e){
            logger.error("获取聊天室人数失败:"+e.getMessage());
            return "-1";
        }
    }

    public String setMessages(String chatRoomId,int num){
        EaseMobToken token = this.easeMobTokenManager.token();
        String linkedNum = String.valueOf(num);
        if (token ==null) {
            logger.error("聊天室推送消息失败:>>获取环信token失败");
            return null;
        }
        String url = String.format("%s/%s/%s/messages",
                this.appSettingConfigs.getEasemobConfigs().getHttpbase(),
                this.appSettingConfigs.getEasemobConfigs().getOrgName(),
                this.appSettingConfigs.getEasemobConfigs().getAppName());
        logger.info(url);
        logger.info(chatRoomId+">>>>"+linkedNum);
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Authorization","Bearer "+token.getAccess_token())
                .post(RequestBody.create(MediaType.parse("application/json"),
                        "{ \"target_type\": \"chatrooms\", \"target\": [\""+chatRoomId+"\"], \"msg\": { \"type\": \"cmd\", \"action\": \"linkedGoodNum\"}, \"ext\": { \"linkedGoodsNum\": \""+linkedNum+"\"}}"))
                .build();
        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                String bodyJSON = response.body().string();
                JSONObject jsonObject = JSON.parseObject(bodyJSON);
                String result = jsonObject.getJSONObject("data").getString(chatRoomId);
                logger.info(result);
                return result;
            } else {
                logger.error("聊天室推送消息失败>>"+response.code()+response.message());
                return null;
            }
        } catch (Exception ex) {
            logger.error("聊天室推送消息失败>>:"+ex.getMessage());
            return null;
        }

    }
}
