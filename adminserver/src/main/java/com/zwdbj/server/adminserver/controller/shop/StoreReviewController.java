package com.zwdbj.server.adminserver.controller.shop;


import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zwdbj.server.adminserver.identity.RoleIdentity;
import com.zwdbj.server.adminserver.service.shop.service.storeReview.model.BusinessSellerReviewModel;
import com.zwdbj.server.adminserver.service.shop.service.storeReview.model.StoreReviewAddInput;
import com.zwdbj.server.adminserver.service.shop.service.storeReview.service.StoreReviewService;
import com.zwdbj.server.basemodel.model.ResponseData;
import com.zwdbj.server.basemodel.model.ResponseDataCode;
import com.zwdbj.server.basemodel.model.ResponsePageInfoData;
import com.zwdbj.server.basemodel.model.ServiceStatusInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/shop/storeReview/dbj")
@Api(description = "店铺认证相关")
public class StoreReviewController {
    @Autowired
    StoreReviewService storeReviewServiceImpl;

    @RequiresAuthentication
    @RequiresRoles(value = {RoleIdentity.ADMIN_ROLE, RoleIdentity.MARKET_ROLE}, logical = Logical.OR)
    @RequestMapping(value = "/select", method = RequestMethod.GET)
    @ApiOperation(value = "查询所有店铺认证信息")
    public ResponsePageInfoData<List<BusinessSellerReviewModel>> findAllStoreReviews(@RequestParam(value = "pageNo", required = true, defaultValue = "1") int pageNo,
                                                                                     @RequestParam(value = "rows", required = true, defaultValue = "30") int rows) {
        Page<BusinessSellerReviewModel> pageInfo = PageHelper.startPage(pageNo, rows);
        List<BusinessSellerReviewModel> businessSellerReviewModels = this.storeReviewServiceImpl.findAllStoreReviews();
        return new ResponsePageInfoData(ResponseDataCode.STATUS_NORMAL, "", businessSellerReviewModels, pageInfo.getTotal());

    }

    @RequiresAuthentication
    @RequiresRoles(value = {RoleIdentity.ADMIN_ROLE, RoleIdentity.MARKET_ROLE}, logical = Logical.OR)
    @RequestMapping(value = "/select/{legalSubjectId}", method = RequestMethod.GET)
    @ApiOperation(value = "查询店铺认证信息详情")
    public ResponsePageInfoData<List<BusinessSellerReviewModel>> getStoreReviewById(@PathVariable long legalSubjectId,
                                                                                    @RequestParam(value = "pageNo", required = true, defaultValue = "1") int pageNo,
                                                                                    @RequestParam(value = "rows", required = true, defaultValue = "30") int rows) {
        Page<BusinessSellerReviewModel> pageInfo = PageHelper.startPage(pageNo, rows);
        ServiceStatusInfo<List<BusinessSellerReviewModel>> businessSellerReviewModel = this.storeReviewServiceImpl.getStoreReviewById(legalSubjectId);
        if (businessSellerReviewModel.isSuccess()) {
            return new ResponsePageInfoData<>(ResponseDataCode.STATUS_NORMAL, businessSellerReviewModel.getMsg(), businessSellerReviewModel.getData(), pageInfo.getTotal());
        }
        return new ResponsePageInfoData<>(ResponseDataCode.STATUS_ERROR, businessSellerReviewModel.getMsg(), null, 0);
    }

    @RequiresAuthentication
    @RequestMapping(value = "/modify/{id}", method = RequestMethod.POST)
    @ApiOperation(value = "修改店铺认证信息,id为认证资料的id")
    public ResponseData<Integer> modifyStoreReview(@PathVariable long id,
                                                   @RequestBody StoreReviewAddInput input) {
        ServiceStatusInfo<Integer> statusInfo = this.storeReviewServiceImpl.modifyStoreReview(id, input);
        if (statusInfo.isSuccess()) {
            return new ResponseData<>(ResponseDataCode.STATUS_NORMAL, statusInfo.getMsg(), null);
        }
        return new ResponseData<>(ResponseDataCode.STATUS_ERROR, statusInfo.getMsg(), null);
    }

    @RequiresAuthentication
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ApiOperation(value = "添加店铺认证信息")
    public ResponseData<Integer> addStoreReview(@RequestBody StoreReviewAddInput input) {
        ServiceStatusInfo<Integer> statusInfo = this.storeReviewServiceImpl.addStoreReview(input);
        if (statusInfo.isSuccess()) {
            return new ResponseData<>(ResponseDataCode.STATUS_NORMAL, statusInfo.getMsg(), null);
        }
        return new ResponseData<>(ResponseDataCode.STATUS_ERROR, statusInfo.getMsg(), null);
    }

    @RequiresAuthentication
    @RequestMapping(value = "/delete/{id}/{legalSubjectId}", method = RequestMethod.GET)
    @ApiOperation(value = "删除此认证信息,id为认证资料的id")
    public ResponseData<Integer> deleteStoreReview(@PathVariable(value = "id") long id, @PathVariable(value = "legalSubjectId") long legalSubjectId) {
        ServiceStatusInfo<Integer> statusInfo = this.storeReviewServiceImpl.deleteStoreReview(id,legalSubjectId);
        if (statusInfo.isSuccess()) {
            return new ResponseData<>(ResponseDataCode.STATUS_NORMAL, statusInfo.getMsg(), null);
        }
        return new ResponseData<>(ResponseDataCode.STATUS_ERROR, statusInfo.getMsg(), null);
    }
}
