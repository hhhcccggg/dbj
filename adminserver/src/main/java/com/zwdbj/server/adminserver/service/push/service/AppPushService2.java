/*
package com.zwdbj.server.adminserver.service.push.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tencent.xinge.*;
import com.tencent.xinge.bean.*;
import com.tencent.xinge.bean.ClickAction;
import com.tencent.xinge.bean.ios.Alert;
import com.tencent.xinge.bean.ios.Aps;
import com.tencent.xinge.push.app.PushAppRequest;
import com.zwdbj.server.adminserver.config.AppConfigConstant;
import com.zwdbj.server.adminserver.service.push.model.*;
import com.zwdbj.server.adminserver.service.user.service.UserService;
import com.zwdbj.server.adminserver.service.userDeviceTokens.model.AdUserDeviceTokenDto;
import com.zwdbj.server.adminserver.service.userDeviceTokens.service.UserDeviceTokensService;
import com.zwdbj.server.adminserver.service.video.model.VideoDetailInfoDto;
import com.zwdbj.server.adminserver.service.video.service.VideoService;
import com.zwdbj.server.probuf.middleware.mq.QueueWorkInfoModel;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class AppPushService2 {
    @Autowired
    protected UserService userService;
    @Autowired
    protected VideoService videoService;
    protected Logger logger = LoggerFactory.getLogger(AppPushService2.class);
    protected final OkHttpClient client = new OkHttpClient();
    @Autowired
    private UserDeviceTokensService userDeviceTokensService;

    public XingeApp xingeAndroid = null;
    public XingeApp xingeIOS = null;
    private String appIdAndroid = AppConfigConstant.XG_ANDROID_APPID;
    private String secretKeyAndroid = AppConfigConstant.XG_ANDROID_SECRECT;
    private String appIdIOS = AppConfigConstant.XG_IOS_APPID;
    private String secretKeyIOS = AppConfigConstant.XG_IOS_SECRECT;

    public boolean push(QueueWorkInfoModel.QueueWorkPush pushData) {
        //消息类型0:系统消息,1:点赞类2:粉丝类3:评论4:关注人发布视频5:关注人发布直播
        try {
            if (pushData == null) return true;
            if (pushData.getMessageType()==0){
                String pushTitle = "爪子提醒";

                String pushDescription = pushData.getMsgContent();//获取消息文本内容
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
                logger.info("推送消息title:"+pushTitle+",内容："+pushDescription);
            }else {
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
        int type=0;
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
            pushResDataContent.setId(resId);
            pushResDataContent.setType(type);
        }

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
        String jsonBody = "";
        org.json.JSONObject j =null;
        PushAppRequest pushAppRequest = new PushAppRequest();
        Message mag = new Message();
        mag.setTitle(message.getTitle());
        mag.setContent(message.getMsgContent());
        if (isAll) {
            pushAppRequest.setAudience_type(AudienceType.all);
        } else  {
            pushAppRequest.setAudience_type(AudienceType.account);
            ArrayList<String> accounts = new ArrayList<>();
            accounts.add(String.valueOf(toUserId));
            pushAppRequest.setAccount_list(accounts);
        }
        if (type.equals("ios")) {
            xingeIOS = new XingeApp(appIdIOS,secretKeyIOS);
            MessageIOS ios = new MessageIOS();
            Aps aps = new Aps();
            Alert alert = new Alert();
            alert.setSubtitle(message.getMsgContent());
            aps.setAlert(alert);
            aps.setBadge_type(1);
            ios.setAps(aps);
            ios.setCustom(JSON.toJSONString(message.getExtraData()));
            pushAppRequest.setPlatform(Platform.ios);
            if ("dev".equals(AppConfigConstant.PUSH_ENV)){
                pushAppRequest.setEnvironment(Environment.dev);
            }
            mag.setIos(ios);
            pushAppRequest.setMessage(mag);
            j = xingeIOS.pushApp(pushAppRequest);
        } else {
            xingeAndroid = new XingeApp(appIdAndroid,secretKeyAndroid);
            MessageAndroid android = new MessageAndroid();
            android.setCustom_content(JSON.toJSONString(message.getExtraData()));
            ClickAction action = new ClickAction();
            if (message.getType()==1){
                action.setActivity("com.zwdbj.aichongpai.ui.message.LikeActivity");
            }else if (message.getType()==2){
                action.setActivity("com.zwdbj.aichongpai.ui.message.FansActivity");
            }else if (message.getType()==3){
                action.setActivity("com.zwdbj.aichongpai.ui.message.CommentsActivity");
            }
            android.setAction(action);
            mag.setAndroid(android);
            pushAppRequest.setMessage(mag);
            j = xingeAndroid.pushApp(pushAppRequest);
        }
        jsonBody = JSON.toJSONString(pushAppRequest);
        logger.info("推送消息{"+type+"},{isALL="+isAll+"}请求:"+jsonBody);
        logger.info("推送结果返回："+j.toString());
    }





}
*/
