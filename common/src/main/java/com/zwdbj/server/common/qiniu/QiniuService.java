package com.zwdbj.server.common.qiniu;

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
import com.zwdbj.server.config.settings.AppSettingConfigs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
@Lazy
public class QiniuService {
    @Autowired
    private AppSettingConfigs appSettingConfigs;

    Logger logger = LoggerFactory.getLogger(QiniuService.class);


    public String uploadToken() {
        long expireTime = this.appSettingConfigs.getQiniuConfigs().getUptokenExpiretime();
        Auth auth = Auth.create(this.appSettingConfigs.getQiniuConfigs().getAccessKey(),
                this.appSettingConfigs.getQiniuConfigs().getAccessSecrect());
        String upToken = auth.uploadToken(this.appSettingConfigs.getQiniuConfigs().getBucketName(),null,expireTime,null);
        return upToken;
    }

    public String url(String key) {
        if (key == null || key.length()<=0) return "";
        if (key.toLowerCase().startsWith("http://") || key.toLowerCase().startsWith("https://")) {
            return key;
        }
        String encodedFileName = key;
        String publicUrl = String.format("%s/%s", this.appSettingConfigs.getQiniuConfigs().getBucketDomain(), encodedFileName);
        return publicUrl;
    }

    public FileInfo getFileInfo(String key) {
        Configuration cfg = new Configuration(Zone.zone0());
        Auth auth = Auth.create(this.appSettingConfigs.getQiniuConfigs().getAccessKey(),
                this.appSettingConfigs.getQiniuConfigs().getAccessSecrect());
        BucketManager bucketManager = new BucketManager(auth, cfg);
        try {
            FileInfo fileInfo = bucketManager.stat(this.appSettingConfigs.getQiniuConfigs().getBucketName(), key);
            return fileInfo;
        } catch (QiniuException ex) {
            return null;
        }
    }

    public String getRTMPPublishUrl(String streamName) {
        Client cli = new Client(this.appSettingConfigs.getQiniuConfigs().getAccessKey(),
                this.appSettingConfigs.getQiniuConfigs().getAccessSecrect());
        String url = cli.RTMPPublishURL(this.appSettingConfigs.getQiniuConfigs().getLiveRtmpPublishDomain()
                ,this.appSettingConfigs.getQiniuConfigs().getLiveHubName()
                ,streamName
                ,this.appSettingConfigs.getQiniuConfigs().getLivePublishExpiretime());
        return url;
    }

    public String getRTMPPlayUrl(String streamName) {
        Client cli = new Client(this.appSettingConfigs.getQiniuConfigs().getAccessKey(),
                this.appSettingConfigs.getQiniuConfigs().getAccessSecrect());
        String url = cli.RTMPPlayURL(this.appSettingConfigs.getQiniuConfigs().getLiveRtmpPlayDomain()
                ,this.appSettingConfigs.getQiniuConfigs().getLiveHubName()
                ,streamName);
        return url;
    }

    public String getHLSPlayUrl(String streamName) {
        Client cli = new Client(this.appSettingConfigs.getQiniuConfigs().getAccessKey(),
                this.appSettingConfigs.getQiniuConfigs().getAccessSecrect());
        String url = cli.HLSPlayURL(this.appSettingConfigs.getQiniuConfigs().getLiveHlsPlayDomain()
                ,this.appSettingConfigs.getQiniuConfigs().getLiveHubName(),streamName);
        return url;
    }

    public boolean getLiveStatus(String streamName) {
        Client cli = new Client(this.appSettingConfigs.getQiniuConfigs().getAccessKey(),
                this.appSettingConfigs.getQiniuConfigs().getAccessSecrect());
        Hub hub = cli.newHub(this.appSettingConfigs.getQiniuConfigs().getLiveHubName());
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
        Client cli = new Client(this.appSettingConfigs.getQiniuConfigs().getAccessKey(),
                this.appSettingConfigs.getQiniuConfigs().getAccessSecrect());
        Hub hub = cli.newHub(this.appSettingConfigs.getQiniuConfigs().getLiveHubName());
        try {
            Stream stream = hub.get(streamName);
            stream.disable();

        }catch (PiliException e){
            e.printStackTrace();
        }


    }

}