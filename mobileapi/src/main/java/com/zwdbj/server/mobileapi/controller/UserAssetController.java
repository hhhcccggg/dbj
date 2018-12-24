package com.zwdbj.server.mobileapi.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zwdbj.server.mobileapi.service.userAssets.model.*;
import com.zwdbj.server.mobileapi.service.userAssets.service.UserAssetServiceImpl;
import com.zwdbj.server.utility.common.shiro.JWTUtil;
import com.zwdbj.server.utility.model.ResponseData;
import com.zwdbj.server.utility.model.ResponseDataCode;
import com.zwdbj.server.utility.model.ResponsePageInfoData;
import com.zwdbj.server.utility.model.ServiceStatusInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.jws.Oneway;
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
    public ResponseData<Long> getCoinsByUserId(){
        ServiceStatusInfo<Long> info = this.userAssetServiceImpl.getCoinsByUserId();
        if (info.isSuccess()){
            return new ResponseData<>(ResponseDataCode.STATUS_NORMAL,"",info.getData());
        }else {
            return new ResponseData<>(ResponseDataCode.STATUS_ERROR,"获取失败",null);
        }
    }
    @RequestMapping(value = "/myCoinsByType",method = RequestMethod.GET)
    @ApiOperation(value = "根据金币分类得到我的金币资产")
    @RequiresAuthentication
    public ResponseData<UserCoinTypeModel> getUserCoinType(@RequestParam String type){
        ServiceStatusInfo<UserCoinTypeModel> info =  this.userAssetServiceImpl.getUserCoinType(type);
        if (info.isSuccess()){
            return new ResponseData<>(ResponseDataCode.STATUS_NORMAL,"",info.getData());
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

    @RequiresAuthentication
    @RequestMapping(value = "/search/buyCoinConfig", method = RequestMethod.GET)
    @ApiOperation(value = "查询可选充值金币配置列表")
    public ResponsePageInfoData<List<BuyCoinConfigModel>> findAllBuyCoinConfigs(@RequestParam String type,
                                                                                @RequestParam(value = "pageNo", defaultValue = "1", required = true) int pageNo,
                                                                                @RequestParam(value = "rows", defaultValue = "30", required = true) int rows) {
        Page<BuyCoinConfigModel> pageInfo = PageHelper.startPage(pageNo,rows);
        List<BuyCoinConfigModel> result = this.userAssetServiceImpl.findAllBuyCoinConfigs();
        return new ResponsePageInfoData<>(ResponseDataCode.STATUS_NORMAL, "", result, pageInfo.getTotal());
    }

    @RequiresAuthentication
    @RequestMapping(value = "/bindAliAccount",method = RequestMethod.POST)
    @ApiOperation("绑定支付宝账号")
    public ResponseData<Object> bindAliAccount(@RequestBody AliAccountBindInput input) {
        ServiceStatusInfo<Object> serviceStatusInfo = this.userAssetServiceImpl.bindAliAccount(input,JWTUtil.getCurrentId());
        if (serviceStatusInfo.isSuccess()) {
            return new ResponseData<>(ResponseDataCode.STATUS_NORMAL,"OK",null);
        }
        return new ResponseData<>(ResponseDataCode.STATUS_ERROR,serviceStatusInfo.getMsg(),null);
    }

    @RequiresAuthentication
    @RequestMapping(value = "/banding/third", method = RequestMethod.POST)
    @ApiOperation(value = "提现:绑定第三方平台")
    public ResponseData<Integer> bandingThird(@RequestBody BandingThirdInput input){
        long userId = JWTUtil.getCurrentId();
        ServiceStatusInfo<Integer> info = this.userAssetServiceImpl.bandingThird(userId,input);
        if (info.isSuccess()){
            return new ResponseData<>(ResponseDataCode.STATUS_NORMAL,"",info.getData());
        }else {
            return new ResponseData<>(ResponseDataCode.STATUS_ERROR,"绑定失败",null);
        }
    }

    @RequiresAuthentication
    @RequestMapping(value = "/unBanding/third/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "提现:解绑第三方平台，id为提现第三方支付账号的id")
    public ResponseData<Integer> unBandingThird(@PathVariable long id){
        ServiceStatusInfo<Integer> info = this.userAssetServiceImpl.unBandingThird(id);
        if (info.isSuccess()){
            return new ResponseData<>(ResponseDataCode.STATUS_NORMAL,"",info.getData());
        }else {
            return new ResponseData<>(ResponseDataCode.STATUS_ERROR,"解绑失败",null);
        }
    }

    @RequestMapping(value = "/search/thirds", method = RequestMethod.GET)
    @ApiOperation(value = "提现:获取提现的第三方账户")
    public ResponsePageInfoData<List<EnCashAccountModel>> getMyEnCashAccounts(@RequestParam(value = "pageNo", defaultValue = "1", required = true) int pageNo,
                                                                              @RequestParam(value = "rows", defaultValue = "30", required = true) int rows){
        Page<EnCashAccountModel> pageInfo = PageHelper.startPage(pageNo,rows);
        List<EnCashAccountModel> models = this.userAssetServiceImpl.getMyEnCashAccounts();
        return new ResponsePageInfoData<>(ResponseDataCode.STATUS_NORMAL, "", models, pageInfo.getTotal());
    }

    @RequiresAuthentication
    @RequestMapping(value = "/enCash", method = RequestMethod.POST)
    @ApiOperation(value = "提现")
    public ResponseData<Integer> enCashMyCoins(@RequestBody EnCashInput input){
        ServiceStatusInfo<Integer> info = this.userAssetServiceImpl.enCashMyCoins(input);
        if (info.isSuccess()){
            return new ResponseData<>(ResponseDataCode.STATUS_NORMAL,"",info.getData());
        }else {
            return new ResponseData<>(ResponseDataCode.STATUS_ERROR,"提现失败",null);
        }

    }





}
