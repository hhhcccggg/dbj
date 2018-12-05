package com.zwdbj.server.shopadmin.Controller;


import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zwdbj.server.shop_admin_service.BusinessSellers.model.BusinessSellerAddInput;
import com.zwdbj.server.shop_admin_service.BusinessSellers.model.BusinessSellerModel;
import com.zwdbj.server.shop_admin_service.BusinessSellers.model.BusinessSellerModifyInput;
import com.zwdbj.server.shop_admin_service.BusinessSellers.service.BusinessSellerService;
import com.zwdbj.server.shop_admin_service.BusinessSellers.service.BusinessSellerServiceImpl;
import com.zwdbj.server.utility.model.ResponseData;
import com.zwdbj.server.utility.model.ResponseDataCode;
import com.zwdbj.server.utility.model.ResponsePageInfoData;
import com.zwdbj.server.utility.model.ServiceStatusInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/api/seller/dbj")
@Api(description = "店铺相关")
public class BusinessSellerController {
    @Autowired
    BusinessSellerServiceImpl businessSellerService;

    @RequestMapping(value = "/select", method = RequestMethod.GET)
    @ApiOperation(value = "查询所有店铺")
    public ResponsePageInfoData<List<BusinessSellerModel>> findAllBusinessSellers(@RequestParam(value = "pageNo", required = true, defaultValue = "1") int pageNo,
                                                                               @RequestParam(value = "rows", required = true, defaultValue = "30") int rows) {
        Page<BusinessSellerModel> pageInfo = PageHelper.startPage(pageNo,rows);
        List<BusinessSellerModel> businessSellerModels = this.businessSellerService.findAllBusinessSellers();
        return new ResponsePageInfoData(ResponseDataCode.STATUS_NORMAL, "", businessSellerModels, pageInfo.getTotal());

    }

    @RequestMapping(value = "/select/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "查询店铺详情")
    public ResponseData<BusinessSellerModel> getBusinessSellerById(@PathVariable long id) {
        ServiceStatusInfo <BusinessSellerModel> businessSellerModel = this.businessSellerService.getBusinessSellerById(id);
        if (businessSellerModel.isSuccess()) {
            return new ResponseData<>(ResponseDataCode.STATUS_NORMAL,businessSellerModel.getMsg(),businessSellerModel.getData());
        }
        return new ResponseData<>(ResponseDataCode.STATUS_ERROR,businessSellerModel.getMsg(),null);
    }

    @RequestMapping(value = "/modify/{id}", method = RequestMethod.POST)
    @ApiOperation(value = "修改店铺信息")
    public ResponseData<Integer> modifyBusinessSellers(@PathVariable long id,
                                                       @RequestBody BusinessSellerModifyInput input) {
        ServiceStatusInfo <Integer> statusInfo = this.businessSellerService.modifyBusinessSellers(id,input);
        if (statusInfo.isSuccess()) {
            return new ResponseData<>(ResponseDataCode.STATUS_NORMAL,statusInfo.getMsg(),null);
        }
        return new ResponseData<>(ResponseDataCode.STATUS_ERROR,statusInfo.getMsg(),null);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ApiOperation(value = "添加店铺")
    public ResponseData<Integer> addBusinessSellers(@RequestBody BusinessSellerAddInput input) {
        ServiceStatusInfo <Integer> statusInfo = this.businessSellerService.addBusinessSellers(input);
        if (statusInfo.isSuccess()) {
            return new ResponseData<>(ResponseDataCode.STATUS_NORMAL,statusInfo.getMsg(),null);
        }
        return new ResponseData<>(ResponseDataCode.STATUS_ERROR,statusInfo.getMsg(),null);
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "删除店铺")
    public ResponseData<Integer> deleteBusinessSellers(@PathVariable(value = "id") long id) {
        ServiceStatusInfo <Integer> statusInfo = this.businessSellerService.deleteBusinessSellers(id);
        if (statusInfo.isSuccess()) {
            return new ResponseData<>(ResponseDataCode.STATUS_NORMAL,statusInfo.getMsg(),null);
        }
        return new ResponseData<>(ResponseDataCode.STATUS_ERROR,statusInfo.getMsg(),null);
    }





}
