package com.zwdbj.server.mobileapi.controller.wxMini;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zwdbj.server.mobileapi.service.wxMiniProgram.product.model.ProductInput;
import com.zwdbj.server.mobileapi.service.wxMiniProgram.product.model.ProductOut;
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
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/wx/mini/product")
@Api(description = "兑换商城")
public class WXMiniProductController {

    @Autowired
    ProductService productServiceImpl;

    @Autowired
    ProductOrderService productOrderService;

    @GetMapping(value = "findByProduct")
    @ApiOperation(value = "兑换商城列表")
    public ResponsePageInfoData<ProductOut> findByProduct(@RequestParam(value = "pageNo", required = true, defaultValue = "1") int pageNo,
                                                          @RequestParam(value = "rows", required = true, defaultValue = "10") int rows,
                                                          ProductInput productInput){
        PageHelper.startPage(pageNo,rows);
        ServiceStatusInfo<List<ProductOut>> serviceStatusInfo =  this.productServiceImpl.selectWXXCXShopProduct(productInput);
        if( !serviceStatusInfo.isSuccess() ){
            return new ResponsePageInfoData(ResponseDataCode.STATUS_ERROR, serviceStatusInfo.getMsg(), null, 0);
        }
        PageInfo<ProductOut> pageInfo = new PageInfo(serviceStatusInfo.getData());
        return new ResponsePageInfoData(ResponseDataCode.STATUS_NORMAL, "", pageInfo.getList(), pageInfo.getTotal());
    }

    @GetMapping(value = "find/{id}")
    @ApiOperation(value = "查看单个商品")
    public ResponseData<Map<String,Object>> findById(@PathVariable long id){
        ServiceStatusInfo<Map<String,Object>> serviceStatusInfo = this.productServiceImpl.selectWXXCXById(id);
        if(serviceStatusInfo.isSuccess())
            return new ResponseData<>(ResponseDataCode.STATUS_NORMAL,"",serviceStatusInfo.getData());
        return new ResponseData<>(ResponseDataCode.STATUS_ERROR,serviceStatusInfo.getMsg(),null);
    }

    @GetMapping(value = "myByProduct")
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
}
