package com.zwdbj.server.pay.wechat.wechatpay;

import com.github.wxpay.sdk.WXPayConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

public class WeChatPayConfig implements WXPayConfig {
    @Autowired
    private static Logger logger= LoggerFactory.getLogger(WeChatPayConfig.class);

    private static volatile WeChatPayConfig payCfg = null;
    public static WeChatPayConfig payConfig(String certPath) {
        if (payCfg == null) {
            synchronized (WeChatPayConfig.class) {
                if (payCfg == null) {
                    try {
                        payCfg = new WeChatPayConfig(certPath);
                    } catch ( Exception ex ) {
                        logger.error("config:"+ex.getMessage());
                    }
                }
            }
        }
        return payCfg;
    }
    public static WeChatPayConfig sandBoxPayConfig(String sandBoxKey,String certPath) throws Exception {
        WeChatPayConfig sandBoxConfig = new WeChatPayConfig(certPath);
        sandBoxConfig.setKey(sandBoxKey);
        return sandBoxConfig;
    }

    private byte[] certData;
    private String key;
    public WeChatPayConfig(String certPath) throws Exception {
        File file = new File(certPath);
        InputStream certStream = new FileInputStream(file);
        this.certData = new byte[(int) file.length()];
        certStream.read(this.certData);
        certStream.close();
        this.setKey("JpP6T3AqRCNXJVKKbLai391yWd6tXsPD");
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
        return key;
    }

    public void setKey(String key) {
        this.key = key;
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
