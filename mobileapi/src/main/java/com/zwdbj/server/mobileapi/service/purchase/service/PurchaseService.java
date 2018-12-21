package com.zwdbj.server.mobileapi.service.purchase.service;

import com.alibaba.fastjson.JSONObject;
import com.zwdbj.server.mobileapi.service.purchase.model.Receipt;
import com.zwdbj.server.mobileapi.service.purchase.model.RequestMsg;
import com.zwdbj.server.mobileapi.service.purchase.model.ResponseMsg;
import com.zwdbj.server.mobileapi.service.userAssets.model.UserCoinDetailAddInput;
import com.zwdbj.server.mobileapi.service.userAssets.model.UserCoinDetailModifyInput;
import com.zwdbj.server.mobileapi.service.userAssets.service.IUserAssetService;
import com.zwdbj.server.utility.model.ServiceStatusInfo;
import org.apache.http.util.TextUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
public class PurchaseService {
    @Autowired
    IUserAssetService userAssetServiceImpl;
    private Logger logger = LoggerFactory.getLogger(PurchaseService.class);
    private URL url;
    private BufferedReader reader = null;
    private String result = "";

    public ServiceStatusInfo<Object> purchaseStatus(RequestMsg requestMsg) {
        String json = JSONObject.toJSONString(requestMsg.getReceipt());
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

            if (json != null && !TextUtils.isEmpty(json)) {
                byte[] writeBytes = json.getBytes();
                conn.setRequestProperty("Content-Length", String.valueOf(writeBytes.length));
                OutputStream outwritestream = conn.getOutputStream();
                outwritestream.write(json.getBytes());
                outwritestream.flush();
                outwritestream.close();

            }
            if (conn.getResponseCode() == 200) {
                reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                result = reader.readLine();
                // TODO 添加金币详情及其他表的金币数据
                /*Receipt receipt = (Receipt) JSONObject.parse(result);
                UserCoinDetailAddInput addInput = new UserCoinDetailAddInput();
                addInput.setTitle("充值"+receipt+"金币");
                addInput.setNum(input.getCoins());
                addInput.setExtraData(result);
                addInput.setType("PAY");
                addInput.setTradeNo("");
                addInput.setTradeType("APPLEPAY");
                addInput.setStatus("SUCCESS");
                long id = this.userAssetServiceImpl.addUserCoinDetail(userId,addInput);
                UserCoinDetailModifyInput coinDetailModifyInput = new UserCoinDetailModifyInput();
                coinDetailModifyInput.setId(id);
                coinDetailModifyInput.setType("PAY");
                coinDetailModifyInput.setStatus("SUCCESS");
                coinDetailModifyInput.setTradeNo("");
                this.userAssetServiceImpl.updateUserCoinDetail(coinDetailModifyInput);*/
                logger.info(result);


            }
            return new ServiceStatusInfo<>(0, "", JSONObject.parse(result));

        } catch (Exception e) {
            logger.info(e.getMessage());
            return new ServiceStatusInfo<>(1, "购买失败" + e.getMessage(), null);
        } finally {
            if (reader != null) {

                try {
                    reader.close();
                } catch (IOException e) {
                    logger.info(e.getMessage());
                }
            }
        }

    }


}
