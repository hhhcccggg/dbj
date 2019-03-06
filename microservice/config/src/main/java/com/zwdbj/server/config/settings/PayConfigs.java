package com.zwdbj.server.config.settings;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
@ConfigurationProperties(prefix = "app.pay")
public class PayConfigs implements Serializable {

    private static final long serialVersionUID = 3605858785057070800L;
    /**
     * 是否在沙盒运行微信支付
     */
    private boolean wechatIsSandbox;
    /**
     * 微信支付知否为测试
     */
    private boolean wechatTest;
    private String wechatPayResultCallbackUrl;
    private String aliPayResultCallbackUrl;
    private String aliOrderPayResultCallbackUrl;
    private String aliOrderRefundResultCallbackUrl;
    private String wechatOrderRefundResultCallbackUrl;
    private String wechatOrderPayResultCallbackUrl;
    /**
     * 需要调试此模块请联系李明儒获取证书
     */
    private String wechatCertPath;

    public boolean isWechatIsSandbox() {
        return wechatIsSandbox;
    }

    public void setWechatIsSandbox(boolean wechatIsSandbox) {
        this.wechatIsSandbox = wechatIsSandbox;
    }

    public boolean isWechatTest() {
        return wechatTest;
    }

    public void setWechatTest(boolean wechatTest) {
        this.wechatTest = wechatTest;
    }

    public String getWechatPayResultCallbackUrl() {
        return wechatPayResultCallbackUrl;
    }

    public void setWechatPayResultCallbackUrl(String wechatPayResultCallbackUrl) {
        this.wechatPayResultCallbackUrl = wechatPayResultCallbackUrl;
    }

    public String getAliPayResultCallbackUrl() {
        return aliPayResultCallbackUrl;
    }

    public void setAliPayResultCallbackUrl(String aliPayResultCallbackUrl) {
        this.aliPayResultCallbackUrl = aliPayResultCallbackUrl;
    }

    public String getAliOrderPayResultCallbackUrl() {
        return aliOrderPayResultCallbackUrl;
    }

    public void setAliOrderPayResultCallbackUrl(String aliOrderPayResultCallbackUrl) {
        this.aliOrderPayResultCallbackUrl = aliOrderPayResultCallbackUrl;
    }

    public String getAliOrderRefundResultCallbackUrl() {
        return aliOrderRefundResultCallbackUrl;
    }

    public void setAliOrderRefundResultCallbackUrl(String aliOrderRefundResultCallbackUrl) {
        this.aliOrderRefundResultCallbackUrl = aliOrderRefundResultCallbackUrl;
    }

    public String getWechatOrderRefundResultCallbackUrl() {
        return wechatOrderRefundResultCallbackUrl;
    }

    public void setWechatOrderRefundResultCallbackUrl(String wechatOrderRefundResultCallbackUrl) {
        this.wechatOrderRefundResultCallbackUrl = wechatOrderRefundResultCallbackUrl;
    }

    public String getWechatOrderPayResultCallbackUrl() {
        return wechatOrderPayResultCallbackUrl;
    }

    public void setWechatOrderPayResultCallbackUrl(String wechatOrderPayResultCallbackUrl) {
        this.wechatOrderPayResultCallbackUrl = wechatOrderPayResultCallbackUrl;
    }

    public String getWechatCertPath() {
        return wechatCertPath;
    }

    public void setWechatCertPath(String wechatCertPath) {
        this.wechatCertPath = wechatCertPath;
    }
}
