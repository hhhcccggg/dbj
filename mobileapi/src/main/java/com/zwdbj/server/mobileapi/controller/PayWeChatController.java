package com.zwdbj.server.mobileapi.controller;

import com.zwdbj.server.mobileapi.service.pay.wechat.model.ChargeCoinWXResult;
import com.zwdbj.server.mobileapi.service.pay.model.ChargeCoinInput;
import com.zwdbj.server.mobileapi.service.pay.wechat.service.WXPayService;
import com.zwdbj.server.mobileapi.service.shop.order.model.PayOrderInput;
import com.zwdbj.server.pay.wechat.wechatpay.model.*;
import com.zwdbj.server.utility.common.shiro.JWTUtil;
import com.zwdbj.server.basemodel.model.ResponseData;
import com.zwdbj.server.basemodel.model.ResponseDataCode;
import com.zwdbj.server.basemodel.model.ServiceStatusInfo;
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
    private WXPayService weChatService;

    @RequiresAuthentication
    @RequestMapping(value = "/chargeCoins",method = RequestMethod.POST)
    @ApiOperation("微信充值小饼干")
    public ResponseData<ChargeCoinWXResult> chargeCoins(@RequestBody ChargeCoinInput input) {
        ServiceStatusInfo<ChargeCoinWXResult> serviceStatusInfo = this.weChatService.chargeCoins(input,JWTUtil.getCurrentId());
        if(serviceStatusInfo.isSuccess()) {
            return new ResponseData<>(ResponseDataCode.STATUS_NORMAL, "OK", serviceStatusInfo.getData());
        } else {
            return new ResponseData<>(ResponseDataCode.STATUS_ERROR,serviceStatusInfo.getMsg(),null);
        }
    }
    @RequiresAuthentication
    @RequestMapping(value = "/order/pay",method = RequestMethod.POST)
    @ApiOperation("订单付款")
    public ResponseData<ChargeCoinWXResult> payOrder(@RequestBody PayOrderInput input) {
        ServiceStatusInfo<ChargeCoinWXResult> serviceStatusInfo = this.weChatService.payOrder(input,JWTUtil.getCurrentId());
        if(serviceStatusInfo.isSuccess()) {
            return new ResponseData<>(ResponseDataCode.STATUS_NORMAL, "OK", serviceStatusInfo.getData());
        } else {
            return new ResponseData<>(ResponseDataCode.STATUS_ERROR,serviceStatusInfo.getMsg(),null);
        }
    }
    @RequiresAuthentication
    @RequestMapping(value = "/orderQuery",method = RequestMethod.POST)
    @ApiOperation("查询小饼干订单")
    public ResponseData<OrderPayResultDto> orderQuery(@RequestBody OrderQueryInput input) {
        ServiceStatusInfo<OrderPayResultDto> serviceStatusInfo = this.weChatService.orderQuery(input,1);
        if(serviceStatusInfo.isSuccess()) {
            return new ResponseData<>(ResponseDataCode.STATUS_NORMAL, "OK", serviceStatusInfo.getData());
        } else {
            return new ResponseData<>(ResponseDataCode.STATUS_ERROR,serviceStatusInfo.getMsg(),null);
        }
    }
    @RequiresAuthentication
    @RequestMapping(value = "/payOrder/orderQuery",method = RequestMethod.POST)
    @ApiOperation("查询付款订单")
    public ResponseData<OrderPayResultDto> payOrderQuery(@RequestBody OrderQueryInput input) {
        ServiceStatusInfo<OrderPayResultDto> serviceStatusInfo = this.weChatService.orderQuery(input,2);
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
        ServiceStatusInfo<String> stringServiceStatusInfo = this.weChatService.responseWeChatPayResult(wholeStr,1);
        OutputStream outputStream = response.getOutputStream();
        if (stringServiceStatusInfo.isSuccess()) {
            response.setStatus(200);
        } else {
            response.setStatus(500);
        }
        outputStream.write(stringServiceStatusInfo.getData().getBytes("UTF-8"));
    }
    @RequestMapping(value = "/order/payNotify",method = RequestMethod.POST)
    public void orderPayNotify(HttpServletResponse response, HttpServletRequest request) throws IOException {
        BufferedReader reader = request.getReader();
        String str, wholeStr = "";
        while((str = reader.readLine()) != null){
            wholeStr += str;
        }
        //校验响应
        ServiceStatusInfo<String> stringServiceStatusInfo = this.weChatService.responseWeChatPayResult(wholeStr,2);
        OutputStream outputStream = response.getOutputStream();
        if (stringServiceStatusInfo.isSuccess()) {
            response.setStatus(200);
        } else {
            response.setStatus(500);
        }
        outputStream.write(stringServiceStatusInfo.getData().getBytes("UTF-8"));
    }
    @RequestMapping(value = "/refund/payNotify",method = RequestMethod.POST)
    public void refundNotify(HttpServletResponse response, HttpServletRequest request) throws IOException {
        BufferedReader reader = request.getReader();
        String str, wholeStr = "";
        while((str = reader.readLine()) != null){
            wholeStr += str;
        }
        //校验响应
        ServiceStatusInfo<String> stringServiceStatusInfo = this.weChatService.responseWeChatRefundResult(wholeStr);
        OutputStream outputStream = response.getOutputStream();
        if (stringServiceStatusInfo.isSuccess()) {
            response.setStatus(200);
        } else {
            response.setStatus(500);
        }
        outputStream.write(stringServiceStatusInfo.getData().getBytes("UTF-8"));
    }

}
