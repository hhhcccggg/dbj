package com.zwdbj.server.adminserver.service.qiniu.service;

import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.pili.Client;
import com.qiniu.pili.Hub;
import com.qiniu.pili.PiliException;
import com.qiniu.pili.Stream;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.model.FileInfo;
import com.qiniu.util.Auth;
import com.zwdbj.server.adminserver.easemob.api.EaseMobChatRoom;
import com.zwdbj.server.adminserver.config.AppConfigConstant;
import com.zwdbj.server.adminserver.service.living.mapper.ILivingMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
//@EnableScheduling
public class QiniuService {
    @Autowired
    private  ILivingMapper livingMapper;
    @Autowired
    EaseMobChatRoom easeMobChatRoom;
    @Autowired
    private RedisTemplate redisTemplate;

    public String uploadToken() {
        long expireTime = AppConfigConstant.QINIU_UPTOKEN_EXPIRETIME;
        Auth auth = Auth.create(AppConfigConstant.QINIU_ACCESS_KEY, AppConfigConstant.QINIU_ACCESS_SECRECT);
        String upToken = auth.uploadToken(AppConfigConstant.QINIU_BUCKET_NAME,null,expireTime,null);
        return upToken;
    }

    public String url(String key) {
        if (key == null || key.length()<=0) return "";
        if (key.toLowerCase().startsWith("http://") || key.toLowerCase().startsWith("https://")) {
            return key;
        }
        String encodedFileName = key;
        String publicUrl = String.format("%s/%s", AppConfigConstant.QINIU_BUCKET_DOMAIN, encodedFileName);
        return publicUrl;
    }

    public FileInfo getFileInfo(String key) {
        Configuration cfg = new Configuration(Zone.zone0());
        Auth auth = Auth.create(AppConfigConstant.QINIU_ACCESS_KEY, AppConfigConstant.QINIU_ACCESS_SECRECT);
        BucketManager bucketManager = new BucketManager(auth, cfg);
        try {
            FileInfo fileInfo = bucketManager.stat(AppConfigConstant.QINIU_BUCKET_NAME, key);
            return fileInfo;
        } catch (QiniuException ex) {
            return null;
        }
    }

    public String getRTMPPublishUrl(String streamName) {
        Client cli = new Client(AppConfigConstant.QINIU_ACCESS_KEY,AppConfigConstant.QINIU_ACCESS_SECRECT);
        String url = cli.RTMPPublishURL(AppConfigConstant.QINIU_LIVE_RTMP_PUBLISH_DOMAIN,AppConfigConstant.QINIU_LIVE_HUB_NAME,streamName,AppConfigConstant.QINIU_LIVE_PUBLISH_EXPIRETIME);
        return url;
    }
    public String getRTMPPlayUrl(String streamName) {
        Client cli = new Client(AppConfigConstant.QINIU_ACCESS_KEY,AppConfigConstant.QINIU_ACCESS_SECRECT);
        String url = cli.RTMPPlayURL(AppConfigConstant.QINIU_LIVE_RTMP_PLAY_DOMAIN,AppConfigConstant.QINIU_LIVE_HUB_NAME,streamName);
        return url;
    }

    public String getHLSPlayUrl(String streamName) {
        Client cli = new Client(AppConfigConstant.QINIU_ACCESS_KEY,AppConfigConstant.QINIU_ACCESS_SECRECT);
        String url = cli.HLSPlayURL(AppConfigConstant.QINIU_LIVE_HLS_PLAY_DOMAIN,AppConfigConstant.QINIU_LIVE_HUB_NAME,streamName);
        return url;
    }

    public boolean getLiveStatus(String streamName) {
        Client cli = new Client(AppConfigConstant.QINIU_ACCESS_KEY,AppConfigConstant.QINIU_ACCESS_SECRECT);
        Hub hub = cli.newHub(AppConfigConstant.QINIU_LIVE_HUB_NAME);
        try {
            Stream stream = hub.get(streamName);
            if (stream == null) return false;
            Stream.LiveStatus status = stream.liveStatus();
            return true;
        } catch (PiliException ex) {
            return false;
        }
    }

    public void disableStream(String streamName,Long livingId){
        Client cli = new Client(AppConfigConstant.QINIU_ACCESS_KEY,AppConfigConstant.QINIU_ACCESS_SECRECT);
        Hub hub = cli.newHub(AppConfigConstant.QINIU_LIVE_HUB_NAME);
        try {
            Stream stream = hub.get(streamName);
            stream.disable();

        }catch (PiliException e){
            e.printStackTrace();
        }


    }

}
