package com.zwdbj.server.adminserver.service.push.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zwdbj.server.probuf.middleware.mq.QueueWorkInfoModel;
import com.zwdbj.server.adminserver.config.AppConfigConstant;
import com.zwdbj.server.adminserver.service.push.model.*;
import com.zwdbj.server.adminserver.service.user.service.UserService;
import com.zwdbj.server.adminserver.service.userDeviceTokens.model.AdUserDeviceTokenDto;
import com.zwdbj.server.adminserver.service.userDeviceTokens.service.UserDeviceTokensService;
import com.zwdbj.server.adminserver.service.video.model.VideoDetailInfoDto;
import com.zwdbj.server.adminserver.service.video.service.VideoService;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Service
public class AppPushService {
    @Autowired
    protected UserService userService;
    @Autowired
    protected VideoService videoService;
    protected Logger logger = LoggerFactory.getLogger(AppPushService.class);
    protected final OkHttpClient client = new OkHttpClient();
    @Autowired
    private UserDeviceTokensService userDeviceTokensService;

    public boolean push(QueueWorkInfoModel.QueueWorkPush pushData) {
        //消息类型0:系统消息,1:点赞类2:粉丝类3:评论4:关注人发布视频5:关注人发布直播
        try {
            if (pushData == null) return true;
            if (pushData.getMessageType()==0) {//获取推送消息类型
                String pushTitle = "爪子提醒";
                String pushDescription = pushData.getMsgContent();//获取消息文本内容
                int type = 0;
                PushMessage pushMessage = new PushMessage();
                pushMessage.setPushId(pushData.getPushId());
                PushXGExtraMessage pushXGExtraMessage = new PushXGExtraMessage();
                pushXGExtraMessage.setMessageType(0);
                pushXGExtraMessage.setResId(0);
                pushXGExtraMessage.setResType(0);
                pushMessage.setExtraData(pushXGExtraMessage);
                pushMessage.setMsgContent(pushDescription);
                pushMessage.setRefUrl(pushData.getRefUrl());
                pushMessage.setTitle(pushTitle);
                this.pushMessage(pushMessage,0);
                logger.info("推送的类型:"+type+"推送消息title:"+pushTitle+",内容："+pushDescription);
            } else {
                return this.pushOneToOne(pushData);
            }

        }catch (Exception e){
           logger.error( "push异常:"+e.getMessage());
        }
        return true;
    }

    protected boolean pushOneToOne(QueueWorkInfoModel.QueueWorkPush pushData) {
        String pushTitle = "爪子提醒";
        String pushDescription = pushData.getMsgContent();
        int type = 0;
        if (pushData.getCreatorUserId()==0 || pushData.getToUserId()==0) {
            logger.warn("推送消息失败编号:"+pushData.getPushId()+"没有创建者或者目的用户");
            return true;
        }

        PushResDataContent pushResDataContent = null;
        if (pushData.getMessageType()==1 || pushData.getMessageType()==3) {
            pushResDataContent = parseResDataContent(pushData);
            if (pushResDataContent == null) return true;
        }
        if (pushData.getMessageType() == 1) {
            type=1;
            pushTitle = "收到新点赞";
            pushDescription = String.format("你的作品《%s》收到新点赞",pushResDataContent.getTitle());
        } else if (pushData.getMessageType() == 3) {
            type=3;
            pushTitle = "收到新评论";
            pushDescription = String.format("你的作品《%s》收到新评论",pushResDataContent.getTitle());
        } else if (pushData.getMessageType() == 2) {
            type=2;
            pushTitle = "新粉丝通知";
            pushDescription = "又有人悄悄关注了你，快去看看！";
        }

        //TODO 关注的人发布视频和直播提醒
        

        PushMessage pushMessage = new PushMessage();
        pushMessage.setType(type);
        pushMessage.setPushId(pushData.getPushId());
        PushXGExtraMessage pushXGExtraMessage = new PushXGExtraMessage();
        pushXGExtraMessage.setMessageType(pushResDataContent.getType());
        pushXGExtraMessage.setResId(pushResDataContent.getId());
        pushXGExtraMessage.setResType(pushResDataContent.getType());
        pushMessage.setExtraData(pushXGExtraMessage);
        pushMessage.setMsgContent(pushDescription);
        pushMessage.setRefUrl(pushData.getRefUrl());
        pushMessage.setTitle(pushTitle);
        this.pushMessage(pushMessage,pushData.getToUserId());

        return true;
    }

