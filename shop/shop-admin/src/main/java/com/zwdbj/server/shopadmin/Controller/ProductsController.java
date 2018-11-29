package com.zwdbj.server.shopadmin.Controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zwdbj.server.shop_admin_service.products.model.Products;
import com.zwdbj.server.shop_admin_service.products.service.ProductService;
import com.zwdbj.server.utility.model.ResponseData;
import com.zwdbj.server.utility.model.ResponseDataCode;
import com.zwdbj.server.utility.model.ResponsePageInfoData;
import com.zwdbj.server.utility.model.ServiceStatusInfo;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping
@Api(description = "商品相关")
public class ProductsController {
    @Resource
    private ProductService productServiceImpl=null;

    @RequestMapping(value = "/",method = RequestMethod.GET)
    public ResponseData<List<Products>> findAllProducts( @RequestParam(value = "pageNo",required = true,defaultValue = "1") int pageNo,@RequestParam(value = "rows",required = true,defaultValue = "30") int rows){


        Page<List<Products>> pageInfo = PageHelper.startPage(pageNo,rows);
        ServiceStatusInfo<List<Products>> serviceStatusInfo=this.productServiceImpl.selectAll(pageNo,rows);
        List<Products> userModelList = serviceStatusInfo.getData();
            return new ResponsePageInfoData<>(ResponseDataCode.STATUS_NORMAL,
                    "",userModelList,pageInfo.getTotal());
      
    }

}
