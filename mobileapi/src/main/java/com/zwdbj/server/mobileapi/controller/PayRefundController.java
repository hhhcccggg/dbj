package com.zwdbj.server.mobileapi.controller;

import com.zwdbj.server.mobileapi.service.pay.Refund.model.PayRefundInput;
import com.zwdbj.server.mobileapi.service.pay.Refund.service.PayRefundService;
import com.zwdbj.server.basemodel.model.ResponseData;
import com.zwdbj.server.basemodel.model.ResponseDataCode;
import com.zwdbj.server.basemodel.model.ServiceStatusInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Api("支付")
@RequestMapping("/api/pay/refund")
public class PayRefundController {
    @Autowired
    private PayRefundService payRefundService;

    @RequiresAuthentication
    @ApiOperation("申请退款")
    @RequestMapping(value = "/order/refund",method = RequestMethod.POST)
    public ResponseData<Object> refundOrder(@RequestBody PayRefundInput input){
        ServiceStatusInfo<Object> serviceStatusInfo = this.payRefundService.refundOrder(input);
        if (serviceStatusInfo.isSuccess()) {
            return new ResponseData<>(ResponseDataCode.STATUS_NORMAL,"OK",serviceStatusInfo.getData());
        }
        return new ResponseData<>(ResponseDataCode.STATUS_ERROR,serviceStatusInfo.getMsg(),null);
    }
    @RequiresAuthentication
    @ApiOperation("退款查询")
    @RequestMapping(value = "/order/orderQuery/{orderId}",method = RequestMethod.GET)
    public ResponseData<Object> refundOrderQuery(@PathVariable long orderId){
        ServiceStatusInfo<Object> serviceStatusInfo = this.payRefundService.refundOrderQuery(orderId);
        if (serviceStatusInfo.isSuccess()) {
            return new ResponseData<>(ResponseDataCode.STATUS_NORMAL,"OK",serviceStatusInfo.getData());
        }
        return new ResponseData<>(ResponseDataCode.STATUS_ERROR,serviceStatusInfo.getMsg(),null);
    }
}
