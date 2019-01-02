package com.zwdbj.server.shopadmin.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zwdbj.server.shop_admin_service.service.productBrands.model.ProductBrands;
import com.zwdbj.server.shop_admin_service.service.productBrands.service.ProductBrandsService;
import com.zwdbj.server.utility.model.ResponseData;
import com.zwdbj.server.utility.model.ResponseDataCode;
import com.zwdbj.server.utility.model.ResponsePageInfoData;
import com.zwdbj.server.utility.model.ServiceStatusInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/productBrands/dbj")
@Api(produces = "品牌相关")
public class ProductBrandsController {
    @Autowired
    private ProductBrandsService productBrandsServiceImpl;

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ApiOperation(value = "创建品牌")
    public ResponseData<Long> create(@RequestBody ProductBrands productBrands) {
        ServiceStatusInfo<Long> serviceStatusInfo = productBrandsServiceImpl.createProductBrands(productBrands);
        if (serviceStatusInfo.isSuccess()) {
            return new ResponseData(ResponseDataCode.STATUS_NORMAL, "", serviceStatusInfo.getData());
        }
        return new ResponseData(ResponseDataCode.STATUS_ERROR, serviceStatusInfo.getMsg(), null);
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "删除品牌")
    public ResponseData<Long> delete(@PathVariable Long id) {
        ServiceStatusInfo<Long> serviceStatusInfo = productBrandsServiceImpl.deleteById(id);
        if (serviceStatusInfo.isSuccess()) {
            return new ResponseData(ResponseDataCode.STATUS_NORMAL, "", serviceStatusInfo.getData());
        }
        return new ResponseData(ResponseDataCode.STATUS_ERROR, serviceStatusInfo.getMsg(), null);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ApiOperation(value = "修改品牌")
    public ResponseData<Long> update(@RequestBody ProductBrands productBrands) {
        ServiceStatusInfo<Long> serviceStatusInfo = productBrandsServiceImpl.updateProductBrands(productBrands);
        if (serviceStatusInfo.isSuccess()) {
            return new ResponseData(ResponseDataCode.STATUS_NORMAL, "", serviceStatusInfo.getData());
        }
        return new ResponseData(ResponseDataCode.STATUS_ERROR, serviceStatusInfo.getMsg(), null);
    }

    @RequestMapping(value = "/select", method = RequestMethod.GET)
    @ApiOperation(value = "查询品牌")
    public ResponsePageInfoData<List<ProductBrands>> select(@RequestParam(value = "pageNo", required = true, defaultValue = "1") int pageNo,
                                                            @RequestParam(value = "rows", required = true, defaultValue = "30") int rows) {
        PageHelper.startPage(pageNo, rows);
        List<ProductBrands> list = productBrandsServiceImpl.selectAll().getData();
        PageInfo<ProductBrands> pageInfo = new PageInfo(list);
        return new ResponsePageInfoData(ResponseDataCode.STATUS_NORMAL, "", list, pageInfo.getTotal());

    }
}
