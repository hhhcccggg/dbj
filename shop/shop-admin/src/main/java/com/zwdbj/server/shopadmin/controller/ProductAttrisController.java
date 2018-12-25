package com.zwdbj.server.shopadmin.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zwdbj.server.shop_admin_service.ProductAttris.model.ProductAttris;
import com.zwdbj.server.shop_admin_service.ProductAttris.service.ProductAttrisService;
import com.zwdbj.server.utility.model.ResponseData;
import com.zwdbj.server.utility.model.ResponseDataCode;
import com.zwdbj.server.utility.model.ResponsePageInfoData;
import com.zwdbj.server.utility.model.ServiceStatusInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(value = "/api/productAttris/dbj")
@RestController
@Api(description = "商品属性规格相关")
public class ProductAttrisController {

    @Autowired
    private ProductAttrisService productAttrisServiceImpl;

    @RequestMapping(value = "/select", method = RequestMethod.GET)
    @ApiOperation(value = "查询所有商品属性规格")
    public ResponsePageInfoData<List<ProductAttrisController>> selectAll(@RequestParam(value = "pageNo", defaultValue = "1", required = true) Integer pageNo,
                                                                         @RequestParam(value = "rows", defaultValue = "30", required = true) Integer rows) {
        PageHelper.startPage(pageNo, rows);
        List<ProductAttris> list = productAttrisServiceImpl.select().getData();
        PageInfo<ProductAttris> pageInfo = new PageInfo<>(list);
        return new ResponsePageInfoData(ResponseDataCode.STATUS_NORMAL, "", list, pageInfo.getTotal());
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ApiOperation(value = "修改商品属性规格")
    public ResponseData<Long> updateProdcutAttris(@RequestBody ProductAttris productAttris) {
        ServiceStatusInfo<Long> serviceStatusInfo = productAttrisServiceImpl.updateProductAttris(productAttris);
        if (serviceStatusInfo.isSuccess()) {
            return new ResponseData(ResponseDataCode.STATUS_NORMAL, "", serviceStatusInfo.getData());
        }
        return new ResponseData<>(ResponseDataCode.STATUS_ERROR, serviceStatusInfo.getMsg(), null);
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ApiOperation(value = "增加商品属性规格")
    public ResponseData<Long> createProdcutAttris(@RequestBody ProductAttris productAttris) {
        ServiceStatusInfo<Long> serviceStatusInfo = productAttrisServiceImpl.createProductAttris(productAttris);
        if (serviceStatusInfo.isSuccess()) {
            return new ResponseData(ResponseDataCode.STATUS_NORMAL, "", serviceStatusInfo.getData());
        }
        return new ResponseData<>(ResponseDataCode.STATUS_ERROR, serviceStatusInfo.getMsg(), null);
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "删除商品属性规格")
    public ResponseData<Long> deleteById(@PathVariable("id") Long id) {
        ServiceStatusInfo<Long> serviceStatusInfo = productAttrisServiceImpl.deleteById(id);
        if (serviceStatusInfo.isSuccess()) {
            return new ResponseData(ResponseDataCode.STATUS_NORMAL, "", serviceStatusInfo.getData());
        }
        return new ResponseData<>(ResponseDataCode.STATUS_ERROR, serviceStatusInfo.getMsg(), null);
    }
}
