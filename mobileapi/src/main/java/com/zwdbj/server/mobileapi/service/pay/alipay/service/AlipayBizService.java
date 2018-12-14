package com.zwdbj.server.mobileapi.service.pay.alipay.service;

import com.zwdbj.server.mobileapi.service.pay.alipay.model.ChargeCoinAlipayResult;
import com.zwdbj.server.mobileapi.service.pay.model.ChargeCoinInput;
import com.zwdbj.server.mobileapi.service.userAssets.model.UserCoinDetailAddInput;
import com.zwdbj.server.mobileapi.service.userAssets.model.UserCoinDetailModifyInput;
import com.zwdbj.server.mobileapi.service.userAssets.service.IUserAssetService;
import com.zwdbj.server.pay.alipay.AlipayService;
import com.zwdbj.server.pay.alipay.model.AppPayInput;
import com.zwdbj.server.pay.alipay.model.AppPayResult;
import com.zwdbj.server.pay.alipay.model.OrderQueryInput;
import com.zwdbj.server.pay.alipay.model.OrderQueryResult;
import com.zwdbj.server.utility.model.ServiceStatusInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class AlipayBizService {
    @Autowired
    private AlipayService alipayService;
    private Logger logger = LoggerFactory.getLogger(AlipayBizService.class);
    @Autowired
    private IUserAssetService userAssetServiceImpl;

    /**
     * @param input 充值信息
     * @param userId 谁充值
     * @return 返回订单信息
     */
    @Transactional
    public ServiceStatusInfo<ChargeCoinAlipayResult> chargeCoins(ChargeCoinInput input,long userId) {
        // 1:10比例充值金币，单位分
        int rmbs = 0;
        rmbs = (input.getCoins() / 10) * 100;
        rmbs = 1;// 测试
        UserCoinDetailAddInput detailInput = new UserCoinDetailAddInput();
        detailInput.setTitle("充值"+input.getCoins()+"金币");
        detailInput.setNum(input.getCoins());
        detailInput.setExtraData("");
        detailInput.setType("PAY");
        long id = this.userAssetServiceImpl.addUserCoinDetail(userId,detailInput);
        AppPayInput appPayInput = new AppPayInput();
        appPayInput.setBody("充值"+input.getCoins()+"金币");
        appPayInput.setSubject("充值金币");
        appPayInput.setOutTradeNo(String.valueOf(id));
        appPayInput.setTimeoutExpress("15m");
        float amount = rmbs/100f;
        BigDecimal b = new BigDecimal(amount);
        float f1 = b.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
        appPayInput.setTotalAmount(String.valueOf(f1));

        ServiceStatusInfo<AppPayResult> serviceStatusInfo = this.alipayService.appPay(appPayInput);
        if (!serviceStatusInfo.isSuccess()) {
            return new ServiceStatusInfo<>(1,serviceStatusInfo.getMsg(),null);
        }

        ChargeCoinAlipayResult result = new ChargeCoinAlipayResult();
        result.setBody(appPayInput.getBody());
        result.setOutTradeNo(appPayInput.getOutTradeNo());
        result.setSubject(appPayInput.getSubject());
        result.setTimeoutExpress(appPayInput.getTimeoutExpress());
        result.setTotalAmount(Float.valueOf(appPayInput.getTotalAmount()));
        result.setOrderString(serviceStatusInfo.getData().getOrderString());
        return new ServiceStatusInfo<>(0,"OK",result);
    }

    public ServiceStatusInfo<OrderQueryResult> orderQuery(OrderQueryInput input) {
        ServiceStatusInfo<OrderQueryResult> serviceStatusInfo = this.alipayService.orderQuery(input);
        if (!serviceStatusInfo.isSuccess()) {
            logger.warn(serviceStatusInfo.getMsg());
            return serviceStatusInfo;
        }
        boolean isSuccess = serviceStatusInfo.getData().getTradeStatus().equals("TRADE_SUCCESS");
        processPayResult(input.getOutTradeNo(),isSuccess);
        return serviceStatusInfo;
    }
    @Transactional
    protected void processPayResult(String tradeNo,boolean isSuccess) {
        if (!isSuccess) return;
        long id = Long.parseLong(tradeNo);
        UserCoinDetailModifyInput coinDetailModifyInput = new UserCoinDetailModifyInput();
        coinDetailModifyInput.setId(id);
        coinDetailModifyInput.setType("PAY");
        coinDetailModifyInput.setStatus("SUCCESS");
        this.userAssetServiceImpl.updateUserCoinDetail(coinDetailModifyInput);
    }
}
