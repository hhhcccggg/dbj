package com.zwdbj.server.mobileapi.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zwdbj.server.mobileapi.service.userAssets.model.UserAssetModel;
import com.zwdbj.server.mobileapi.service.userAssets.model.UserCoinDetailsModel;
import com.zwdbj.server.mobileapi.service.userAssets.model.UserCoinTypeModel;
import com.zwdbj.server.mobileapi.service.userAssets.service.UserAssetServiceImpl;
import com.zwdbj.server.utility.model.ResponseData;
import com.zwdbj.server.utility.model.ResponseDataCode;
import com.zwdbj.server.utility.model.ResponsePageInfoData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/coin")
@Api(description = "用户金币相关",value = "coin")
public class UserAssetController {
    @Autowired
    private UserAssetServiceImpl userAssetServiceImpl;

    @RequestMapping(value = "/myCoins",method = RequestMethod.GET)
    @ApiOperation(value = "得到我的全部金币资产")
    @RequiresAuthentication
    public ResponseData<UserAssetModel> getCoinsByUserId(){
        UserAssetModel userAssetModel = this.userAssetServiceImpl.getCoinsByUserId();
        if (userAssetModel!=null){
            return new ResponseData<>(ResponseDataCode.STATUS_NORMAL,"",userAssetModel);
        }else {
            return new ResponseData<>(ResponseDataCode.STATUS_ERROR,"获取失败",null);
        }
    }
    @RequestMapping(value = "/myCoinsByType",method = RequestMethod.GET)
    @ApiOperation(value = "根据金币分类得到我的金币资产")
    @RequiresAuthentication
    public ResponseData<UserCoinTypeModel> getUserCoinType(@RequestParam String type){
        UserCoinTypeModel userCoinTypeModel = this.userAssetServiceImpl.getUserCoinType(type);
        if (userCoinTypeModel!=null){
            return new ResponseData<>(ResponseDataCode.STATUS_NORMAL,"",userCoinTypeModel);
        }else {
            return new ResponseData<>(ResponseDataCode.STATUS_ERROR,"获取失败",null);
        }
    }

    @RequestMapping(value = "/myCoinsDetails",method = RequestMethod.GET)
    @ApiOperation(value = "得到我的金币明细")
    @RequiresAuthentication
    public ResponsePageInfoData<List<UserCoinDetailsModel>> getUserCoinDetails(@RequestParam(value = "pageNo",required = true,defaultValue = "1") int pageNo,
                                                                               @RequestParam(value = "rows",required = true,defaultValue = "30") int rows){
        Page<UserCoinDetailsModel> pageInfo = PageHelper.startPage(pageNo,rows);
        List<UserCoinDetailsModel> userCoinDetailsModels = this.userAssetServiceImpl.getUserCoinDetails();
        return new ResponsePageInfoData<>(ResponseDataCode.STATUS_NORMAL,"",userCoinDetailsModels,pageInfo.getTotal());
    }


}
