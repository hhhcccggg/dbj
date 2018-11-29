package com.zwdbj.server.shopadmin.Controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zwdbj.server.shop_admin_service.products.model.Products;
import com.zwdbj.server.shop_admin_service.products.service.ProductService;
import com.zwdbj.server.utility.model.ResponseData;
import com.zwdbj.server.utility.model.ResponseDataCode;
import com.zwdbj.server.utility.model.ResponsePageInfoData;
import com.zwdbj.server.utility.model.ServiceStatusInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/products")
@Api(description = "商品相关")
public class ProductsController {
    @Resource
    private ProductService productServiceImpl;

    @RequestMapping(value = "/select", method = RequestMethod.GET)
    @ApiOperation(value = "查询所有商品")
    public ResponseData<List<Products>> findAllProducts(@RequestParam(value = "pageNo", required = true, defaultValue = "1") int pageNo, @RequestParam(value = "rows", required = true, defaultValue = "30") int rows) {

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

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    @ApiOperation(value = "删除商品")
    public ResponseData<Long> deleteProducts(@RequestBody Products products) {
        ServiceStatusInfo<Long> serviceStatusInfo = this.productServiceImpl.deleteProductsById(products);
        if (serviceStatusInfo.isSuccess()) {
            return new ResponseData(ResponseDataCode.STATUS_NORMAL, "", serviceStatusInfo.getData());
        }
        return new ResponseData(ResponseDataCode.STATUS_ERROR, serviceStatusInfo.getMsg(), null);
    }

    @ApiOperation(value = "修改商品")
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public ResponseData<Long> updateProducts(@RequestBody Products products){
        ServiceStatusInfo<Long> serviceStatusInfo = this.productServiceImpl.updateProducts(products);
        if (serviceStatusInfo.isSuccess()) {
            return new ResponseData(ResponseDataCode.STATUS_NORMAL, "", serviceStatusInfo.getData());
        }
        return new ResponseData(ResponseDataCode.STATUS_ERROR, serviceStatusInfo.getMsg(), null);
    }

}