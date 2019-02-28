package com.zwdbj.server.adminserver.controller.shop;

import com.zwdbj.server.adminserver.service.shop.service.accountInfo.service.AccountInfoService;
import com.zwdbj.server.adminserver.service.user.model.AdModifyManagerPasswordInput;
import com.zwdbj.server.adminserver.service.user.model.AdNewlyPwdInput;
import com.zwdbj.server.basemodel.model.ResponseData;
import com.zwdbj.server.basemodel.model.ResponseDataCode;
import com.zwdbj.server.basemodel.model.ServiceStatusInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RequestMapping("/api/shop/account/dbj")
@RestController
@Api(value = "账户信息相关")
public class AccountInController {
    @Autowired
    private AccountInfoService accountInfoServiceImpl;

    @RequestMapping(value = "/sendPhoneCode/{phone}", method = RequestMethod.GET)
    @ApiOperation("/发送手机验证码")
    public ResponseData<String> sendPhoneCode(@PathVariable("phone") String phone) {
        ServiceStatusInfo<String> statusInfo = accountInfoServiceImpl.sendPhoneCode(phone);
        if (statusInfo.isSuccess()) {
            return new ResponseData<>(0, "", statusInfo.getData());
        }
        return new ResponseData<>(1, statusInfo.getMsg(), null);
    }

    @RequestMapping(value = "/checkPhoneCode", method = RequestMethod.GET)
    @ApiOperation("验证手机验证码")
    public ResponseData<Object> checkPhoneCode(@RequestParam("phone") String phone, @RequestParam("code") String code) {

        ServiceStatusInfo<Object> statusInfo = accountInfoServiceImpl.checkPhoneCode(phone, code);
        if (statusInfo.isSuccess()) {
            return new ResponseData<>(0, "", statusInfo.getData());
        }
        return new ResponseData<>(1, statusInfo.getMsg(), null);
    }
    @RequiresAuthentication
    @RequestMapping(value = "/newlyPwd", method = RequestMethod.POST)
    @ApiOperation("账号管理-账号密码修改")
    public ResponseData<Long> newlyPwdAd(@RequestBody AdNewlyPwdInput input) {
        ServiceStatusInfo<Long> statusInfo = this.accountInfoServiceImpl.newlyPwdAd(input);
        if (statusInfo.isSuccess()) {
            return new ResponseData<>(ResponseDataCode.STATUS_NORMAL, "", statusInfo.getData());
        }
        return new ResponseData<>(ResponseDataCode.STATUS_ERROR, statusInfo.getMsg(), null);
    }

    @RequiresAuthentication
    @RequestMapping(value = "/modifyPwd/{id}", method = RequestMethod.POST)
    @ApiOperation("账号管理-账号密码修改")
    public ResponseData<Long> modifyPwdAd(@PathVariable Long id,
                                          @RequestBody AdModifyManagerPasswordInput input) {
        ServiceStatusInfo<Long> statusInfo = this.accountInfoServiceImpl.modifyPwdAd(id, input);
        if (statusInfo.isSuccess()) {
            return new ResponseData<>(ResponseDataCode.STATUS_NORMAL, "", statusInfo.getData());
        }
        return new ResponseData<>(ResponseDataCode.STATUS_ERROR, statusInfo.getMsg(), null);
    }

}
