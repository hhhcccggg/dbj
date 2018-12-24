package com.zwdbj.server.adminserver.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zwdbj.server.adminserver.identity.RoleIdentity;
import com.zwdbj.server.adminserver.service.BuyCoinConfig.model.BuyCoinConfig;
import com.zwdbj.server.adminserver.service.BuyCoinConfig.model.BuyCoinConfigAdd;
import com.zwdbj.server.adminserver.service.BuyCoinConfig.service.BuyCoinConfigService;
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

@RestController
@RequestMapping(value = "/api/buyCoinConfig/dbj")
@Api(description = "可选充值金币配置列表相关")
public class BuyCoinConfigController {
    @Resource
    private BuyCoinConfigService buyCoinConfigServiceImpl;

    @RequestMapping(value = "/search/{type}", method = RequestMethod.GET)
    @ApiOperation(value = "通过平台类型查询可选充值金币配置列表")
    @RequiresRoles(value = {RoleIdentity.ADMIN_ROLE,RoleIdentity.MARKET_ROLE},logical = Logical.OR)
    public ResponsePageInfoData<List<BuyCoinConfig>> search(@RequestParam(value = "pageNo", defaultValue = "1", required = true) int pageNo,
                                                            @RequestParam(value = "rows", defaultValue = "30", required = true) int rows,
                                                            @PathVariable(value = "type") String type) {
        PageHelper.startPage(pageNo, rows);
        List<BuyCoinConfig> result = this.buyCoinConfigServiceImpl.searchByType(type).getData();
        PageInfo pageInfo = new PageInfo(result);
        return new ResponsePageInfoData<>(ResponseDataCode.STATUS_NORMAL, "", result, pageInfo.getTotal());
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    @ApiOperation(value = "查询所有可选充值金币配置列表")
    @RequiresRoles(value = {RoleIdentity.ADMIN_ROLE,RoleIdentity.MARKET_ROLE},logical = Logical.OR)
    public ResponseData<List<BuyCoinConfig>> search() {
        ServiceStatusInfo<List<BuyCoinConfig>> serviceStatusInfo = this.buyCoinConfigServiceImpl.searchAll();
        if (serviceStatusInfo.isSuccess()) {
            return new ResponseData<>(ResponseDataCode.STATUS_NORMAL, "", serviceStatusInfo.getData());
        }
        return new ResponseData<>(ResponseDataCode.STATUS_ERROR, serviceStatusInfo.getMsg(), null);
    }

    @RequestMapping(value = "create", method = RequestMethod.POST)
    @ApiOperation(value = "创建可选充值金币配置列表")
    @RequiresRoles(value = {RoleIdentity.ADMIN_ROLE,RoleIdentity.MARKET_ROLE},logical = Logical.OR)
    public ResponseData<Long> create(@RequestBody BuyCoinConfigAdd buyCoinConfig) {
        ServiceStatusInfo<Long> serviceStatusInfo = this.buyCoinConfigServiceImpl.create(buyCoinConfig);
        if (serviceStatusInfo.isSuccess()) {
            return new ResponseData<>(ResponseDataCode.STATUS_NORMAL, "", serviceStatusInfo.getData());
        }
        return new ResponseData<>(ResponseDataCode.STATUS_ERROR, serviceStatusInfo.getMsg(), null);
    }

    @RequestMapping(value = "delete/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "删除可选充值金币配置列表")
    @RequiresRoles(value = {RoleIdentity.ADMIN_ROLE,RoleIdentity.MARKET_ROLE},logical = Logical.OR)
    public ResponseData<Long> delete(@PathVariable("id") Long id) {
        ServiceStatusInfo<Long> serviceStatusInfo = this.buyCoinConfigServiceImpl.deleteById(id);
        if (serviceStatusInfo.isSuccess()) {
            return new ResponseData<>(ResponseDataCode.STATUS_NORMAL, "", serviceStatusInfo.getData());
        }
        return new ResponseData<>(ResponseDataCode.STATUS_ERROR, serviceStatusInfo.getMsg(), null);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ApiOperation(value = "修改可选充值金币配置列表")
    @RequiresRoles(value = {RoleIdentity.ADMIN_ROLE,RoleIdentity.MARKET_ROLE},logical = Logical.OR)
    public ResponseData<Long> update(@RequestBody BuyCoinConfig buyCoinConfig) {
        ServiceStatusInfo<Long> serviceStatusInfo = this.buyCoinConfigServiceImpl.update(buyCoinConfig);
        if (serviceStatusInfo.isSuccess()) {
            return new ResponseData<>(ResponseDataCode.STATUS_NORMAL, "", serviceStatusInfo.getData());
        }
        return new ResponseData<>(ResponseDataCode.STATUS_ERROR, serviceStatusInfo.getMsg(), null);
    }

}

