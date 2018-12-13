package com.zwdbj.server.mobileapi.controller;

import com.zwdbj.server.mobileapi.service.pay.alipay.model.ChargeCoinAlipayResult;
import com.zwdbj.server.mobileapi.service.pay.alipay.service.AlipayBizService;
import com.zwdbj.server.mobileapi.service.pay.model.ChargeCoinInput;
import com.zwdbj.server.pay.alipay.AlipayService;
import com.zwdbj.server.pay.alipay.model.AppPayResult;
import com.zwdbj.server.utility.common.shiro.JWTUtil;
import com.zwdbj.server.utility.model.ResponseData;
import com.zwdbj.server.utility.model.ResponseDataCode;
import com.zwdbj.server.utility.model.ServiceStatusInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api("支付")
@RequestMapping("/api/pay/alipay")
public class PayAlipayController {
    @Autowired
    private AlipayBizService alipayBizService;

    @RequiresAuthentication
    @ApiOperation("生成充值订单")
    @RequestMapping(value = "/chargeCoins",method = RequestMethod.POST)
    public ResponseData<ChargeCoinAlipayResult> chargeCoins(@RequestBody ChargeCoinInput input) {
        ServiceStatusInfo<ChargeCoinAlipayResult> serviceStatusInfo = this.alipayBizService.chargeCoins(input,JWTUtil.getCurrentId());
        if (serviceStatusInfo.isSuccess()) {
            return new ResponseData<>(ResponseDataCode.STATUS_NORMAL,"OK",serviceStatusInfo.getData());
        }
        return new ResponseData<>(ResponseDataCode.STATUS_ERROR,serviceStatusInfo.getMsg(),null);
    }
}
