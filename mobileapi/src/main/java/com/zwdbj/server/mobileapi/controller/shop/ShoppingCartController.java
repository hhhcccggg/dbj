package com.zwdbj.server.mobileapi.controller.shop;

import com.zwdbj.server.mobileapi.service.shop.shoppingcart.model.ProductInfo;
import com.zwdbj.server.mobileapi.service.shop.shoppingcart.service.ShoppingCartService;
import com.zwdbj.server.utility.model.ResponseData;
import com.zwdbj.server.utility.model.ServiceStatusInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RequestMapping("/api/shoppingcart/dbj")
@RestController
@Api(description = "购物车模块")
public class ShoppingCartController {

    @Autowired
    private ShoppingCartService shoppingCartServiceImpl;


    @ApiOperation(value = "添加购物车")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseData addShoppingCart(HttpServletRequest request, HttpServletResponse response, @RequestBody ProductInfo productInfo) {
        ServiceStatusInfo<Long> statusInfo = this.shoppingCartServiceImpl.add(request, response, productInfo);
        if (statusInfo.isSuccess()) {
            return new ResponseData(0, "", statusInfo.getData());
        }
        return new ResponseData(1, statusInfo.getMsg(), null);

    }

    @ApiOperation(value = "查看购物车")
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public ResponseData getShoppingCart(HttpServletRequest request, HttpServletResponse response) {
        ServiceStatusInfo<List<ProductInfo>> statusInfo = this.shoppingCartServiceImpl.getShoppingCart(request, response);
        if (statusInfo.isSuccess()) {
            return new ResponseData(0, "", statusInfo.getData());
        }
        return new ResponseData(1, statusInfo.getMsg(), null);

    }

    @ApiOperation(value = "修改购物车")
    @RequestMapping(value = "/modify", method = RequestMethod.POST)
    public ResponseData<List<ProductInfo>> modifyShoppingCart(HttpServletRequest request, HttpServletResponse response, @RequestBody ProductInfo productInfo) {
        ServiceStatusInfo<List<ProductInfo>> statusInfo = this.shoppingCartServiceImpl.modifyShoppingCart(request, response, productInfo);
        if (statusInfo.isSuccess()) {
            return new ResponseData(0, "", statusInfo.getData());
        }
        return new ResponseData(1, statusInfo.getMsg(), null);

    }

    @ApiOperation(value = "删除购物车")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ResponseData deleteShoppingCart(HttpServletRequest request, HttpServletResponse response, @RequestBody ProductInfo productInfo) {
        ServiceStatusInfo<List<ProductInfo>> statusInfo = this.shoppingCartServiceImpl.deleteShoppingCart(request, response, productInfo);
        if (statusInfo.isSuccess()) {
            return new ResponseData(0, "", statusInfo.getData());
        }
        return new ResponseData(1, statusInfo.getMsg(), null);

    }
}
