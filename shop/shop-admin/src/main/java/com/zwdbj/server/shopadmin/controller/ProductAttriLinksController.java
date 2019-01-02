package com.zwdbj.server.shopadmin.controller;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zwdbj.server.shop_admin_service.service.productAttriLinks.model.ProductAttriLinks;
import com.zwdbj.server.shop_admin_service.service.productAttriLinks.service.ProductAttriLinksService;
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
@RequestMapping(value = "/api/productAttriLinks/dbj")
@Api(description = "商品属性关系相关")
public class ProductAttriLinksController {

    @Autowired
    private ProductAttriLinksService productAttriLinksServiceImpl;

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ApiOperation(value = "添加商品属性关系")
    public ResponseData<Long> createProductAttriLinks(@RequestBody ProductAttriLinks productAttriLinks) {
        ServiceStatusInfo<Long> serviceStatusInfo = productAttriLinksServiceImpl.createProductAttriLinks(productAttriLinks);
        if (serviceStatusInfo.isSuccess()) {
            return new ResponseData<>(ResponseDataCode.STATUS_NORMAL, "", serviceStatusInfo.getData());
        }
        return new ResponseData<>(ResponseDataCode.STATUS_ERROR, serviceStatusInfo.getMsg(), null);
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "删除商品属性关系")
    public ResponseData<Long> deleteProductAttriLinks(@PathVariable("id") Long id) {
        ServiceStatusInfo<Long> serviceStatusInfo = productAttriLinksServiceImpl.deleteById(id);
        if (serviceStatusInfo.isSuccess()) {
            return new ResponseData<>(ResponseDataCode.STATUS_NORMAL, "", serviceStatusInfo.getData());
        }
        return new ResponseData<>(ResponseDataCode.STATUS_ERROR, serviceStatusInfo.getMsg(), null);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ApiOperation(value = "修改商品属性关系")
    public ResponseData<Long> updateProductAttriLinks(@RequestBody ProductAttriLinks productAttriLinks) {
        ServiceStatusInfo<Long> serviceStatusInfo = productAttriLinksServiceImpl.updateProductAttriLinks(productAttriLinks);
        if (serviceStatusInfo.isSuccess()) {
            return new ResponseData<>(ResponseDataCode.STATUS_NORMAL, "", serviceStatusInfo.getData());
        }
        return new ResponseData<>(ResponseDataCode.STATUS_ERROR, serviceStatusInfo.getMsg(), null);

    }

    @RequestMapping(value = "/select", method = RequestMethod.GET)
    @ApiOperation(value = "查询所有商品属性关系")
    public ResponsePageInfoData<List<ProductAttriLinks>> selectAll(@RequestParam(value = "pageNo", defaultValue = "1", required = true) Integer pageNo,
                                                                   @RequestParam(value = "rows", defaultValue = "30", required = true) Integer rows) {
        PageHelper.startPage(pageNo, rows);
        List<ProductAttriLinks> list = productAttriLinksServiceImpl.select().getData();
        PageInfo<ProductAttriLinks> pageInfo = new PageInfo<>(list);
        return new ResponsePageInfoData<>(ResponseDataCode.STATUS_NORMAL, "", list, pageInfo.getTotal());
    }
}
