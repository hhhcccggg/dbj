package com.zwdbj.server.adminserver.controller.shop;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zwdbj.server.adminserver.service.shop.service.productOrder.model.*;
import com.zwdbj.server.adminserver.service.shop.service.productOrder.service.ProductOrderService;
import com.zwdbj.server.basemodel.model.ResponseData;
import com.zwdbj.server.basemodel.model.ResponseDataCode;
import com.zwdbj.server.basemodel.model.ResponsePageInfoData;
import com.zwdbj.server.basemodel.model.ServiceStatusInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/shop/order")
@Api(description = "后台订单相关")
public class ProductOrderController {
    @Autowired
    ProductOrderService productOrderService;

    @RequiresAuthentication
    @RequestMapping(value = "/search/{storeId}/orders", method = RequestMethod.POST)
    @ApiOperation(value = "根据店铺id查询订单")
    public ResponsePageInfoData<List<ProductOrderModel>> getStoreOrders(@PathVariable long storeId,
                                                                        @RequestBody ProductOrderInput input,
                                                                        @RequestParam(value = "pageNo", required = true, defaultValue = "1") int pageNo,
                                                                        @RequestParam(value = "rows", required = true, defaultValue = "30") int rows ){
        Page<ProductOrderModel> pageInfo = PageHelper.startPage(pageNo,rows);
        List<ProductOrderModel> orderModels = this.productOrderService.getStoreOrders(storeId,input);
        return new ResponsePageInfoData<>(ResponseDataCode.STATUS_NORMAL,"",orderModels,pageInfo.getTotal());
    }

    @RequiresAuthentication
    @RequestMapping(value = "/search/order/{orderId}", method = RequestMethod.GET)
    @ApiOperation(value = "根据订单id查询订单")
    public ResponseData<ProductOrderDetailModel> getOrderById(@PathVariable long orderId){
        ServiceStatusInfo<ProductOrderDetailModel> statusInfo = this.productOrderService.getOrderById(orderId);
        if (statusInfo.isSuccess()){
            return new ResponseData<>(ResponseDataCode.STATUS_NORMAL,"",statusInfo.getData());
        }
        return new ResponseData<>(ResponseDataCode.STATUS_ERROR,statusInfo.getMsg(),null);
    }
    @RequiresAuthentication
    @RequestMapping(value = "/search/order/orderNo", method = RequestMethod.POST)
    @ApiOperation(value = "根据订单号查询订单")
    public ResponseData<ProductOrderDetailModel> getOrderByOrderNo(@RequestBody GetOrderByOrderNoInput input){
        ServiceStatusInfo<ProductOrderDetailModel> statusInfo = this.productOrderService.getOrderByOrderNo(input.getOrderNo());
        if (statusInfo.isSuccess()){
            return new ResponseData<>(ResponseDataCode.STATUS_NORMAL,"",statusInfo.getData());
        }
        return new ResponseData<>(ResponseDataCode.STATUS_ERROR,statusInfo.getMsg(),null);
    }

    @RequiresAuthentication
    @RequestMapping(value = "/deliver/{orderId}", method = RequestMethod.POST)
    @ApiOperation(value = "店铺发货")
    public ResponseData<Integer> deliverOrder(@PathVariable long orderId,
                                              @RequestBody DeliverOrderInput input){
        ServiceStatusInfo<Integer> statusInfo = this.productOrderService.deliverOrder(orderId,input);
        if (statusInfo.isSuccess()){
            return new ResponseData<>(ResponseDataCode.STATUS_NORMAL,"",statusInfo.getData());
        }
        return new ResponseData<>(ResponseDataCode.STATUS_ERROR,statusInfo.getMsg(),null);
    }
    /*@RequestMapping(value = "/user/deliver/{orderId}/{userId}", method = RequestMethod.POST)
    @ApiOperation(value = "用户确认收货,orderId为订单id,userId为用户id")
    public ResponseData<Integer> deliverOrderByUser(@PathVariable long orderId,
                                                    @PathVariable long userId){
        ServiceStatusInfo<Integer> statusInfo = this.productOrderService.deliverOrderByUser(orderId,userId);
        if (statusInfo.isSuccess()){
            return new ResponseData<>(ResponseDataCode.STATUS_NORMAL,"",statusInfo.getData());
        }
        return new ResponseData<>(ResponseDataCode.STATUS_ERROR,statusInfo.getMsg(),null);
    }*/
    @RequiresAuthentication
    @RequestMapping(value = "/identifyingCode/{orderId}", method = RequestMethod.POST)
    @ApiOperation(value = "验证消费码")
    public ResponseData<Integer> identifyingCode(@PathVariable long orderId,
                                              @RequestBody IdentifyCodeInput input){
        ServiceStatusInfo<Integer> statusInfo = this.productOrderService.identifyingCode(orderId,input);
        if (statusInfo.isSuccess()){
            return new ResponseData<>(ResponseDataCode.STATUS_NORMAL,"",statusInfo.getData());
        }
        return new ResponseData<>(ResponseDataCode.STATUS_ERROR,statusInfo.getMsg(),null);
    }



}
