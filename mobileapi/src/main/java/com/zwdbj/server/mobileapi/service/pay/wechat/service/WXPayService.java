package com.zwdbj.server.mobileapi.service.pay.wechat.service;

import com.zwdbj.server.mobileapi.service.pay.wechat.model.ChargeCoinDto;
import com.zwdbj.server.mobileapi.service.pay.wechat.model.ChargeCoinInput;
import com.zwdbj.server.mobileapi.service.userAssets.model.UserCoinDetailAddInput;
import com.zwdbj.server.mobileapi.service.userAssets.model.UserCoinDetailModifyInput;
import com.zwdbj.server.mobileapi.service.userAssets.service.IUserAssetService;
import com.zwdbj.server.pay.wechat.wechatpay.WXPayAppCfg;
import com.zwdbj.server.pay.wechat.wechatpay.model.*;
import com.zwdbj.server.pay.wechat.wechatpay.service.WechatPayService;
import com.zwdbj.server.utility.model.ServiceStatusInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
public class WXPayService {
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
    public ServiceStatusInfo<ChargeCoinDto> chargeCoins(ChargeCoinInput input, long userId) {
        // TODO 解析充值模板，当前直接解析金币
        // 生成充值明细订单
        // 1:10比例充值金币，单位分
        int rmbs = 0;
        if(this.wxPayAppCfg.isSandBox()) {
            rmbs = 201;
        } else {
            rmbs = (input.getCoins()/10)*100;
        }
        rmbs = 1;
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
        ChargeCoinDto chargeCoinDto = new ChargeCoinDto();
        chargeCoinDto.setPrepayId(unifiedOrderDtoServiceStatusInfo.getData().getPrepayId());
        chargeCoinDto.setNonceStr(mapServiceStatusInfo.getData().get("noncestr"));
        chargeCoinDto.setPackageN(mapServiceStatusInfo.getData().get("package"));
        chargeCoinDto.setSign(mapServiceStatusInfo.getData().get("sign"));
        chargeCoinDto.setTimestamp(mapServiceStatusInfo.getData().get("timestamp"));
        chargeCoinDto.setOutTradeNo(String.valueOf(id));
        return new ServiceStatusInfo<>(0,"OK",chargeCoinDto);
    }

    /**
     * @param input
     * @return 订单支付查询结果
     */
    public ServiceStatusInfo<OrderPayResultDto> orderQuery(OrderQueryInput input) {
        ServiceStatusInfo<OrderPayResultDto> serviceStatusInfo = this.wechatPayService.orderQuery(input);
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
        ServiceStatusInfo<PayNotifyResult> stringServiceStatusInfo = this.wechatPayService.responseWeChatPayResult(resFromWX);
        if(!stringServiceStatusInfo.isSuccess()) {
            return new ServiceStatusInfo<>(stringServiceStatusInfo.getCode(),stringServiceStatusInfo.getMsg(),null);
        }
        processPayResult(stringServiceStatusInfo.getData().getPayResultDto());
        return new ServiceStatusInfo<>(0,"OK",stringServiceStatusInfo.getData().getResponseWeChatXML());
    }
    @Transactional
    protected void processPayResult(OrderPayResultDto resultDto) {
        if (resultDto.getTradeState().equals("SUCCESS")) {
            UserCoinDetailModifyInput coinDetailModifyInput = new UserCoinDetailModifyInput();
            coinDetailModifyInput.setId(Long.parseLong(resultDto.getOutTradeNo()));
            if (this.wxPayAppCfg.isSandBox()) {
                coinDetailModifyInput.setNum(10);
            } else {
                coinDetailModifyInput.setNum(
                        resultDto.getTotalFee() / 100 * 10
                );
            }
            coinDetailModifyInput.setType("PAY");
            coinDetailModifyInput.setStatus("SUCCESS");
            this.userAssetServiceImpl.updateUserCoinDetail(coinDetailModifyInput);
        }
    }
}
