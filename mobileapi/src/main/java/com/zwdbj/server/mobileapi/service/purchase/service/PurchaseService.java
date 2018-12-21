package com.zwdbj.server.mobileapi.service.purchase.service;

import com.alibaba.fastjson.JSONObject;
import com.zwdbj.server.mobileapi.service.purchase.model.Receipt;
import com.zwdbj.server.mobileapi.service.purchase.model.RequestMsg;
import com.zwdbj.server.mobileapi.service.purchase.model.ResponseMsg;
import com.zwdbj.server.mobileapi.service.purchase.util.IosVerifyUtil;
import com.zwdbj.server.mobileapi.service.userAssets.model.UserCoinDetailAddInput;
import com.zwdbj.server.mobileapi.service.userAssets.model.UserCoinDetailModifyInput;
import com.zwdbj.server.mobileapi.service.userAssets.service.IUserAssetService;
import com.zwdbj.server.utility.model.ServiceStatusInfo;
import org.apache.http.util.TextUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.net.ssl.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@Service
public class PurchaseService {
    @Autowired
    IUserAssetService userAssetServiceImpl;
    private Logger logger = LoggerFactory.getLogger(PurchaseService.class);





    /**
     * @throws Exception
     * 苹果内购支付
     * @Title: doIosRequest
     * @Description:Ios客户端内购支付
     * @param  TransactionID ：交易单号  需要客户端传过来的参数1
     * @param  payload：需要客户端传过来的参数2
     * @throws
     */
    public ServiceStatusInfo<Map<String, Object>> doIosRequest(String TransactionID, String payload, long userId) throws Exception {
        try {
            Map<String, Object> map = new HashMap<>();
            Map<String, Object> mapChange = new HashMap<String, Object>();
            logger.info("客户端传过来的值1："+TransactionID+"客户端传过来的值2："+payload);

            String verifyResult =  IosVerifyUtil.buyAppVerify(payload,1); 			//1.先线上测试    发送平台验证
            if (verifyResult == null) {   											// 苹果服务器没有返回验证结果
                logger.info("无订单信息!");
            } else {  	    														// 苹果验证有返回结果
                logger.info("线上，苹果平台返回JSON:"+verifyResult);
                JSONObject job = JSONObject.parseObject(verifyResult);
                String states = job.getString("status");

                if("21007".equals(states)){											//是沙盒环境，应沙盒测试，否则执行下面
                    verifyResult =  IosVerifyUtil.buyAppVerify(payload,0);			//2.再沙盒测试  发送平台验证
                    logger.info("沙盒环境，苹果平台返回JSON:"+verifyResult);
                    job = JSONObject.parseObject(verifyResult);
                    states = job.getString("status");
                }

                logger.info("苹果平台返回值：job"+job);
                if (states.equals("0")){ // 前端所提供的收据是有效的    验证成功
                    String r_receipt = job.getString("receipt");
                    JSONObject returnJson = JSONObject.parseObject(r_receipt);
                    String in_app = returnJson.getString("in_app");
                    JSONObject in_appJson = JSONObject.parseObject(in_app.substring(1, in_app.length()-1));

                    String product_id = in_appJson.getString("product_id");
                    String transaction_id = in_appJson.getString("transaction_id");   // 订单号
                    String quantity = in_appJson.getString("quantity");   // 购买的商品数量
/************************************************+自己的业务逻辑**********************************************************/
                    //如果单号一致  则保存到数据库
                    if(TransactionID.equals(transaction_id)){
                        String [] moneys = product_id.split("\\.");
                        map.put("money", moneys[moneys.length-1]);
                    }
/************************************************+自己的业务逻辑end**********************************************************/
                    if((boolean) mapChange.get("success")){//用户金币数量新增成功
                        map.put("success", true);
                        map.put("message", "充值金币成功！");
                        map.put("status", states);
                        logger.info(map.toString());
                        return new ServiceStatusInfo<>(0,"充值金币成功",map);
                    }else{
                        map.put("success", false);
                        map.put("message", "充值金币失败！");
                        logger.info(map.toString());
                        return new ServiceStatusInfo<>(1,"充值金币失败",map);
                    }
                } else {
                    map.put("success", false);
                    map.put("message", "receipt数据有问题");
                    map.put("status", states);
                    logger.info(map.toString());
                    return new ServiceStatusInfo<>(1,"receipt数据有问题",map);
                }
            }

        }catch (Exception e){
            logger.info(e.getMessage());
            return new ServiceStatusInfo<>(1,"",null);
        }
        return null;

    }






   /* public String  purchaseStatus(String receipt,int type) {
        //String json = JSONObject.toJSONString(requestMsg.getReceipt());
        String str = String.format(Locale.CHINA, "{\"receipt-data\":\"" +receipt + "\"}");//拼成固定的格式传给平台
        String url = "";
        try {
            if (type==0){
                url=url_sandbox;
            }else {
                url=url_verify;
            }
            *//*SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, new TrustManager[] { new TrustAnyTrustManager() }, new java.security.SecureRandom());*//*
            //苹果服务器url
            URL console  = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) console.openConnection();

            //设置是否向connection输出
            conn.setDoInput(true);
            conn.setDoOutput(true);

            //设置请求方式为post
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Proxy-Connection", "Keep-Alive");

            conn.setUseCaches(false);
            conn.setInstanceFollowRedirects(true);
            //设置文件类型
            conn.setRequestProperty("content-Type", "application/json");
            //设置接受类型
            conn.setRequestProperty("accept", "application/json");

            if (!TextUtils.isEmpty(str)) {
                byte[] writeBytes = str.getBytes();
                conn.setRequestProperty("Content-Length", String.valueOf(writeBytes.length));
                OutputStream outwritestream = conn.getOutputStream();
                outwritestream.write(str.getBytes());
                outwritestream.flush();
                outwritestream.close();

            }
            if (conn.getResponseCode() == 200) {
                reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                result = reader.readLine();
                // TODO 添加金币详情及其他表的金币数据
                *//*Receipt receipt = (Receipt) JSONObject.parse(result);
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
                this.userAssetServiceImpl.updateUserCoinDetail(coinDetailModifyInput);*//*
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
*/

}
