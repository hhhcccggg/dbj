package com.zwdbj.server.mobileapi.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zwdbj.server.mobileapi.config.MainKeyType;
import com.zwdbj.server.mobileapi.service.wxMiniProgram.product.model.ProductInput;
import com.zwdbj.server.mobileapi.service.wxMiniProgram.product.model.ProductMainDto;
import com.zwdbj.server.mobileapi.service.wxMiniProgram.product.model.ProductOut;
import com.zwdbj.server.mobileapi.service.wxMiniProgram.product.model.ProductlShow;
import com.zwdbj.server.mobileapi.service.wxMiniProgram.product.service.ProductService;
import com.zwdbj.server.mobileapi.service.wxMiniProgram.productOrder.model.OrderOut;
import com.zwdbj.server.mobileapi.service.wxMiniProgram.productOrder.service.ProductOrderService;
import com.zwdbj.server.utility.model.ResponseData;
import com.zwdbj.server.utility.model.ResponseDataCode;
import com.zwdbj.server.utility.model.ResponsePageInfoData;
import com.zwdbj.server.utility.model.ServiceStatusInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/shop/product")
@Api(description = "兑换商城")
public class ProductController {

    @Autowired
    ProductService productServiceImpl;

    @Autowired
    ProductOrderService productOrderService;

    @Autowired
    private RedisTemplate redisTemplate;

    @GetMapping(value = "findByProduct")
    @ApiOperation(value = "兑换商城列表")
    public ResponsePageInfoData<List<ProductOut>> findByProduct(@RequestParam(value = "pageNo", required = true, defaultValue = "1") int pageNo,
                                                          @RequestParam(value = "rows", required = true, defaultValue = "10") int rows,
                                                          @Valid ProductInput productInput){
        PageHelper.startPage(pageNo,rows);
        ServiceStatusInfo<List<ProductOut>> serviceStatusInfo =  this.productServiceImpl.selectShopProduct(productInput);
        if( !serviceStatusInfo.isSuccess() ){
            return new ResponsePageInfoData(ResponseDataCode.STATUS_ERROR, serviceStatusInfo.getMsg(), null, 0);
        }
        PageInfo<ProductOut> pageInfo = new PageInfo(serviceStatusInfo.getData());
        return new ResponsePageInfoData(ResponseDataCode.STATUS_NORMAL, "", pageInfo.getList(), pageInfo.getTotal());
    }

    @GetMapping(value = "find/{storeId}/{id}")
    @ApiOperation(value = "查看单个商品")
    public ResponseData<ProductlShow> findById(@PathVariable long id,@PathVariable long storeId){
        ServiceStatusInfo<ProductlShow> serviceStatusInfo = this.productServiceImpl.selectByIdByStoreId(id,storeId);
        if(serviceStatusInfo.isSuccess())
            return new ResponseData<>(ResponseDataCode.STATUS_NORMAL,"",serviceStatusInfo.getData());
        return new ResponseData<>(ResponseDataCode.STATUS_ERROR,serviceStatusInfo.getMsg(),null);
    }

    @GetMapping(value = "findByMyOrder")
    @ApiOperation(value = "我的兑换")
    @RequiresAuthentication
    public ResponsePageInfoData<OrderOut> findByMyOrder(@RequestParam(value = "pageNo", required = true, defaultValue = "1") int pageNo,
                                                        @RequestParam(value = "rows", required = true, defaultValue = "10") int rows){
        PageHelper.startPage(pageNo,rows);
        ServiceStatusInfo<List<OrderOut>> serviceStatusInfo =  this.productOrderService.findByMyOrder();
        if( !serviceStatusInfo.isSuccess() ){
            return new ResponsePageInfoData(ResponseDataCode.STATUS_ERROR, serviceStatusInfo.getMsg(), null, 0);
        }
        PageInfo<ProductOut> pageInfo = new PageInfo(serviceStatusInfo.getData());
        return new ResponsePageInfoData(ResponseDataCode.STATUS_NORMAL, "", pageInfo.getList(), pageInfo.getTotal());

    }

    @GetMapping(value = "mainByProduct")
    @ApiOperation(value = "主页的兑换商城")
    public ResponseData<List<ProductMainDto>> mainByProduct(){
        ServiceStatusInfo<List<ProductMainDto>> serviceStatusInfo = this.productServiceImpl.mainProduct();
        if(serviceStatusInfo.isSuccess())
            return new ResponseData<>(ResponseDataCode.STATUS_NORMAL,"",serviceStatusInfo.getData());
        return new ResponseData<>(ResponseDataCode.STATUS_ERROR,serviceStatusInfo.getMsg(),null);
    }
    @DeleteMapping(value = "del")
    @ApiOperation(value = "删除首页缓存数据")
    public void deleteReidsKey(){
        redisTemplate.delete(MainKeyType.MAINPRODUCT);
        redisTemplate.delete(MainKeyType.MAINCATEGORY);
    }
}