    protected PushResDataContent parseResDataContent(QueueWorkInfoModel.QueueWorkPush pushData) {
        if(pushData.getDataContent()==null
                || pushData.getDataContent().length()==0
                || pushData.getDataContent().trim().length()==0) {
            logger.warn("推送消息失败编号:"+pushData.getPushId()
                    +"dataContent数据没有值");
            return null;
        }
        JSONObject jsonObject = JSON.parseObject(pushData.getDataContent());
        if (jsonObject == null) {
            logger.warn("推送消息失败编号:"+pushData.getPushId()
                    +"dataContent数据没有值或者解析不正常");
            return null;
        }
        long resId = jsonObject.getLong("resId");
        int type = jsonObject.getInteger("type");
        if (resId <= 0) {
            logger.warn("推送消息失败编号:"+pushData.getPushId()
                    +"资源数据ID非法");
            return null;
        }
        PushResDataContent pushResDataContent = new PushResDataContent();
        if (type == 0) {
            VideoDetailInfoDto detailInfoDto = this.videoService.video(resId);
            if (detailInfoDto == null) {
                logger.warn("推送消息失败编号:"+pushData.getPushId()
                        +", 视频已经不存在");
                return null;
            }
            pushResDataContent.setTitle(detailInfoDto.getTitle());
        }
        pushResDataContent.setId(resId);
        pushResDataContent.setType(type);
        return pushResDataContent;
    }

    protected void pushMessage(PushMessage message,long toUserId) {
        if (toUserId<=0) {
            pushMessage(message,toUserId,"ios",true);
            pushMessage(message,toUserId,"android",true);
        } else  {
            AdUserDeviceTokenDto tokenDto = this.userDeviceTokensService.getDeviceTokenByUserId(toUserId);
            if (tokenDto==null) {
                logger.warn("推送消息失败编号:"+message.getPushId()
                        +", 找不到绑定的终端");
                return;
            }
            pushMessage(message, toUserId,tokenDto.getDeviceType().toLowerCase(),false);
        }
    }
    protected void pushMessage(PushMessage message,long toUserId,String type,boolean isAll) {
        Request.Builder builder = new Request.Builder();
        builder.url(AppConfigConstant.XG_SERVICE_URL);
        settingRequestHeader(builder,type);
        String jsonBody = "";
        PushXGMessage xgMessage = new PushXGMessage();
        xgMessage.setPlatform(type);
        xgMessage.setEnvironment(AppConfigConstant.PUSH_ENV);
        if (isAll) {
            xgMessage.setAudience_type("all");
        } else  {
            xgMessage.setAudience_type("account");
            List<String> accounts = new ArrayList<>();
            accounts.add(String.valueOf(toUserId));
            xgMessage.setAccount_list(accounts);
        }
        if (type.equals("ios")) {
            PushXGIOSMessage xgiosMessage = new PushXGIOSMessage();
            xgiosMessage.setContent(message.getMsgContent());
            xgiosMessage.setTitle(message.getTitle());
            xgiosMessage.setCustom(message.getExtraData());
            xgiosMessage.setType(message.getType());
            xgiosMessage.setAps("{\"alert\": \""+message.getMsgContent()+"\",\"badge\": 1}");
            xgMessage.setMessage(xgiosMessage);
        } else {
            PushXGAndroidMessage androidMessage = new PushXGAndroidMessage();
            androidMessage.setType(message.getType());
            androidMessage.setTitle(message.getTitle());
            androidMessage.setContent(message.getMsgContent());
            androidMessage.setCustom(message.getExtraData());
            xgMessage.setMessage(androidMessage);
        }
        jsonBody = JSON.toJSONString(xgMessage);
        builder.post(RequestBody.create(MediaType.parse("application/json"),jsonBody));
        logger.info("推送消息{"+type+"},{isALL="+isAll+"}请求:"+jsonBody);
        Request request = builder.build();
        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                String responseString = response.body().string();
                logger.info("推送消息{"+type+"},{isALL="+isAll+"}成功:"+responseString);
            } else {
                logger.info("推送消息{"+type+"},{isALL="+isAll+"}失败:"+response.message());
            }
        } catch (Exception ex) {
            logger.error("推送消息{"+type+"},{isALL="+isAll+"}失败:"+ex.getMessage());
        }
    }


    private void settingRequestHeader(@NotNull Request.Builder request,@NotNull String deviceType) {
        String type = deviceType.toLowerCase();
        String waitingAuthString = null;
        if (type.equals("ios")) {
            waitingAuthString = String.format("%s:%s",AppConfigConstant.XG_IOS_APPID,AppConfigConstant.XG_IOS_SECRECT);
        } else {
            waitingAuthString = String.format("%s:%s",AppConfigConstant.XG_ANDROID_APPID,AppConfigConstant.XG_ANDROID_SECRECT);
        }
        byte[] bytes = Base64.getEncoder().encode(waitingAuthString.getBytes());
        String base64EncodeString = new String(bytes);
        request.addHeader("Authorization","Basic "+base64EncodeString);
        request.addHeader("Content-Type","application/json");
    }

}
