package com.zwdbj.server.adminserver.service.push.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zwdbj.server.adminserver.service.pet.model.PetModelDto;
import com.zwdbj.server.adminserver.service.pet.service.PetService;
import com.zwdbj.server.adminserver.service.push.model.*;
import com.zwdbj.server.adminserver.service.setting.model.AppPushSettingModel;
import com.zwdbj.server.adminserver.service.setting.service.SettingService;
import com.zwdbj.server.adminserver.service.user.service.UserService;
import com.zwdbj.server.adminserver.service.userDeviceTokens.model.AdUserDeviceTokenDto;
import com.zwdbj.server.adminserver.service.userDeviceTokens.service.UserDeviceTokensService;
import com.zwdbj.server.adminserver.service.video.model.VideoDetailInfoDto;
import com.zwdbj.server.adminserver.service.video.service.VideoService;
import com.zwdbj.server.config.settings.AppSettingConfigs;
import com.zwdbj.server.probuf.middleware.mq.QueueWorkInfoModel;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Map;

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
    @Autowired
    private AppSettingConfigs appSettingConfigs;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private SettingService settingService;
    @Autowired
    private PetService petService;

    public boolean push(QueueWorkInfoModel.QueueWorkPush pushData) {
        //消息类型0:系统消息,1:点赞类2:粉丝类3:评论4:关注人发布视频5:关注人发布直播
        try {
            if (pushData == null) return true;
            if (pushData.getMessageType() == 0) {//获取推送消息类型
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
                pushMessage.setType(type);
                this.pushMessage(pushMessage, 0);
                logger.info("推送的类型:" + type + "推送消息title:" + pushTitle + ",内容：" + pushDescription);
            } else {
                return this.pushOneToOne(pushData);
            }

        } catch (Exception e) {
            logger.error("push异常:" + e.getMessage());
        }
        return true;
    }

    protected boolean pushOneToOne(QueueWorkInfoModel.QueueWorkPush pushData) {
        String pushTitle = "爪子提醒";
        String pushDescription = pushData.getMsgContent();
        int type = 0;
        if (pushData.getCreatorUserId() == 0 || pushData.getToUserId() == 0) {
            logger.warn("推送消息失败编号:" + pushData.getPushId() + "没有创建者或者目的用户");
            return true;
        }

        PushResDataContent pushResDataContent = null;
        if (pushData.getMessageType() == 1 || pushData.getMessageType() == 3 || pushData.getMessageType()==6) {
            pushResDataContent = parseResDataContent(pushData);
            if (pushResDataContent == null) return true;
        }
        if (pushData.getMessageType() == 1) {
            type = 1;
            pushTitle = "收到新点赞";
            if (pushResDataContent.getType()==0){
                pushDescription = String.format("你的作品《%s》收到新点赞", pushResDataContent.getTitle());
            }else if (pushResDataContent.getType()==1){
                pushDescription = String.format("你的宠物:%s收到新点赞", pushResDataContent.getTitle());
            }

        } else if (pushData.getMessageType() == 3) {
            type = 3;
            pushTitle = "收到新评论";
            pushDescription = String.format("你的作品《%s》收到新评论", pushResDataContent.getTitle());
        } else if (pushData.getMessageType() == 2) {
            type = 2;
            pushTitle = "新粉丝通知";
            pushDescription = "又有人悄悄关注了你，快去看看！";
        }else if (pushData.getMessageType() == 6) {
            type = 6;
            pushTitle = "获得新打赏通知";
            pushDescription = String.format("你的作品《%s》收到新打赏", pushResDataContent.getTitle());
        }

        //TODO 关注的人发布视频


        PushMessage pushMessage = new PushMessage();
        pushMessage.setType(type);
        pushMessage.setPushId(pushData.getPushId());
        PushXGExtraMessage pushXGExtraMessage = new PushXGExtraMessage();
        pushXGExtraMessage.setMessageType(pushData.getMessageType());
        if (pushResDataContent!=null){
            pushXGExtraMessage.setResId(pushResDataContent.getId());
            pushXGExtraMessage.setResType(pushResDataContent.getType());
        }
        pushMessage.setExtraData(pushXGExtraMessage);
        pushMessage.setMsgContent(pushDescription);
        if (pushData.getRefUrl()!=null && pushData.getRefUrl().length()!=0){
            pushMessage.setRefUrl(pushData.getRefUrl());
        }
        pushMessage.setTitle(pushTitle);
        this.pushMessage(pushMessage, pushData.getToUserId());

        return true;
    }

    protected PushResDataContent parseResDataContent(QueueWorkInfoModel.QueueWorkPush pushData) {
        if (pushData.getDataContent() == null
                || pushData.getDataContent().length() == 0
                || pushData.getDataContent().trim().length() == 0) {
            logger.warn("推送消息失败编号:" + pushData.getPushId()
                    + "dataContent数据没有值");
            return null;
        }
        JSONObject jsonObject = JSON.parseObject(pushData.getDataContent());
        if (jsonObject == null) {
            logger.warn("推送消息失败编号:" + pushData.getPushId()
                    + "dataContent数据没有值或者解析不正常");
            return null;
        }
        long resId = jsonObject.getLong("resId");
        int type = jsonObject.getInteger("type");
        if (resId <= 0) {
            logger.warn("推送消息失败编号:" + pushData.getPushId()
                    + "资源数据ID非法");
            return null;
        }
        PushResDataContent pushResDataContent = new PushResDataContent();
        if (type == 0) {
            VideoDetailInfoDto detailInfoDto = this.videoService.video(resId);
            if (detailInfoDto == null) {
                logger.warn("推送消息失败编号:" + pushData.getPushId()
                        + ", 视频已经不存在");
                return null;
            }
            pushResDataContent.setTitle(detailInfoDto.getTitle());
            pushResDataContent.setId(resId);
            pushResDataContent.setType(type);
        }else if (type==1 && pushData.getMessageType()==1){
            PetModelDto petModelDto = this.petService.get(resId);
            if (petModelDto == null) {
                logger.warn("推送消息失败编号:" + pushData.getPushId()
                        + ", 视频已经不存在");
                return null;
            }
            pushResDataContent.setId(resId);
            pushResDataContent.setTitle(petModelDto.getNickName());
            pushResDataContent.setType(type);
        }
        return pushResDataContent;
    }

    protected void pushMessage(PushMessage message, long toUserId) {
        ArrayList<String> accounts = new ArrayList<>();
        ArrayList<String> accounts2 = new ArrayList<>();

        //未登录
        if (toUserId <= 0) {
            accounts.addAll(this.redisTemplate.opsForZSet().range("IOSSettingPush_All",0,-1));
            accounts2.addAll(this.redisTemplate.opsForList().range("ANDROIDSettingPush_All",0,-1));
            pushMessage(message, toUserId, "ios", false,accounts);
            pushMessage(message, toUserId, "android", false,accounts2);
        } else {//已登录
            AdUserDeviceTokenDto tokenDto = this.userDeviceTokensService.getDeviceTokenByUserId(toUserId);
            if (tokenDto == null) {
                logger.warn("推送消息失败编号:" + message.getPushId()
                        + ", 找不到绑定的终端");
                return;
            }
            boolean aa = this.settingService.settingUse(toUserId,message.getType());
            if (!aa)return;
            accounts.add(String.valueOf(toUserId));
            pushMessage(message, toUserId, tokenDto.getDeviceType().toLowerCase(), false,accounts);
        }
    }

    //推送请求参数到信鸽后台
    protected void pushMessage(PushMessage message, long toUserId, String type, boolean isAll,ArrayList<String> accounts) {
        Request.Builder builder = new Request.Builder();
        builder.url(this.appSettingConfigs.getXgConfigs().getServiceUrl());
        //设置请求头
        settingRequestHeader(builder, type);
        String jsonBody = "";
        //推送到信鸽后台的请求参数
        PushXGMessage xgMessage = new PushXGMessage();
        Message mag = new Message();
        mag.setTitle(message.getTitle());
        mag.setContent(message.getMsgContent());
        if (isAll) {
            //设置推送目标 all：全量推送
            xgMessage.setAudience_type("all");
        } else {
            if (toUserId>0){//单账号推送
                xgMessage.setAudience_type("account");
            }else {//账号列表推送
                xgMessage.setAudience_type("account_list");
                xgMessage.setPush_id("0");
            }
            xgMessage.setAccount_list(accounts);

        }
        //设置ios推送消息体
        if (type.equals("ios")) {
            PushXGIOSMessage xgiosMessage = new PushXGIOSMessage();
            Aps aps = new Aps();
            Alert alert = new Alert();
            alert.setSubtitle(message.getMsgContent());
            aps.setAlert(alert);
            aps.setBadge_type(1);
            xgiosMessage.setAps(aps);
            xgiosMessage.setCustom(JSON.toJSONString(message.getExtraData()));
            xgMessage.setPlatform("ios");
            if ("dev".equals(this.appSettingConfigs.getPushConfigs().getPushEnvironment())){
                xgMessage.setEnvironment(Environment.dev);
            }
            mag.setIos(xgiosMessage);
            xgMessage.setMessage(mag);
        } else if (type.equals("android")){//安卓推送消息体
            PushXGAndroidMessage androidMessage = new PushXGAndroidMessage();
            androidMessage.setCustom_content(JSON.toJSONString(message.getExtraData()));
            ClickAction action = new ClickAction();
            if (message.getType()==1){
                action.setActivity("com.zwdbj.aichongpai.ui.message.LikeActivity");
            }else if (message.getType()==2){
                action.setActivity("com.zwdbj.aichongpai.my.MyFans2Activity");
            }else if (message.getType()==3){
                action.setActivity("com.zwdbj.aichongpai.ui.message.CommentsActivity");
            }
            androidMessage.setAction(action);
            mag.setAndroid(androidMessage);
            xgMessage.setPlatform("android");
            xgMessage.setMessage(mag);
        }else {
            xgMessage.setPlatform("all");
            xgMessage.setMessage(mag);
        }
        //将请求参数封装为json上传给后台
        jsonBody = JSON.toJSONString(xgMessage);
        builder.post(RequestBody.create(MediaType.parse("application/json"), jsonBody));
        logger.info("推送消息{" + type + "},{isALL=" + isAll + "}请求:" + jsonBody);
        Request request = builder.build();
        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                String responseString = response.body().string();
                logger.info("推送消息{" + type + "},{isALL=" + isAll + "}成功:" + responseString);
            } else {
                logger.info("推送消息{" + type + "},{isALL=" + isAll + "}失败:" + response.message());
            }
        } catch (Exception ex) {
            logger.error("推送消息{" + type + "},{isALL=" + isAll + "}失败:" + ex.getMessage());
        }
    }

    //设置请求头
    private void settingRequestHeader(@NotNull Request.Builder request, @NotNull String deviceType) {
        String type = deviceType.toLowerCase();
        String waitingAuthString = null;
        if (type.equals("ios")) {
            waitingAuthString = String.format("%s:%s", this.appSettingConfigs.getXgConfigs().getIosAppid(), this.appSettingConfigs.getXgConfigs().getIosSecrect());
        } else {
            waitingAuthString = String.format("%s:%s", this.appSettingConfigs.getXgConfigs().getAndroidAppid(), this.appSettingConfigs.getXgConfigs().getAndroidSecrect());
        }
        //加密设备和对应密钥
        byte[] bytes = Base64.getEncoder().encode(waitingAuthString.getBytes());
        String base64EncodeString = new String(bytes);
        request.addHeader("Authorization", "Basic " + base64EncodeString);
        request.addHeader("Content-Type", "application/json");
    }

}
