package com.zwdbj.server.shopadmin.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zwdbj.server.shop_admin_service.products.model.Products;
import com.zwdbj.server.shop_admin_service.products.model.SearchProducts;
import com.zwdbj.server.shop_admin_service.products.service.ProductService;
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
@RequestMapping("/api/products/dbj")
@Api(description = "商品相关")
public class ProductsController {
    @Autowired
    private ProductService productServiceImpl;

    @RequestMapping(value = "/select", method = RequestMethod.GET)
    @ApiOperation(value = "查询所有商品")
    public ResponsePageInfoData<List<Products>> findAllProducts(@RequestParam(value = "pageNo", required = true, defaultValue = "1") int pageNo,
                                                                @RequestParam(value = "rows", required = true, defaultValue = "30") int rows) {

        PageHelper.startPage(pageNo, rows);
        List<Products> productsList = this.productServiceImpl.selectAll().getData();
        PageInfo<Products> pageInfo = new PageInfo(productsList);
        return new ResponsePageInfoData(ResponseDataCode.STATUS_NORMAL, "", productsList, pageInfo.getTotal());

    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ApiOperation(value = "创建商品")
    public ResponseData<Long> createProducts(@RequestBody Products products) {
        ServiceStatusInfo<Long> serviceStatusInfo = this.productServiceImpl.createProducts(products);
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
    public ResponseData<Long> updateProducts(@RequestBody Products products) {
        ServiceStatusInfo<Long> serviceStatusInfo = this.productServiceImpl.updateProducts(products);
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
}
