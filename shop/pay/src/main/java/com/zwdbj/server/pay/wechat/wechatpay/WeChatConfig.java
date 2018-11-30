package com.zwdbj.server.pay.wechat.wechatpay;

import com.github.wxpay.sdk.WXPayConfig;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

public class WeChatConfig implements WXPayConfig {

    private static volatile WeChatConfig payCfg = null;
    public static WeChatConfig payConfig() {
        if (payCfg == null) {
            synchronized (WeChatConfig.class) {
                if (payCfg == null) {
                    try {
                        payCfg = new WeChatConfig();
                    } catch ( Exception ex ) {
                        System.out.println(ex.getMessage());
                    }
                }
            }
        }
        return payCfg;
    }

    private byte[] certData;
    public WeChatConfig() throws Exception {
        //TODO 需要调试此模块请联系李明儒获取证书
        String certPath = "/Users/limingru/.ssh/apiclient_cert.p12";
        File file = new File(certPath);
        InputStream certStream = new FileInputStream(file);
        this.certData = new byte[(int) file.length()];
        certStream.read(this.certData);
        certStream.close();
    }
    @Override
    public String getAppID() {
        return "wx07f6eadb69a4b1f5";
    }

    @Override
    public String getMchID() {
        return "1517178411";
    }

    @Override
    public String getKey() {
        return "JpP6T3AqRCNXJVKKbLai391yWd6tXsPD";
    }

    @Override
    public InputStream getCertStream() {
        ByteArrayInputStream certBis = new ByteArrayInputStream(this.certData);
        return certBis;
    }

    @Override
    public int getHttpConnectTimeoutMs() {
        return 8000;
    }

    @Override
    public int getHttpReadTimeoutMs() {
        return 10000;
    }
}
