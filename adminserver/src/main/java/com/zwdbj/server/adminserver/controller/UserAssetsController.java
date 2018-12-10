package com.zwdbj.server.adminserver.controller;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zwdbj.server.adminserver.service.userAssets.model.UserAssets;
import com.zwdbj.server.adminserver.service.userAssets.model.UserCoinDetail;
import com.zwdbj.server.adminserver.service.userAssets.model.UserCoinType;
import com.zwdbj.server.adminserver.service.userAssets.service.UserAssetsService;
import com.zwdbj.server.utility.model.ResponseData;
import com.zwdbj.server.utility.model.ResponseDataCode;
import com.zwdbj.server.utility.model.ResponsePageInfoData;
import com.zwdbj.server.utility.model.ServiceStatusInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RequestMapping(value = "/api/userAssets/dbj")
@RestController
@Api(description = "用户资产相关")
public class UserAssetsController {
    @Resource
    private UserAssetsService userAssetsServiceImpl;

    @ApiOperation(value = "查询所有用户资产相关")
    @RequestMapping(value = "/searchUserAssets", method = RequestMethod.GET)
    public ResponsePageInfoData<List<UserAssets>> searchAllUserAssets(@RequestParam(value = "pageNo", defaultValue = "1", required = true) int pageNo,
                                                                      @RequestParam(value = "rows", defaultValue = "30", required = true) int rows) {

        PageHelper.startPage(pageNo, rows);
        List<UserAssets> result = this.userAssetsServiceImpl.searchAllUserAssets().getData();
        PageInfo pageInfo = new PageInfo(result);
        return new ResponsePageInfoData<>(ResponseDataCode.STATUS_NORMAL, "", result, pageInfo.getTotal());
    }

    @RequestMapping(value = "/searchUserAssets/{userId}", method = RequestMethod.GET)
    @ApiOperation(value = "通过id查询用户资产")
    public ResponseData<UserAssets> searchUserAssetsByUserId(@PathVariable("userId") Long userId) {
        ServiceStatusInfo<UserAssets> serviceStatusInfo = this.userAssetsServiceImpl.searchUserAssetsByUserId(userId);
        if (serviceStatusInfo.isSuccess()) {
            return new ResponseData<>(ResponseDataCode.STATUS_NORMAL, "", serviceStatusInfo.getData());
        }
        return new ResponseData<>(ResponseDataCode.STATUS_ERROR, serviceStatusInfo.getMsg(), null);
    }

    @ApiOperation(value = "分类查询用户金币总额")
    @RequestMapping(value = "/searchUserCoinType", method = RequestMethod.GET)
    public ResponsePageInfoData<List<UserCoinType>> searchAllUserCoinType(@RequestParam(value = "pageNo", defaultValue = "1", required = true) int pageNo,
                                                                          @RequestParam(value = "rows", defaultValue = "30", required = true) int rows) {

        PageHelper.startPage(pageNo, rows);
        List<UserCoinType> result = this.userAssetsServiceImpl.searchAllUserCoinTyoe().getData();
        PageInfo pageInfo = new PageInfo(result);
        System.out.println(result);
        return new ResponsePageInfoData<>(ResponseDataCode.STATUS_NORMAL, "", result, pageInfo.getTotal());
    }

    @RequestMapping(value = "/searchUserCoinType/{userId}", method = RequestMethod.GET)
    @ApiOperation(value = "通过id分类查询用户金币总额")
    public ResponseData<UserCoinType> searchUserCoinTypeByUserId(@PathVariable("userId") Long userId) {
        ServiceStatusInfo<UserCoinType> serviceStatusInfo = this.userAssetsServiceImpl.searchUserCoinTypeByUserId(userId);
        if (serviceStatusInfo.isSuccess()) {
            return new ResponseData<>(ResponseDataCode.STATUS_NORMAL, "", serviceStatusInfo.getData());
        }
        return new ResponseData<>(ResponseDataCode.STATUS_ERROR, serviceStatusInfo.getMsg(), null);
    }

    @ApiOperation(value = "查询所有用户金币明细")
    @RequestMapping(value = "/searchUserCoinDetail", method = RequestMethod.GET)
    public ResponsePageInfoData<List<UserCoinDetail>> searchAllUserCoinDetail(@RequestParam(value = "pageNo", defaultValue = "1", required = true) int pageNo,
                                                                              @RequestParam(value = "rows", defaultValue = "30", required = true) int rows) {

        PageHelper.startPage(pageNo, rows);
        List<UserCoinDetail> result = this.userAssetsServiceImpl.searchAllUserCoinDetail().getData();
        PageInfo pageInfo = new PageInfo(result);
        return new ResponsePageInfoData<>(ResponseDataCode.STATUS_NORMAL, "", result, pageInfo.getTotal());
    }

    @RequestMapping(value = "/searchUserCoinDetail/{userId}", method = RequestMethod.GET)
    @ApiOperation(value = "通过id查询用户资产")
    public ResponseData<UserCoinDetail> searchByUserId(@PathVariable("userId") Long userId) {
        ServiceStatusInfo<UserCoinDetail> serviceStatusInfo = this.userAssetsServiceImpl.searchUserCoinDetailByUserId(userId);
        if (serviceStatusInfo.isSuccess()) {
            return new ResponseData<>(ResponseDataCode.STATUS_NORMAL, "", serviceStatusInfo.getData());
        }
        return new ResponseData<>(ResponseDataCode.STATUS_ERROR, serviceStatusInfo.getMsg(), null);
    }
}
