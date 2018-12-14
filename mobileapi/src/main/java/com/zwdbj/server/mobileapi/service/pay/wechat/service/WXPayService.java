package com.zwdbj.server.mobileapi.service.pay.wechat.service;

import com.alibaba.fastjson.JSON;
import com.zwdbj.server.mobileapi.service.pay.wechat.model.ChargeCoinWXResult;
import com.zwdbj.server.mobileapi.service.pay.model.ChargeCoinInput;
import com.zwdbj.server.mobileapi.service.userAssets.model.UserCoinDetailAddInput;
import com.zwdbj.server.mobileapi.service.userAssets.model.UserCoinDetailModifyInput;
import com.zwdbj.server.mobileapi.service.userAssets.service.IUserAssetService;
import com.zwdbj.server.pay.wechat.wechatpay.WXPayAppCfg;
import com.zwdbj.server.pay.wechat.wechatpay.model.*;
import com.zwdbj.server.pay.wechat.wechatpay.service.WechatPayService;
import com.zwdbj.server.utility.model.ServiceStatusInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
public class WXPayService {
    private Logger logger = LoggerFactory.getLogger(WXPayService.class);
    @Autowired
    private IUserAssetService userAssetServiceImpl;
    @Autowired
    private WechatPayService wechatPayService;
    private WXPayAppCfg wxPayAppCfg;
    public WXPayService(WXPayAppCfg cfg) {
        this.wxPayAppCfg = cfg;
    }
    /**
     * @param input 充值信息
     * @param userId 谁充值
     * @return 返回预付信息
     */
    @Transactional
    public ServiceStatusInfo<ChargeCoinWXResult> chargeCoins(ChargeCoinInput input, long userId) {
        // TODO 解析充值模板，当前直接解析金币
        // 生成充值明细订单
        // 1:10比例充值金币，单位分
        int rmbs = 0;
        if(this.wxPayAppCfg.isSandBox()) {
            rmbs = 201;
        } else {
            if (this.wxPayAppCfg.getIsTest()) {
                rmbs = 1;
            } else {
                rmbs = (input.getCoins() / 10) * 100;
            }
        }
        UserCoinDetailAddInput detailInput = new UserCoinDetailAddInput();
        detailInput.setTitle("充值"+input.getCoins()+"金币");
        detailInput.setNum(input.getCoins());
        detailInput.setExtraData("");
        detailInput.setType("PAY");
        long id = this.userAssetServiceImpl.addUserCoinDetail(userId,detailInput);
        // 生成预付单
        UnifiedOrderInput unifiedOrderInput = new UnifiedOrderInput();
        unifiedOrderInput.setBody("爪子 充值"+input.getCoins()+"金币");
        unifiedOrderInput.setFeeType("CNY");
        unifiedOrderInput.setNotifyUrl(this.wxPayAppCfg.getPayResultCallbackUrl());
        unifiedOrderInput.setTradeType("APP");
        unifiedOrderInput.setTotalFee(rmbs);
        unifiedOrderInput.setOutTradeNo(String.valueOf(id));
        ServiceStatusInfo<UnifiedOrderDto> unifiedOrderDtoServiceStatusInfo =
                this.wechatPayService.unifiedOrder(unifiedOrderInput);
        if (!unifiedOrderDtoServiceStatusInfo.isSuccess()) {
            return new ServiceStatusInfo<>(1,unifiedOrderDtoServiceStatusInfo.getMsg(),null);
        }
        //构建预付订单信息
        ServiceStatusInfo<Map<String,String>> mapServiceStatusInfo =
                this.wechatPayService.invokePayRequestInfo(unifiedOrderDtoServiceStatusInfo.getData().getPrepayId());
        if (!mapServiceStatusInfo.isSuccess()) {
            return new ServiceStatusInfo<>(1,mapServiceStatusInfo.getMsg(),null);
        }
        ChargeCoinWXResult chargeCoinWXResult = new ChargeCoinWXResult();
        chargeCoinWXResult.setPrepayId(unifiedOrderDtoServiceStatusInfo.getData().getPrepayId());
        chargeCoinWXResult.setNonceStr(mapServiceStatusInfo.getData().get("noncestr"));
        chargeCoinWXResult.setPackageN(mapServiceStatusInfo.getData().get("package"));
        chargeCoinWXResult.setSign(mapServiceStatusInfo.getData().get("sign"));
        chargeCoinWXResult.setTimestamp(mapServiceStatusInfo.getData().get("timestamp"));
        chargeCoinWXResult.setOutTradeNo(String.valueOf(id));
        return new ServiceStatusInfo<>(0,"OK", chargeCoinWXResult);
    }

    /**
     * @param input
     * @return 订单支付查询结果
     */
    public ServiceStatusInfo<OrderPayResultDto> orderQuery(OrderQueryInput input) {
        ServiceStatusInfo<OrderPayResultDto> serviceStatusInfo = this.wechatPayService.orderQuery(input);
        logger.info(JSON.toJSONString(serviceStatusInfo));
        if (!serviceStatusInfo.isSuccess()) {
            return serviceStatusInfo;
        }
        processPayResult(serviceStatusInfo.getData());
        return serviceStatusInfo;
    }

    /**
     * @param resFromWX 来自微信的支付结果通知
     * @return 响应微信支付结果通知
     */
    public ServiceStatusInfo<String> responseWeChatPayResult(String resFromWX) {
        logger.info("收到微信支付回调："+resFromWX);
        ServiceStatusInfo<PayNotifyResult> stringServiceStatusInfo = this.wechatPayService.responseWeChatPayResult(resFromWX);
        if(!stringServiceStatusInfo.isSuccess()) {
            logger.info(stringServiceStatusInfo.getMsg());
            return new ServiceStatusInfo<>(stringServiceStatusInfo.getCode(),stringServiceStatusInfo.getMsg(),null);
        }
        processPayResult(stringServiceStatusInfo.getData().getPayResultDto());
        return new ServiceStatusInfo<>(0,"OK",stringServiceStatusInfo.getData().getResponseWeChatXML());
    }
    @Transactional
    protected void processPayResult(OrderPayResultDto resultDto) {
        logger.info("处理微信支付结果"+resultDto.toString());
        if (resultDto.getTradeState().equals("SUCCESS")) {
            logger.info("交易成功");
            UserCoinDetailModifyInput coinDetailModifyInput = new UserCoinDetailModifyInput();
            coinDetailModifyInput.setId(Long.parseLong(resultDto.getOutTradeNo()));
            coinDetailModifyInput.setType("PAY");
            coinDetailModifyInput.setStatus("SUCCESS");
            this.userAssetServiceImpl.updateUserCoinDetail(coinDetailModifyInput);
        } else {
            logger.info("交易失败");
        }
    }
}