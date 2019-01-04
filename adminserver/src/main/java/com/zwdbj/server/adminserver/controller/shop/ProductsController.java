package com.zwdbj.server.adminserver.controller.shop;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zwdbj.server.shop_admin_service.service.products.model.Products;
import com.zwdbj.server.shop_admin_service.service.products.model.SearchProducts;
import com.zwdbj.server.shop_admin_service.service.products.service.ProductService;
import com.zwdbj.server.utility.model.ResponseData;
import com.zwdbj.server.utility.model.ResponseDataCode;
import com.zwdbj.server.utility.model.ResponsePageInfoData;
import com.zwdbj.server.utility.model.ServiceStatusInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/shop/products/dbj")
@Api(description = "商品相关")
public class ProductsController {
    @Autowired
    private ProductService productServiceImpl;

    @RequestMapping(value = "/onSale", method = RequestMethod.GET)
    @ApiOperation(value = "查询销售中商品")
    public ResponsePageInfoData<List<Products>> findAllProducts(@RequestParam(value = "pageNo", required = true, defaultValue = "1") int pageNo,
                                                                @RequestParam(value = "rows", required = true, defaultValue = "30") int rows) {
        PageHelper.startPage(pageNo, rows);
        List<Products> productsList = this.productServiceImpl.selectAll().getData();
        PageInfo<Products> pageInfo = new PageInfo(productsList);
        return new ResponsePageInfoData(ResponseDataCode.STATUS_NORMAL, "", productsList, pageInfo.getTotal());

    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ApiOperation(value = "创建商品")
    public ResponseData<Long> createProducts(@RequestBody Products products ,
                                             @RequestParam(value = "originalPrice",required = true) long originalPrice,
                                             @RequestParam(value = "promotionPrice",required = true) long promotionPrice,
                                             @RequestParam(value = "festivalCanUse",required = true) boolean festivalCanUse,
                                             @RequestParam(value = "specHoursValid",required = false,defaultValue = "0") int specHoursValid,
                                             @RequestParam(value = "validDays",required = false,defaultValue = "-1") int validDays,
                                             @RequestParam(value = "validStartTime",required = false)@DateTimeFormat(pattern = "yyyy-MM-dd") Date validStartTime,
                                             @RequestParam(value = "validEndTime",required = false)@DateTimeFormat(pattern = "yyyy-MM-dd") Date validEndTime,
                                             @RequestParam(value = "validType",required = true) String validType

    ) {
        //店铺Id先固定
        Long storeId = 110L;
        products.setStoreId(storeId);
        ServiceStatusInfo<Long> serviceStatusInfo = this.productServiceImpl.createProducts(products,originalPrice,promotionPrice,
                festivalCanUse,specHoursValid,validDays, validStartTime, validEndTime,validType);
        if (serviceStatusInfo.isSuccess()) {
            return new ResponseData(ResponseDataCode.STATUS_NORMAL, "", serviceStatusInfo.getData());
        }
        return new ResponseData(ResponseDataCode.STATUS_ERROR, serviceStatusInfo.getMsg(), null);
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "删除商品")
    public ResponseData<Long> deleteProducts(@PathVariable Long id) {
        ServiceStatusInfo<Long> serviceStatusInfo = this.productServiceImpl.deleteProductsById(id);
        if (serviceStatusInfo.isSuccess()) {
            return new ResponseData(ResponseDataCode.STATUS_NORMAL, "", serviceStatusInfo.getData());
        }
        return new ResponseData(ResponseDataCode.STATUS_ERROR, serviceStatusInfo.getMsg(), null);
    }

    @ApiOperation(value = "修改商品")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseData<Long> updateProducts(@RequestBody Products products,
                                             @RequestParam(value = "originalPrice",required = true) long originalPrice,
                                             @RequestParam(value = "promotionPrice",required = true) long promotionPrice,
                                             @RequestParam(value = "festivalCanUse",required = true) boolean festivalCanUse,
                                             @RequestParam(value = "specHoursValid",required = false,defaultValue = "0") int specHoursValid,
                                             @RequestParam(value = "validDays",required = false,defaultValue = "-1") int validDays,
                                             @RequestParam(value = "validStartTime",required = false)@DateTimeFormat(pattern = "yyyy-MM-dd") Date validStartTime,
                                             @RequestParam(value = "validEndTime",required = false)@DateTimeFormat(pattern = "yyyy-MM-dd") Date validEndTime,
                                             @RequestParam(value = "validType",required = true) String validType) {
        ServiceStatusInfo<Long> serviceStatusInfo = this.productServiceImpl.updateProducts(products,originalPrice,promotionPrice,
                festivalCanUse,specHoursValid,validDays, validStartTime, validEndTime,validType);
        if (serviceStatusInfo.isSuccess()) {
            return new ResponseData(ResponseDataCode.STATUS_NORMAL, "", serviceStatusInfo.getData());
        }
        return new ResponseData(ResponseDataCode.STATUS_ERROR, serviceStatusInfo.getMsg(), null);
    }

    @ApiOperation(value = "搜索商品")
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public ResponsePageInfoData<List<Products>> serachProcducts(@RequestParam(value = "pageNo", required = true, defaultValue = "1") int pageNo,
                                                                @RequestParam(value = "rows", required = true, defaultValue = "30") int rows,
                                                                @RequestBody SearchProducts searchProduct) {

        PageHelper.startPage(pageNo, rows);
        List<Products> productsList = this.productServiceImpl.searchProducts(searchProduct).getData();
        PageInfo<Products> pageInfo = new PageInfo(productsList);
        return new ResponsePageInfoData(ResponseDataCode.STATUS_NORMAL, "", productsList, pageInfo.getTotal());
    }

    @PostMapping(value = "/updatePublishs")
    @ApiOperation(value = "批量商品上下架")
    public ResponseData<Long> updatePublishs(@RequestParam(value = "id", required =true ) Long[] id ,
                                            @RequestParam(value = "publish" ,required = true) boolean publish){
        ServiceStatusInfo<Long> serviceStatusInfo = this.productServiceImpl.updatePublishs(id,publish);
        if(serviceStatusInfo.isSuccess()){
            return new ResponseData<>(ResponseDataCode.STATUS_NORMAL, "", serviceStatusInfo.getData());
        }
        return new ResponseData<>(ResponseDataCode.STATUS_ERROR, serviceStatusInfo.getMsg(), null);
    }

    @GetMapping(value = "/selectById/{id}")
    @ApiOperation(value = "查询单个商品")
    public ResponseData<Map<String,Object>> selectById(@PathVariable long id){
        ServiceStatusInfo<Map<String,Object>> serviceStatusInfo = this.productServiceImpl.selectById(id);
        if(serviceStatusInfo.isSuccess()){
            return new ResponseData<>(ResponseDataCode.STATUS_NORMAL, "", serviceStatusInfo.getData());
        }
        return new ResponseData<>(ResponseDataCode.STATUS_ERROR, serviceStatusInfo.getMsg() , null);
    }

    @PostMapping(value = "/deleteByProducts")
    @ApiOperation(value = "批量删除商品")
    public ResponseData<Long> deleteByProducts(@RequestParam(value = "id" , required = true) Long[] id){
        ServiceStatusInfo<Long> serviceStatusInfo = this.productServiceImpl.deleteByProducts(id);
        if(serviceStatusInfo.isSuccess()){
            return new ResponseData<>(ResponseDataCode.STATUS_NORMAL, "", serviceStatusInfo.getData());
        }
        return new ResponseData<>(ResponseDataCode.STATUS_ERROR, serviceStatusInfo.getMsg() , null);
    }

}