package com.zwdbj.server.adminserver.controller;

import com.zwdbj.server.utility.model.ResponseData;
import com.zwdbj.server.utility.model.ResponseDataCode;
import com.zwdbj.server.utility.model.ResponsePageInfoData;
import com.zwdbj.server.adminserver.model.user.UserToken;
import com.zwdbj.server.utility.model.ServiceStatusInfo;
import com.zwdbj.server.adminserver.service.youzan.model.AyouzanTradeCartAddParams;
import com.zwdbj.server.adminserver.service.youzan.model.YZItemDto;
import com.zwdbj.server.adminserver.service.youzan.model.YZSearchItemInput;
import com.zwdbj.server.adminserver.service.youzan.model.YZUserLoginToken;
import com.zwdbj.server.adminserver.service.youzan.service.YouZanService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/youzan")
@Api(description = "有赞商城相关api")
public class YouZanController {

    @Autowired
    YouZanService ssoService;

    @RequestMapping(value = "/sdkToken",method = RequestMethod.GET)
    @ApiOperation(value = "SDK校验Token")
    public ResponseData<UserToken> getSDKToken() {
        ServiceStatusInfo<String> serviceStatusInfo = this.ssoService.getToken();
        if (serviceStatusInfo.isSuccess()) {
            return new ResponseData<>(ResponseDataCode.STATUS_NORMAL,
                    serviceStatusInfo.getMsg(),
                    new UserToken(serviceStatusInfo.getData(),
                            7 * 24 *3600));
        } else {
            return  new ResponseData<>(ResponseDataCode.STATUS_ERROR,serviceStatusInfo.getMsg(),null);
        }
    }

    @RequestMapping(value = "/loginToken/{userId}",method = RequestMethod.GET)
    @RequiresAuthentication
    @ApiOperation(value = "有赞移动端免登陆Token，详细参见https://www.youzanyun.com/docs/guide/3400/3466")
    public ResponseData<YZUserLoginToken> userLoginToken(@PathVariable long userId) {
        ServiceStatusInfo<YZUserLoginToken> statusInfo = this.ssoService.getUserToken(userId);
        if(statusInfo.isSuccess()) {
            return new ResponseData<>(ResponseDataCode.STATUS_NORMAL,statusInfo.getMsg(),statusInfo.getData());
        } else {
            return new ResponseData<>(ResponseDataCode.STATUS_ERROR,statusInfo.getMsg(),null);
        }
    }
    @RequestMapping(value = "/logOut/{userId}",method = RequestMethod.GET)
    @RequiresAuthentication
    @ApiOperation(value = "有赞移动端退出商城，详细参见https://www.youzanyun.com/docs/guide/3400/3466")
    public ResponseData<Object> userLogOut(@PathVariable long userId) {
        ServiceStatusInfo<Integer> statusInfo = this.ssoService.logOut(userId);
        if (statusInfo.isSuccess()) {
            return new ResponseData<>(ResponseDataCode.STATUS_NORMAL,statusInfo.getMsg(),null);
        } else {
            return new ResponseData<>(ResponseDataCode.STATUS_ERROR,statusInfo.getMsg(),null);
        }
    }

    @RequestMapping(value = "/searchGoods",method = RequestMethod.POST)
    @RequiresAuthentication
    @ApiOperation(value = "搜索有赞商品")
    public ResponsePageInfoData<List<YZItemDto>> searchGoods(@RequestBody YZSearchItemInput input) {
        return this.ssoService.searchGoods(input);
    }

    @RequestMapping(value = "/cart/add/{userId}/{storeId}",method = RequestMethod.POST)
    @RequiresAuthentication
    @ApiOperation(value = "添加商品至购物车")
    public ResponseData<Integer> addCartGoods(@PathVariable("userId")Long userId,
                                           @PathVariable("storeId")Long storeId,
                                           @RequestBody AyouzanTradeCartAddParams cartAddParams){
        ServiceStatusInfo<Integer> statusInfo = this.ssoService.addCartGoods(userId,storeId,cartAddParams);
        if (statusInfo.isSuccess()) {
            return new ResponseData<>(ResponseDataCode.STATUS_NORMAL,statusInfo.getMsg(),null);
        } else {
            return new ResponseData<>(ResponseDataCode.STATUS_ERROR,statusInfo.getMsg(),null);
        }
    }



}
