package com.zwdbj.server.adminserver.controller.shop;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zwdbj.server.adminserver.identity.RoleIdentity;
import com.zwdbj.server.adminserver.service.shop.service.shoppingCart.model.ProductCartAddInput;
import com.zwdbj.server.adminserver.service.shop.service.shoppingCart.model.ProductCartModel;
import com.zwdbj.server.adminserver.service.shop.service.shoppingCart.model.ProductCartModifyInput;
import com.zwdbj.server.adminserver.service.shop.service.shoppingCart.service.ShoppingCartService;
import com.zwdbj.server.basemodel.model.ResponseData;
import com.zwdbj.server.basemodel.model.ResponseDataCode;
import com.zwdbj.server.basemodel.model.ResponsePageInfoData;
import com.zwdbj.server.basemodel.model.ServiceStatusInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/shop/shoppingCart/dbj")
@Api(description = "购物车相关")
public class ShoppingCartController {
    @Autowired
    ShoppingCartService shoppingCartServiceImpl;
    @RequiresAuthentication
    @RequestMapping(value = "/select", method = RequestMethod.GET)
    @ApiOperation(value = "查询所有购物车")
    @RequiresRoles(value = {RoleIdentity.ADMIN_ROLE, RoleIdentity.MARKET_ROLE}, logical = Logical.OR)
    public ResponsePageInfoData<List<ProductCartModel>> findAllBusinessSellers(@RequestParam(value = "pageNo", required = true, defaultValue = "1") int pageNo,
                                                                               @RequestParam(value = "rows", required = true, defaultValue = "30") int rows) {
        Page<ProductCartModel> pageInfo = PageHelper.startPage(pageNo,rows);
        List<ProductCartModel> shoppingCarts = this.shoppingCartServiceImpl.findAllShoppingCarts();
        return new ResponsePageInfoData(ResponseDataCode.STATUS_NORMAL, "", shoppingCarts, pageInfo.getTotal());

    }

    @RequiresAuthentication
    @RequiresRoles(value = {RoleIdentity.ADMIN_ROLE, RoleIdentity.MARKET_ROLE}, logical = Logical.OR)
    @RequestMapping(value = "/select/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "查询购物车详情")
    public ResponseData<ProductCartModel> getBusinessSellerById(@PathVariable long id) {
        ServiceStatusInfo<ProductCartModel> productCartModel = this.shoppingCartServiceImpl.getShoppingCartById(id);
        if (productCartModel.isSuccess()) {
            return new ResponseData<>(ResponseDataCode.STATUS_NORMAL,productCartModel.getMsg(),productCartModel.getData());
        }
        return new ResponseData<>(ResponseDataCode.STATUS_ERROR,productCartModel.getMsg(),null);
    }
    @RequiresAuthentication
    @RequiresRoles(value = {RoleIdentity.ADMIN_ROLE, RoleIdentity.MARKET_ROLE}, logical = Logical.OR)
    @RequestMapping(value = "/modify/{id}", method = RequestMethod.POST)
    @ApiOperation(value = "修改购物车信息")
    public ResponseData<Integer> modifyShoppingCart(@PathVariable long id,
                                                       @RequestBody ProductCartModifyInput input) {
        ServiceStatusInfo <Integer> statusInfo = this.shoppingCartServiceImpl.modifyShoppingCart(id,input);
        if (statusInfo.isSuccess()) {
            return new ResponseData<>(ResponseDataCode.STATUS_NORMAL,statusInfo.getMsg(),null);
        }
        return new ResponseData<>(ResponseDataCode.STATUS_ERROR,statusInfo.getMsg(),null);
    }

    @RequiresAuthentication
    @RequiresRoles(value = {RoleIdentity.ADMIN_ROLE, RoleIdentity.MARKET_ROLE}, logical = Logical.OR)
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ApiOperation(value = "添加新的购物车")
    public ResponseData<Integer> addShoppingCart(@RequestBody ProductCartAddInput input) {
        ServiceStatusInfo <Integer> statusInfo = this.shoppingCartServiceImpl.addShoppingCart(input);
        if (statusInfo.isSuccess()) {
            return new ResponseData<>(ResponseDataCode.STATUS_NORMAL,statusInfo.getMsg(),null);
        }
        return new ResponseData<>(ResponseDataCode.STATUS_ERROR,statusInfo.getMsg(),null);
    }

    @RequiresAuthentication
    @RequiresRoles(value = {RoleIdentity.ADMIN_ROLE, RoleIdentity.MARKET_ROLE}, logical = Logical.OR)
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "删除购物车")
    public ResponseData<Integer> deleteBusinessSellers(@PathVariable(value = "id") long id) {
        ServiceStatusInfo <Integer> statusInfo = this.shoppingCartServiceImpl.notRealDeleteShoppingCart(id);
        if (statusInfo.isSuccess()) {
            return new ResponseData<>(ResponseDataCode.STATUS_NORMAL,statusInfo.getMsg(),null);
        }
        return new ResponseData<>(ResponseDataCode.STATUS_ERROR,statusInfo.getMsg(),null);
    }

}
