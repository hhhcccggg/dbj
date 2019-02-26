package com.zwdbj.server.adminserver.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zwdbj.server.adminserver.identity.RoleIdentity;
import com.zwdbj.server.adminserver.service.enCashPayAccount.model.EnCashPayAccount;
import com.zwdbj.server.adminserver.service.enCashPayAccount.model.ModifyEnCashPayAccount;
import com.zwdbj.server.adminserver.service.enCashPayAccount.service.EnCashPayAccountService;
import com.zwdbj.server.utility.model.ResponseData;
import com.zwdbj.server.utility.model.ResponseDataCode;
import com.zwdbj.server.utility.model.ResponsePageInfoData;
import com.zwdbj.server.utility.model.ServiceStatusInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RequestMapping(value = "/api/enCashPayAccount/dbj")
@RestController
@Api(description = "提现第三方支付账号相关")
public class EnCashPayAccountController {
    @Resource
    private EnCashPayAccountService enCashPayAccountServiceImpl;

    @RequiresRoles(value = {RoleIdentity.ADMIN_ROLE, RoleIdentity.MARKET_ROLE}, logical = Logical.OR)
    @RequestMapping(value = "/search", method = RequestMethod.GET)
    @ApiOperation(value = "查询所有提现第三方支付账号")
    public ResponsePageInfoData<List<EnCashPayAccount>> search(@RequestParam(value = "pageNo", defaultValue = "1", required = true) int pageNo,
                                                               @RequestParam(value = "rows", defaultValue = "30", required = true) int rows) {
        PageHelper.startPage(pageNo, rows);
        ServiceStatusInfo<List<EnCashPayAccount>> statusInfo = this.enCashPayAccountServiceImpl.findAll();
        List<EnCashPayAccount> result = statusInfo.getData();
        PageInfo<EnCashPayAccount> pageInfo = new PageInfo<>(result);
        return new ResponsePageInfoData<>(ResponseDataCode.STATUS_NORMAL, statusInfo.getMsg(), result, pageInfo.getTotal());


    }

    @RequiresRoles(value = {RoleIdentity.ADMIN_ROLE, RoleIdentity.MARKET_ROLE}, logical = Logical.OR)
    @RequestMapping(value = "/search/{userId}", method = RequestMethod.GET)
    @ApiOperation(value = "通过用户id查询提现第三方支付账号")
    public ResponseData<EnCashPayAccount> searchByUserId(@PathVariable("userId") Long userId) {
        ServiceStatusInfo<EnCashPayAccount> statusInfo = this.enCashPayAccountServiceImpl.findByUserId(userId);
        if (statusInfo.isSuccess()) {
            return new ResponseData<>(ResponseDataCode.STATUS_NORMAL, "", statusInfo.getData());

        }
        return new ResponseData<>(ResponseDataCode.STATUS_ERROR, statusInfo.getMsg(), null);


    }

    @RequiresRoles(value = {RoleIdentity.ADMIN_ROLE, RoleIdentity.MARKET_ROLE}, logical = Logical.OR)
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ApiOperation(value = "创建提现第三方支付账号")
    public ResponseData<Long> create(@RequestBody EnCashPayAccount enCashPayAccount) {
        ServiceStatusInfo<Long> statusInfo = this.enCashPayAccountServiceImpl.create(enCashPayAccount);
        if (statusInfo.isSuccess()) {


            return new ResponseData<>(ResponseDataCode.STATUS_NORMAL, "", statusInfo.getData());
        }
        return new ResponseData<>(ResponseDataCode.STATUS_ERROR, statusInfo.getMsg(), null);


    }

    @RequiresRoles(value = {RoleIdentity.ADMIN_ROLE, RoleIdentity.MARKET_ROLE}, logical = Logical.OR)
    @RequestMapping(value = "/delete/{userId}", method = RequestMethod.GET)
    @ApiOperation(value = "通过用户id删除提现第三方支付账号")
    public ResponseData<Long> deleteByUserId(@PathVariable("userId") Long userId) {
        ServiceStatusInfo<Long> statusInfo = this.enCashPayAccountServiceImpl.deleteByUserId(userId);
        if (statusInfo.isSuccess()) {

            return new ResponseData<>(ResponseDataCode.STATUS_NORMAL, "", statusInfo.getData());
        }
        return new ResponseData<>(ResponseDataCode.STATUS_ERROR, statusInfo.getMsg(), null);
    }

    @RequiresRoles(value = {RoleIdentity.ADMIN_ROLE, RoleIdentity.MARKET_ROLE}, logical = Logical.OR)
    @RequestMapping(value = "/update/{userId}", method = RequestMethod.POST)
    @ApiOperation(value = "修改提现第三方支付账号")
    public ResponseData<Long> update(@PathVariable("userId") Long userId, @RequestBody ModifyEnCashPayAccount enCashPayAccount) {
        ServiceStatusInfo<Long> statusInfo = this.enCashPayAccountServiceImpl.update(userId, enCashPayAccount);
        if (statusInfo.isSuccess()) {
            return new ResponseData<>(ResponseDataCode.STATUS_NORMAL, "", statusInfo.getData());
        }
        return new ResponseData<>(ResponseDataCode.STATUS_ERROR, statusInfo.getMsg(), null);

    }
}
