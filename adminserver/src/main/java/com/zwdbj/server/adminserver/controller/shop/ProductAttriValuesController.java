package com.zwdbj.server.adminserver.controller.shop;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zwdbj.server.adminserver.service.shop.service.productAttriValues.model.ProductAttriValues;
import com.zwdbj.server.adminserver.service.shop.service.productAttriValues.service.ProductAttriValuesService;
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

@RequestMapping(value = "/api/shop/productAttriValues/dbj")
@RestController
@Api(description = "商品属性规格值相关")
public class ProductAttriValuesController {
    @Autowired
    private ProductAttriValuesService productAttriValuesServiceImpl;

    @RequiresAuthentication
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ApiOperation("添加商品属性规格值")
    private ResponseData<Long> create(@RequestBody ProductAttriValues productAttriValues) {
        ServiceStatusInfo<Long> serviceStatusInfo = productAttriValuesServiceImpl.createProductAttriValues(productAttriValues);
        if (serviceStatusInfo.isSuccess()) {
            return new ResponseData<>(ResponseDataCode.STATUS_NORMAL, "", serviceStatusInfo.getData());
        }
        return new ResponseData<>(ResponseDataCode.STATUS_ERROR, serviceStatusInfo.getMsg(), null);
    }

    @RequiresAuthentication
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    @ApiOperation("删除商品属性规格值")
    public ResponseData<Long> delete(@PathVariable("id") Long id) {
        ServiceStatusInfo<Long> serviceStatusInfo = productAttriValuesServiceImpl.deleteById(id);
        if (serviceStatusInfo.isSuccess()) {
            return new ResponseData<>(ResponseDataCode.STATUS_NORMAL, "", serviceStatusInfo.getData());
        }
        return new ResponseData<>(ResponseDataCode.STATUS_ERROR, serviceStatusInfo.getMsg(), null);
    }

    @RequiresAuthentication
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ApiOperation("添加商品属性规格值")
    public ResponseData<Long> update(@RequestBody ProductAttriValues productAttriValues) {
        ServiceStatusInfo<Long> serviceStatusInfo = productAttriValuesServiceImpl.updateProductAttriValues(productAttriValues);
        if (serviceStatusInfo.isSuccess()) {
            return new ResponseData<>(ResponseDataCode.STATUS_NORMAL, "", serviceStatusInfo.getData());
        }
        return new ResponseData<>(ResponseDataCode.STATUS_ERROR, serviceStatusInfo.getMsg(), null);
    }

    @RequiresAuthentication
    @RequestMapping(value = "/select", method = RequestMethod.GET)
    @ApiOperation(value = "查询商品规格属性值")
    public ResponsePageInfoData<List<ProductAttriValues>> select(@RequestParam(value = "pageNo", defaultValue = "1", required = true) int pageNo,
                                                                 @RequestParam(value = "rows", defaultValue = "30", required = true) int rows) {
        PageHelper.startPage(pageNo, rows);
        List<ProductAttriValues> list = productAttriValuesServiceImpl.select().getData();
        PageInfo<ProductAttriValues> pageInfo = new PageInfo<>(list);
        return new ResponsePageInfoData<>(ResponseDataCode.STATUS_NORMAL, "", list, pageInfo.getTotal());

    }

}
