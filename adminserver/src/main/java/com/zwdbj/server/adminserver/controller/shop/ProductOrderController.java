package com.zwdbj.server.adminserver.controller.shop;

import com.zwdbj.server.adminserver.service.shop.service.productOrder.service.ProductOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/shop/order")
@Api(description = "后台订单相关")
public class ProductOrderController {
    @Autowired
    ProductOrderService productOrderService;

    /*@RequestMapping(value = "/search", method = RequestMethod.POST)
    @ApiOperation(value = "查询商户")*/
}
