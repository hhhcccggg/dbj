package com.zwdbj.server.adminserver.controller.shop;


import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zwdbj.server.shop_admin_service.service.storeReview.model.BusinessSellerReviewModel;
import com.zwdbj.server.shop_admin_service.service.storeReview.model.StoreReviewAddInput;
import com.zwdbj.server.shop_admin_service.service.storeReview.service.StoreReviewService;
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
@RequestMapping("/api/shop/storeReview/dbj")
@Api(description = "店铺认证相关")
public class StoreReviewController {
    @Autowired
    StoreReviewService storeReviewServiceImpl;

    @RequestMapping(value = "/select", method = RequestMethod.GET)
    @ApiOperation(value = "查询所有店铺认证信息")
    public ResponsePageInfoData<List<BusinessSellerReviewModel>> findAllStoreReviews(@RequestParam(value = "pageNo", required = true, defaultValue = "1") int pageNo,
                                                                                        @RequestParam(value = "rows", required = true, defaultValue = "30") int rows) {
        Page<BusinessSellerReviewModel> pageInfo = PageHelper.startPage(pageNo,rows);
        List<BusinessSellerReviewModel> businessSellerReviewModels = this.storeReviewServiceImpl.findAllStoreReviews();
        return new ResponsePageInfoData(ResponseDataCode.STATUS_NORMAL, "", businessSellerReviewModels, pageInfo.getTotal());

    }

    @RequestMapping(value = "/select/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "查询店铺认证信息详情")
    public ResponseData<BusinessSellerReviewModel> getStoreReviewById(@PathVariable long id) {
        ServiceStatusInfo<BusinessSellerReviewModel> businessSellerReviewModel = this.storeReviewServiceImpl.getStoreReviewById(id);
        if (businessSellerReviewModel.isSuccess()) {
            return new ResponseData<>(ResponseDataCode.STATUS_NORMAL,businessSellerReviewModel.getMsg(),businessSellerReviewModel.getData());
        }
        return new ResponseData<>(ResponseDataCode.STATUS_ERROR,businessSellerReviewModel.getMsg(),null);
    }

    @RequestMapping(value = "/modify/{id}", method = RequestMethod.POST)
    @ApiOperation(value = "修改店铺认证信息")
    public ResponseData<Integer> modifyStoreReview(@PathVariable long id,
                                                       @RequestBody StoreReviewAddInput input) {
        ServiceStatusInfo <Integer> statusInfo = this.storeReviewServiceImpl.modifyStoreReview(id,input);
        if (statusInfo.isSuccess()) {
            return new ResponseData<>(ResponseDataCode.STATUS_NORMAL,statusInfo.getMsg(),null);
        }
        return new ResponseData<>(ResponseDataCode.STATUS_ERROR,statusInfo.getMsg(),null);
    }

    @RequestMapping(value = "/add/", method = RequestMethod.POST)
    @ApiOperation(value = "添加店铺认证信息")
    public ResponseData<Integer> addStoreReview(@RequestBody StoreReviewAddInput input) {
        ServiceStatusInfo <Integer> statusInfo = this.storeReviewServiceImpl.addStoreReview(input);
        if (statusInfo.isSuccess()) {
            return new ResponseData<>(ResponseDataCode.STATUS_NORMAL,statusInfo.getMsg(),null);
        }
        return new ResponseData<>(ResponseDataCode.STATUS_ERROR,statusInfo.getMsg(),null);
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "删除店铺认证信息")
    public ResponseData<Integer> deleteStoreReview(@PathVariable(value = "id") long businessSellerId) {
        ServiceStatusInfo <Integer> statusInfo = this.storeReviewServiceImpl.deleteStoreReview(businessSellerId);
        if (statusInfo.isSuccess()) {
            return new ResponseData<>(ResponseDataCode.STATUS_NORMAL,statusInfo.getMsg(),null);
        }
        return new ResponseData<>(ResponseDataCode.STATUS_ERROR,statusInfo.getMsg(),null);
    }
}
