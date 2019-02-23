package com.zwdbj.server.mobileapi.controller.shop;


import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zwdbj.server.mobileapi.service.shop.order.model.AddNewOrderInput;
import com.zwdbj.server.mobileapi.service.shop.order.model.ProductOrderDetailModel;
import com.zwdbj.server.mobileapi.service.shop.order.model.ProductOrderModel;
import com.zwdbj.server.mobileapi.service.shop.order.service.OrderService;
import com.zwdbj.server.mobileapi.service.wxMiniProgram.productOrder.model.AddOrderInput;
import com.zwdbj.server.pay.settlement.protocol.Coupon;
import com.zwdbj.server.pay.settlement.protocol.SettlementResult;
import com.zwdbj.server.utility.common.shiro.JWTUtil;
import com.zwdbj.server.utility.model.ResponseData;
import com.zwdbj.server.utility.model.ResponseDataCode;
import com.zwdbj.server.utility.model.ResponsePageInfoData;
import com.zwdbj.server.utility.model.ServiceStatusInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/shop/order")
@Api(description = "移动端订单相关")
public class OrderController {
    @Autowired
    OrderService orderService;

    @RequiresAuthentication
    @ApiOperation(value = "获取我的订单,status交易状态:" +
            "0，（全部）" +
            "1，STATE_WAIT_BUYER_PAY（待付款，等待买家付款）" +
            "2，STATE_UNUSED (待使用，虚拟商品有效) " +
            "comment:如要获取待评价的请传1,其他情况请传0")
    @RequestMapping(value = "/getOrders/{status}/{comment}", method = RequestMethod.GET)
    public ResponsePageInfoData<List<ProductOrderModel>> getMyOrders(@PathVariable int status,
                                                                     @PathVariable int comment,
                                                                     @RequestParam(value = "pageNo", required = true, defaultValue = "1") int pageNo,
                                                                     @RequestParam(value = "rows", required = true, defaultValue = "30") int rows){
        Page<ProductOrderModel> pageInfo = PageHelper.startPage(pageNo,rows);
        List<ProductOrderModel> orderModels = this.orderService.getMyOrders(status,comment);
        return new ResponsePageInfoData<>(ResponseDataCode.STATUS_NORMAL,"",orderModels,pageInfo.getTotal());
    }

    @RequiresAuthentication
    @RequestMapping(value = "/search/order/{orderId}", method = RequestMethod.GET)
    @ApiOperation(value = "根据订单id查询订单")
    public ResponseData<ProductOrderDetailModel> getOrderById(@PathVariable long orderId){
        ServiceStatusInfo<ProductOrderDetailModel> statusInfo = this.orderService.getOrderById(orderId);
        if (statusInfo.isSuccess()){
            return new ResponseData<>(ResponseDataCode.STATUS_NORMAL,"",statusInfo.getData());
        }
        return new ResponseData<>(ResponseDataCode.STATUS_ERROR,statusInfo.getMsg(),null);
    }

    @RequestMapping(value = "/createOrder",method = RequestMethod.POST)
    @ApiOperation(value = "创建订单")
    @RequiresAuthentication
    public ResponseData<Long> createOrder(@RequestBody @Valid AddNewOrderInput input){
        ServiceStatusInfo<Long> statusInfo = this.orderService.createOrder(input);
        if (statusInfo.isSuccess()) {
            return new ResponseData<>(ResponseDataCode.STATUS_NORMAL,statusInfo.getMsg(),statusInfo.getData());
        }
        return new ResponseData<>(ResponseDataCode.STATUS_ERROR,statusInfo.getMsg(),null);

    }
    @RequestMapping(value = "/takeOver/goods",method = RequestMethod.GET)
    @ApiOperation(value = "确认收货,id为订单id")
    @RequiresAuthentication
    public ResponseData<Integer> takeOverGoods(@RequestParam long id){
        ServiceStatusInfo<Integer> statusInfo = this.orderService.takeOverGoods(id, JWTUtil.getCurrentId());
        if (statusInfo.isSuccess()) {
            return new ResponseData<>(ResponseDataCode.STATUS_NORMAL,statusInfo.getMsg(),statusInfo.getData());
        }
        return new ResponseData<>(ResponseDataCode.STATUS_ERROR,statusInfo.getMsg(),null);

    }
    @RequestMapping(value = "/settlement/order",method = RequestMethod.POST)
    @ApiOperation(value = "订单结算调用")
    @RequiresAuthentication
    public ResponseData<SettlementResult> settlementOrder(@RequestParam long coins,
                                                 @RequestParam int amount,
                                                 @RequestBody Coupon coupon){
        ServiceStatusInfo<SettlementResult> statusInfo = this.orderService.settlementOrder(amount,coins,coupon);
        if (statusInfo.isSuccess()) {
            return new ResponseData<>(ResponseDataCode.STATUS_NORMAL,statusInfo.getMsg(),statusInfo.getData());
        }
        return new ResponseData<>(ResponseDataCode.STATUS_ERROR,statusInfo.getMsg(),null);

    }

}
