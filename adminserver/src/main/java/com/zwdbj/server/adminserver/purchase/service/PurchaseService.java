package com.zwdbj.server.adminserver.purchase.service;

import com.alibaba.fastjson.JSONObject;
import com.zwdbj.server.adminserver.purchase.model.ResponseMsg;
import org.apache.http.util.TextUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
public class PurchaseService {
    private Logger logger = LoggerFactory.getLogger(PurchaseService.class);
    private URL url;
    private BufferedReader reader = null;
    private String result = "";

    public ResponseMsg purchaseStatus(String msg) {
        String json = JSONObject.toJSONString(msg);
        try {
            //苹果服务器url
            url = new URL(" https://buy.itunes.apple.com/verifyReceipt ");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            //设置是否向connection输出
            conn.setDoInput(true);
            conn.setDoOutput(true);

            //设置请求方式为post
            conn.setRequestMethod("POST");

            conn.setUseCaches(false);
            conn.setInstanceFollowRedirects(true);
            //设置文件类型
            conn.setRequestProperty("content-Type", "application/json");
            //设置接受类型
            conn.setRequestProperty("accept", "application/json");

            if (msg != null && !TextUtils.isEmpty(msg)) {
                byte[] writeBytes = msg.getBytes();
                conn.setRequestProperty("Content-Length", String.valueOf(writeBytes.length));
                OutputStream outwritestream = conn.getOutputStream();
                outwritestream.write(msg.getBytes());
                outwritestream.flush();
                outwritestream.close();

            }
            if (conn.getResponseCode() == 200) {
                reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                result = reader.readLine();
            }


        } catch (Exception e) {
            logger.info(e.getMessage());
        } finally {
            if (reader != null) {

                try {
                    reader.close();
                } catch (IOException e) {
                    logger.info(e.getMessage());
                }
            }
        }
        return (ResponseMsg) JSONObject.parse(result);
    }


}
