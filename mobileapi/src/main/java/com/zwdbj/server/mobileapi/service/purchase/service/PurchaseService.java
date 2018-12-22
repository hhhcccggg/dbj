package com.zwdbj.server.mobileapi.service.purchase.service;

import com.alibaba.fastjson.JSONObject;
import com.zwdbj.server.mobileapi.service.purchase.model.Receipt;
import com.zwdbj.server.mobileapi.service.purchase.model.RequestMsg;
import com.zwdbj.server.mobileapi.service.purchase.model.ResponseMsg;
import com.zwdbj.server.mobileapi.service.purchase.util.IosVerifyUtil;
import com.zwdbj.server.mobileapi.service.userAssets.model.BuyCoinConfigModel;
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
     * @param  TransactionID ：交易标识符
     * @param  payload：二次验证的重要依据 receipt
     * @throws
     */
    public ServiceStatusInfo<ResponseMsg> doIosRequest(String TransactionID, String payload, long userId) throws Exception {
        try {
            ResponseMsg responseMsg = new ResponseMsg();
            logger.info("客户端传过来的值1："+TransactionID+"客户端传过来的值2："+payload);

            String verifyResult =  IosVerifyUtil.buyAppVerify(payload,1); 			//1.先线上测试    发送平台验证
            if (verifyResult == null) {   											// 苹果服务器没有返回验证结果
                logger.info("无订单信息!");
            } else {  	    														// 苹果验证有返回结果
                logger.info("线上，苹果平台返回JSON:"+verifyResult);
                JSONObject job = JSONObject.parseObject(verifyResult);
                String states = job.getString("status");

                if("21007".equals(states)){									//是沙盒环境，应沙盒测试，否则执行下面
                    verifyResult =  IosVerifyUtil.buyAppVerify(payload,0);			//2.再沙盒测试  发送平台验证
                    logger.info("沙盒环境，苹果平台返回JSON:"+verifyResult);
                    job = JSONObject.parseObject(verifyResult);
                    states = job.getString("status");
                }
                int status = Integer.valueOf(states);
                responseMsg.setStatus(status);
                responseMsg.setReceipt(verifyResult);

                logger.info("苹果平台返回值：job"+job);
                if (status==0){ // 前端所提供的收据是有效的    验证成功
                    String r_receipt = job.getString("receipt");
                    JSONObject returnJson = JSONObject.parseObject(r_receipt);
                    String in_app = returnJson.getString("in_app");
                    JSONObject in_appJson = JSONObject.parseObject(in_app.substring(1, in_app.length()-1));

                    String product_id = in_appJson.getString("product_id");
                    String transaction_id = in_appJson.getString("transaction_id");   // 订单号
/************************************************+自己的业务逻辑**********************************************************/
                    //如果单号一致  则保存到数据库
                    int a = 0;
                    if(TransactionID.equals(transaction_id)){
                        BuyCoinConfigModel coinConfigModel = this.userAssetServiceImpl.findCoinConfigByProductId(product_id,"IOS");
                        UserCoinDetailAddInput addInput = new UserCoinDetailAddInput();
                        addInput.setTitle(coinConfigModel.getTitle());
                        addInput.setNum(coinConfigModel.getCoins());
                        addInput.setExtraData(r_receipt);
                        addInput.setType("PAY");
                        addInput.setTradeNo(transaction_id);
                        addInput.setTradeType("APPLEPAY");
                        addInput.setStatus("SUCCESS");
                        long id = this.userAssetServiceImpl.addUserCoinDetail(userId,addInput);
                        UserCoinDetailModifyInput coinDetailModifyInput = new UserCoinDetailModifyInput();
                        coinDetailModifyInput.setId(id);
                        coinDetailModifyInput.setType("PAY");
                        coinDetailModifyInput.setStatus("SUCCESS");
                        coinDetailModifyInput.setTradeNo(transaction_id);
                       a =  this.userAssetServiceImpl.updateUserCoinDetail(coinDetailModifyInput);

                    }
/************************************************+自己的业务逻辑end**********************************************************/
                    if(a!=0){//用户金币数量新增成功
                        return new ServiceStatusInfo<>(0,"充值金币成功",responseMsg);
                    }else{
                        return new ServiceStatusInfo<>(1,"充值金币失败",responseMsg);
                    }
                } else {
                    return new ServiceStatusInfo<>(1,"receipt数据有问题",responseMsg);
                }
            }

        }catch (Exception e){
            logger.info(e.getMessage());
            return new ServiceStatusInfo<>(1,"出现异常"+e.getMessage(),null);
        }
        return null;

    }


}
