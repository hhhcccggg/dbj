package com.zwdbj.server.config.settings;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AppSettingConfigs {
    @Autowired
    private AliyunConfigs aliyunConfigs;
    @Autowired
    private EasemobConfigs easemobConfigs;
    @Autowired
    private H5Configs h5Configs;
    @Autowired
    private PayConfigs payConfigs;
    @Autowired
    private PushConfigs pushConfigs;
    @Autowired
    private QiniuConfigs qiniuConfigs;
    @Autowired
    private SmsSendConfigs smsSendConfigs;
    @Autowired
    private LetterSendConfigs letterSendConfigs;
    @Autowired
    private SwaggerConfigs swaggerConfigs;
    @Autowired
    private XgConfigs xgConfigs;
    @Autowired
    private YouZanConfigs youZanConfigs;
    @Autowired
    private ElasticsearchConfigs elasticsearchConfigs;

    public ElasticsearchConfigs getElasticsearchConfigs() {
        return elasticsearchConfigs;
    }

    public void setElasticsearchConfigs(ElasticsearchConfigs elasticsearchConfigs) {
        this.elasticsearchConfigs = elasticsearchConfigs;
    }

    public AliyunConfigs getAliyunConfigs() {
        return aliyunConfigs;
    }

    public void setAliyunConfigs(AliyunConfigs aliyunConfigs) {
        this.aliyunConfigs = aliyunConfigs;
    }

    public EasemobConfigs getEasemobConfigs() {
        return easemobConfigs;
    }

    public void setEasemobConfigs(EasemobConfigs easemobConfigs) {
        this.easemobConfigs = easemobConfigs;
    }

    public H5Configs getH5Configs() {
        return h5Configs;
    }

    public void setH5Configs(H5Configs h5Configs) {
        this.h5Configs = h5Configs;
    }

    public PayConfigs getPayConfigs() {
        return payConfigs;
    }

    public void setPayConfigs(PayConfigs payConfigs) {
        this.payConfigs = payConfigs;
    }

    public PushConfigs getPushConfigs() {
        return pushConfigs;
    }

    public void setPushConfigs(PushConfigs pushConfigs) {
        this.pushConfigs = pushConfigs;
    }

    public QiniuConfigs getQiniuConfigs() {
        return qiniuConfigs;
    }

    public void setQiniuConfigs(QiniuConfigs qiniuConfigs) {
        this.qiniuConfigs = qiniuConfigs;
    }

    public SmsSendConfigs getSmsSendConfigs() {
        return smsSendConfigs;
    }

    public void setSmsSendConfigs(SmsSendConfigs smsSendConfigs) {
        this.smsSendConfigs = smsSendConfigs;
    }

    public SwaggerConfigs getSwaggerConfigs() {
        return swaggerConfigs;
    }

    public void setSwaggerConfigs(SwaggerConfigs swaggerConfigs) {
        this.swaggerConfigs = swaggerConfigs;
    }

    public XgConfigs getXgConfigs() {
        return xgConfigs;
    }

    public void setXgConfigs(XgConfigs xgConfigs) {
        this.xgConfigs = xgConfigs;
    }

    public YouZanConfigs getYouZanConfigs() {
        return youZanConfigs;
    }

    public void setYouZanConfigs(YouZanConfigs youZanConfigs) {
        this.youZanConfigs = youZanConfigs;
    }

    public LetterSendConfigs getLetterSendConfigs() {
        return letterSendConfigs;
    }

    public void setLetterSendConfigs(LetterSendConfigs letterSendConfigs) {
        this.letterSendConfigs = letterSendConfigs;
    }
}
