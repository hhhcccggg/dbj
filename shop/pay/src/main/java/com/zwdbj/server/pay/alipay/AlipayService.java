package com.zwdbj.server.pay.alipay;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.request.AlipayTradePrecreateRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.zwdbj.server.pay.alipay.model.AppPayInput;
import com.zwdbj.server.pay.alipay.model.AppPayResult;
import com.zwdbj.server.utility.model.ServiceStatusInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class AlipayService {
    private AlipayClient alipayClient = AlipaySDKClient.getInstance().getAlipayClient();
    private Logger logger = LoggerFactory.getLogger(AlipayService.class);
    /**
     * @param input
     * @return 阿里支付预下单
     */
    public ServiceStatusInfo<AppPayResult> AppPay(AppPayInput input) {
        try {
//            AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
//            String bizJson = JSON.toJSONString(input);
//            request.setBizContent(bizJson);
//            AlipayTradeAppPayResponse response = alipayClient.execute(request);
//            if (response.isSuccess()) {
//                AppPayResult result = new AppPayResult();
//                result.setOutTradeNo(response.getOutTradeNo());
//                result.setSellerId(response.getSellerId());
//                result.setTotalAmount(response.getTotalAmount());
//                result.setTradeNo(response.getTradeNo());
//                return new ServiceStatusInfo<>(0,"OK",result);
//            } else {
//                logger.info(response.getCode()+","+response.getMsg());
//                return new ServiceStatusInfo<>(1,"下单失败("+response.getCode()+")",null);
//            }
            AlipayTradePrecreateRequest request = new AlipayTradePrecreateRequest();
            request.setBizContent("{" +
                    "\"out_trade_no\":\"20150320010101001\"," +
                    "\"seller_id\":\"2088102146225135\"," +
                    "\"total_amount\":88.88," +
                    "\"discountable_amount\":8.88," +
                    "\"subject\":\"Iphone6 16G\"," +
                    "      \"goods_detail\":[{" +
                    "        \"goods_id\":\"apple-01\"," +
                    "\"goods_name\":\"ipad\"," +
                    "\"quantity\":1," +
                    "\"price\":2000," +
                    "\"goods_category\":\"34543238\"," +
                    "\"categories_tree\":\"124868003|126232002|126252004\"," +
                    "\"body\":\"特价手机\"," +
                    "\"show_url\":\"http://www.alipay.com/xxx.jpg\"" +
                    "        }]," +
                    "\"body\":\"Iphone6 16G\"," +
                    "\"operator_id\":\"yx_001\"," +
                    "\"store_id\":\"NJ_001\"," +
                    "\"disable_pay_channels\":\"pcredit,moneyFund,debitCardExpress\"," +
                    "\"enable_pay_channels\":\"pcredit,moneyFund,debitCardExpress\"," +
                    "\"terminal_id\":\"NJ_T_001\"," +
                    "\"extend_params\":{" +
                    "\"sys_service_provider_id\":\"2088511833207846\"," +
                    "\"industry_reflux_info\":\"{\\\\\\\"scene_code\\\\\\\":\\\\\\\"metro_tradeorder\\\\\\\",\\\\\\\"channel\\\\\\\":\\\\\\\"xxxx\\\\\\\",\\\\\\\"scene_data\\\\\\\":{\\\\\\\"asset_name\\\\\\\":\\\\\\\"ALIPAY\\\\\\\"}}\"," +
                    "\"card_type\":\"S0JP0000\"" +
                    "    }," +
                    "\"timeout_express\":\"90m\"," +
                    "\"settle_info\":{" +
                    "        \"settle_detail_infos\":[{" +
                    "          \"trans_in_type\":\"cardSerialNo\"," +
                    "\"trans_in\":\"A0001\"," +
                    "\"summary_dimension\":\"A0001\"," +
                    "\"settle_entity_id\":\"2088xxxxx;ST_0001\"," +
                    "\"settle_entity_type\":\"SecondMerchant、Store\"," +
                    "\"amount\":0.1" +
                    "          }]" +
                    "    }," +
                    "\"merchant_order_no\":\"20161008001\"," +
                    "\"business_params\":\"{\\\"data\\\":\\\"123\\\"}\"," +
                    "\"qr_code_timeout_express\":\"90m\"" +
                    "  }");
            AlipayTradePrecreateResponse response = alipayClient.execute(request);
            if(response.isSuccess()){
                System.out.println("调用成功");
            } else {
                System.out.println("调用失败");
            }
            return null;
        } catch ( AlipayApiException ex ) {
            logger.info(ex.getErrMsg());
            logger.info(ex.getErrCode());
            return new ServiceStatusInfo<>(1,ex.getErrMsg(),null);
        }
    }
}
