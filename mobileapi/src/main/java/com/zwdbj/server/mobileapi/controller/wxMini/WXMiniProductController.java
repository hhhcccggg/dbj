package com.zwdbj.server.mobileapi.controller.wxMini;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zwdbj.server.mobileapi.service.wxMiniProgram.product.model.ProductOut;
import com.zwdbj.server.mobileapi.service.wxMiniProgram.product.service.ProductService;
import com.zwdbj.server.utility.model.ResponseData;
import com.zwdbj.server.utility.model.ResponseDataCode;
import com.zwdbj.server.utility.model.ResponsePageInfoData;
import com.zwdbj.server.utility.model.ServiceStatusInfo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/wx/mini/product")
public class WXMiniProductController {

    @Autowired
    ProductService productServiceImpl;

    @GetMapping(value = "findProduct")
    @ApiOperation(value = "兑换商城列表")
    public ResponsePageInfoData<ProductOut> findProduct(@RequestParam(value = "pageNo", required = true, defaultValue = "1") int pageNo,
                                                        @RequestParam(value = "rows", required = true, defaultValue = "10") int rows){
        PageHelper.startPage(pageNo,rows);
        ServiceStatusInfo<List<ProductOut>> serviceStatusInfo =  this.productServiceImpl.selectWXXCXShopProduct();
        if( !serviceStatusInfo.isSuccess() ){
            return new ResponsePageInfoData(ResponseDataCode.STATUS_ERROR, serviceStatusInfo.getMsg(), null, 0);
        }
        PageInfo<ProductOut> pageInfo = new PageInfo(serviceStatusInfo.getData());
        return new ResponsePageInfoData(ResponseDataCode.STATUS_NORMAL, "", pageInfo.getList(), pageInfo.getTotal());
    }

    @GetMapping(value = "findById")
    @ApiOperation(value = "查看单个商品")
    public ResponseData<Map<String,Object>> findById(@RequestParam(value = "id" , required = true) long id){
        ServiceStatusInfo<Map<String,Object>> serviceStatusInfo = this.productServiceImpl.selectWXXCXById(id);
        if(serviceStatusInfo.isSuccess())
            return new ResponseData<>(ResponseDataCode.STATUS_NORMAL,"",serviceStatusInfo.getData());
        return new ResponseData<>(ResponseDataCode.STATUS_ERROR,serviceStatusInfo.getMsg(),null);
    }
}
