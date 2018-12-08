package com.zwdbj.server.mobileapi.controller;

import com.zwdbj.server.mobileapi.service.pay.wechat.model.ChargeCoinDto;
import com.zwdbj.server.mobileapi.service.pay.wechat.model.ChargeCoinInput;
import com.zwdbj.server.mobileapi.service.pay.wechat.service.WeChatService;
import com.zwdbj.server.pay.wechat.wechatpay.model.OrderQueryDto;
import com.zwdbj.server.pay.wechat.wechatpay.model.OrderQueryInput;
import com.zwdbj.server.pay.wechat.wechatpay.model.UnifiedOrderDto;
import com.zwdbj.server.pay.wechat.wechatpay.model.UnifiedOrderInput;
import com.zwdbj.server.pay.wechat.wechatpay.service.WechatPayService;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStream;

@RestController
@Api("支付")
@RequestMapping("/api/pay/weChat")
public class PayWeChatController {
    @Autowired
    private WechatPayService wechatPayService;
    @Autowired
    private WeChatService weChatService;

    @RequiresAuthentication
    @RequestMapping(value = "/chargeCoins",method = RequestMethod.POST)
    @ApiOperation("微信充值金币")
    public ResponseData<ChargeCoinDto> chargeCoins(@RequestBody ChargeCoinInput input) {
        ServiceStatusInfo<ChargeCoinDto> serviceStatusInfo = this.weChatService.chargeCoins(input,JWTUtil.getCurrentId());
        if(serviceStatusInfo.isSuccess()) {
            return new ResponseData<>(ResponseDataCode.STATUS_NORMAL, "OK", serviceStatusInfo.getData());
        } else {
            return new ResponseData<>(ResponseDataCode.STATUS_ERROR,serviceStatusInfo.getMsg(),null);
        }
    }
    @RequiresAuthentication
    @RequestMapping(value = "/orderQuery",method = RequestMethod.POST)
    @ApiOperation("查询订单")
    public ResponseData<OrderQueryDto> orderQuery(OrderQueryInput input) {
        //TODO 刷新用户金币数据
        ServiceStatusInfo<OrderQueryDto> serviceStatusInfo = this.wechatPayService.orderQuery(input);
        if(serviceStatusInfo.isSuccess()) {
            return new ResponseData<>(ResponseDataCode.STATUS_NORMAL, "OK", serviceStatusInfo.getData());
        } else {
            return new ResponseData<>(ResponseDataCode.STATUS_ERROR,serviceStatusInfo.getMsg(),null);
        }
    }
    @RequestMapping(value = "/payNotify",method = RequestMethod.POST)
    public void payNotify(HttpServletResponse response, HttpServletRequest request) throws IOException {
        BufferedReader reader = request.getReader();
        String str, wholeStr = "";
        while((str = reader.readLine()) != null){
            wholeStr += str;
        }
        //校验响应
        ServiceStatusInfo<String> stringServiceStatusInfo = this.wechatPayService.responseWeChatPayResult(wholeStr);
        OutputStream outputStream = response.getOutputStream();
        if (stringServiceStatusInfo.isSuccess()) {
            response.setStatus(200);
        } else {
            response.setStatus(500);
        }
        outputStream.write(stringServiceStatusInfo.getMsg().getBytes("UTF-8"));
    }

}
