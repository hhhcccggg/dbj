package com.zwdbj.server.mobileapi.controller.wxMini;

import com.zwdbj.server.mobileapi.service.wxMiniProgram.productOrder.model.AddOrderInput;
import com.zwdbj.server.mobileapi.service.wxMiniProgram.productOrder.service.ProductOrderService;
import com.zwdbj.server.utility.model.ResponseData;
import com.zwdbj.server.utility.model.ResponseDataCode;
import com.zwdbj.server.utility.model.ServiceStatusInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/wx/mini")
@Api(description = "小程序相关")
public class WXMiniOrderController {
    @Autowired
    ProductOrderService productOrderService;

    @RequestMapping(value = "/createOrder",method = RequestMethod.POST)
    @ApiOperation(value = "创建订单")
    @RequiresAuthentication
    public ResponseData<Integer> createOrder(@RequestBody @Valid AddOrderInput input){
        ServiceStatusInfo<Integer> statusInfo = this.productOrderService.createOrder(input);
        if (statusInfo.isSuccess()) {
            return new ResponseData<>(ResponseDataCode.STATUS_NORMAL,statusInfo.getMsg(),statusInfo.getData());
        }
        return new ResponseData<>(ResponseDataCode.STATUS_ERROR,statusInfo.getMsg(),null);

    }

}
