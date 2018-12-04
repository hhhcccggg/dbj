package com.zwdbj.server.mobileapi.controller;

import com.zwdbj.server.pay.wechat.wechatpay.model.OrderQueryDto;
import com.zwdbj.server.pay.wechat.wechatpay.model.OrderQueryInput;
import com.zwdbj.server.pay.wechat.wechatpay.model.UnifiedOrderDto;
import com.zwdbj.server.pay.wechat.wechatpay.model.UnifiedOrderInput;
import com.zwdbj.server.pay.wechat.wechatpay.service.WechatPayService;
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
@RequestMapping("/api/pay/weChat")
public class PayWeChatController {
    @Autowired
    private WechatPayService wechatPayService;

    @RequiresAuthentication
    @RequestMapping(value = "/unifiedOrder",method = RequestMethod.POST)
    @ApiOperation("微信支付统一下单")
    public ResponseData<UnifiedOrderDto> unifiedOrder(@RequestBody UnifiedOrderInput input) {
        ServiceStatusInfo<UnifiedOrderDto> serviceStatusInfo = this.wechatPayService.unifiedOrder(input);
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
        ServiceStatusInfo<OrderQueryDto> serviceStatusInfo = this.wechatPayService.orderQuery(input);
        if(serviceStatusInfo.isSuccess()) {
            return new ResponseData<>(ResponseDataCode.STATUS_NORMAL, "OK", serviceStatusInfo.getData());
        } else {
            return new ResponseData<>(ResponseDataCode.STATUS_ERROR,serviceStatusInfo.getMsg(),null);
        }
    }
}
