package com.zwdbj.server.mobileapi.service.pay.wechat.service;

import com.zwdbj.server.mobileapi.service.pay.wechat.model.ChargeCoinDto;
import com.zwdbj.server.mobileapi.service.pay.wechat.model.ChargeCoinInput;
import com.zwdbj.server.mobileapi.service.userCoinDetails.model.UserCoinDetailAddInput;
import com.zwdbj.server.mobileapi.service.userCoinDetails.service.UserCoinDetailService;
import com.zwdbj.server.pay.wechat.wechatpay.model.UnifiedOrderDto;
import com.zwdbj.server.pay.wechat.wechatpay.model.UnifiedOrderInput;
import com.zwdbj.server.pay.wechat.wechatpay.service.WechatPayService;
import com.zwdbj.server.utility.common.UniqueIDCreater;
import com.zwdbj.server.utility.model.ServiceStatusInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class WeChatService {
    @Autowired
    private IUserAssetService userAssetServiceImpl;
    @Autowired
    private WechatPayService wechatPayService;
    private WXPayAppCfg wxPayAppCfg;
    public WeChatService(WXPayAppCfg cfg) {
        this.wxPayAppCfg = cfg;
    }
    /**
     * @param input 充值信息
     * @param userId 谁充值
     * @return 返回预付信息
     */
    public ServiceStatusInfo<ChargeCoinDto> chargeCoins(ChargeCoinInput input, long userId) {
        // TODO 解析充值模板，当前直接解析金币

        // 生成充值明细订单
        // 1:10比例充值金币，单位分
        int rmbs = (input.getCoins()/10)*100;
        // TODO @WPH 生成明细逻辑有问题
        UserCoinDetailAddInput detailInput = new UserCoinDetailAddInput();
        detailInput.setTitle("充值"+input.getCoins()+"金币");
        detailInput.setNum(input.getCoins());
        detailInput.setExtraData("");
        detailInput.setType("PAY");
        //TODO 应该返回明细信息
        this.userAssetServiceImpl.addUserCoinDetail(userId,detailInput);
        // 生成预付单
        // TODO 生成假的id
        long id = UniqueIDCreater.generateID();
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
}
