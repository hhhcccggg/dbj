package com.zwdbj.server.adminserver.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zwdbj.server.adminserver.identity.RoleIdentity;
import com.zwdbj.server.adminserver.service.userTenant.model.*;
import com.zwdbj.server.adminserver.service.userTenant.service.UserTenantService;
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

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/tenant")
@Api(description = "商户相关")
public class UserTenantController {
    @Autowired
    UserTenantService userTenantService;


    @RequiresAuthentication
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    @ApiOperation(value = "查询租户")
    @RequiresRoles(value = {RoleIdentity.ADMIN_ROLE, RoleIdentity.MARKET_ROLE}, logical = Logical.OR)
    public ResponsePageInfoData<List<UserTenantModel>> getUserTenants(@RequestBody UserTenantSearchInput input,
                                                                      @RequestParam(value = "pageNo", required = true, defaultValue = "1") int pageNo,
                                                                      @RequestParam(value = "rows", required = true, defaultValue = "30") int rows) {
        Page<UserTenantModel> pageInfo = PageHelper.startPage(pageNo, rows);
        List<UserTenantModel> userTenantModels = this.userTenantService.getUserTenants(input);
        return new ResponsePageInfoData<>(ResponseDataCode.STATUS_NORMAL, "", userTenantModels, pageInfo.getTotal());
    }

    @RequiresAuthentication
    @RequestMapping(value = "/search/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "根据租户id查询租户详细信息")
    @RequiresRoles(value = {RoleIdentity.ADMIN_ROLE, RoleIdentity.MARKET_ROLE}, logical = Logical.OR)
    public ResponseData<TenantDetailModel> getDetailTenantById(@PathVariable long id) {
        ServiceStatusInfo<TenantDetailModel> statusInfo = this.userTenantService.getDetailTenantById(id);
        if (statusInfo.isSuccess()) {
            return new ResponseData<>(ResponseDataCode.STATUS_NORMAL, "租户查询成功", statusInfo.getData());
        }
        return new ResponseData<>(ResponseDataCode.STATUS_ERROR, statusInfo.getMsg(), null);
    }

    @RequiresAuthentication
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ApiOperation(value = "添加租户")
    @RequiresRoles(value = {RoleIdentity.ADMIN_ROLE, RoleIdentity.MARKET_ROLE}, logical = Logical.OR)
    public ResponseData<Integer> addUserTenant(@RequestBody @Valid UserTenantInput input) {
        ServiceStatusInfo<Integer>statusInfo = this.userTenantService.addUserTenant(input);
        if (statusInfo.isSuccess()) {
            return new ResponseData<>(ResponseDataCode.STATUS_NORMAL, "租户添加成功", 1);
        }
        return new ResponseData<>(ResponseDataCode.STATUS_ERROR, statusInfo.getMsg(), null);
    }
    @RequiresAuthentication
    @RequestMapping(value = "/modify/{id}", method = RequestMethod.POST)
    @ApiOperation(value = "修改租户信息")
    @RequiresRoles(value = {RoleIdentity.ADMIN_ROLE, RoleIdentity.MARKET_ROLE}, logical = Logical.OR)
    public ResponseData<Integer> modifyUserTenant(@PathVariable long id, @RequestBody @Valid ModifyUserTenantInput input) {
        ServiceStatusInfo<Integer>statusInfo = this.userTenantService.modifyUserTenant(id,input);
        if (statusInfo.isSuccess()) {
            return new ResponseData<>(ResponseDataCode.STATUS_NORMAL, "租户修改成功", 1);
        }
        return new ResponseData<>(ResponseDataCode.STATUS_ERROR, statusInfo.getMsg(), null);
    }

    @RequiresAuthentication
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "删除租户信息")
    @RequiresRoles(value = {RoleIdentity.ADMIN_ROLE, RoleIdentity.MARKET_ROLE}, logical = Logical.OR)
    public ResponseData<Integer> deleteUserTenant(@PathVariable long id) {
        ServiceStatusInfo<Integer>statusInfo = this.userTenantService.deleteUserTenant(id);
        if (statusInfo.isSuccess()) {
            return new ResponseData<>(ResponseDataCode.STATUS_NORMAL, "租户删除成功", 1);
        }
        return new ResponseData<>(ResponseDataCode.STATUS_ERROR, statusInfo.getMsg(), null);
    }
}
