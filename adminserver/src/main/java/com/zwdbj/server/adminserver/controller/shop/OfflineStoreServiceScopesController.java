package com.zwdbj.server.adminserver.controller.shop;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zwdbj.server.adminserver.service.shop.service.offlineStoreServiceScopes.model.OfflineStoreServiceScopes;
import com.zwdbj.server.adminserver.service.shop.service.offlineStoreServiceScopes.service.OfflineStoreServiceScopesService;
import com.zwdbj.server.basemodel.model.ResponseData;
import com.zwdbj.server.basemodel.model.ResponseDataCode;
import com.zwdbj.server.basemodel.model.ResponsePageInfoData;
import com.zwdbj.server.basemodel.model.ServiceStatusInfo;
import com.zwdbj.server.tokencenter.TokenCenterManager;
import com.zwdbj.server.utility.common.shiro.JWTUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/shop/offlineStoreServiceScopes/dbj")
@Api(description = "线下门店服务范围相关")
public class OfflineStoreServiceScopesController {

    @Autowired
    private OfflineStoreServiceScopesService offlineStoreServiceScopesServiceImpl;
    @Autowired
    private TokenCenterManager tokenCenterManager;

    @RequiresAuthentication
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ApiOperation(value = "添加线下门店服务范围")
    public ResponseData<Long> create(@RequestBody OfflineStoreServiceScopes offlineStoreServiceScopes) {
        long userId = JWTUtil.getCurrentId();
        long legalSubjectId = tokenCenterManager.fetchUser(String.valueOf(userId)).getData().getLegalSubjectId();
        ServiceStatusInfo<Long> serviceStatusInfo = offlineStoreServiceScopesServiceImpl.create(offlineStoreServiceScopes, legalSubjectId);
        if (serviceStatusInfo.isSuccess()) {
            return new ResponseData<>(ResponseDataCode.STATUS_NORMAL, "", serviceStatusInfo.getData());
        }
        return new ResponseData<>(ResponseDataCode.STATUS_ERROR, serviceStatusInfo.getMsg(), null);
    }

    @RequiresAuthentication
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ApiOperation(value = "修改线下门店服务范围")
    public ResponseData<Long> update(@RequestBody OfflineStoreServiceScopes offlineStoreServiceScopes) {
        long userId = JWTUtil.getCurrentId();
        long legalSubjectId = tokenCenterManager.fetchUser(String.valueOf(userId)).getData().getLegalSubjectId();
        ServiceStatusInfo<Long> serviceStatusInfo = offlineStoreServiceScopesServiceImpl.update(offlineStoreServiceScopes,legalSubjectId);
        if (serviceStatusInfo.isSuccess()) {
            return new ResponseData<>(ResponseDataCode.STATUS_NORMAL, "", serviceStatusInfo.getData());
        }
        return new ResponseData<>(ResponseDataCode.STATUS_ERROR, serviceStatusInfo.getMsg(), null);
    }

    @RequiresAuthentication
    @RequestMapping(value = "/delete/{serviceScopeId}", method = RequestMethod.GET)
    @ApiOperation(value = "删除线下门店服务范围")
    public ResponseData<Long> deleteById(@PathVariable("serviceScopeId") Long serviceScopeId) {
        long userId = JWTUtil.getCurrentId();
        long legalSubjectId = tokenCenterManager.fetchUser(String.valueOf(userId)).getData().getLegalSubjectId();

        ServiceStatusInfo<Long> serviceStatusInfo = offlineStoreServiceScopesServiceImpl.deleteById(serviceScopeId, legalSubjectId);
        if (serviceStatusInfo.isSuccess()) {
            return new ResponseData<>(ResponseDataCode.STATUS_NORMAL, "", serviceStatusInfo.getData());
        }
        return new ResponseData<>(ResponseDataCode.STATUS_ERROR, serviceStatusInfo.getMsg(), null);
    }

    @RequiresAuthentication
    @RequestMapping(value = "/select", method = RequestMethod.GET)
    @ApiOperation(value = "查询所有线下门店服务范围")
    public ResponsePageInfoData<List<OfflineStoreServiceScopes>> select(@RequestParam(value = "pageNo", defaultValue = "1", required = true) int pageNo,
                                                                        @RequestParam(value = "rows", defaultValue = "30", required = true) int rows) {
        PageHelper.startPage(pageNo, rows);
        List<OfflineStoreServiceScopes> list = offlineStoreServiceScopesServiceImpl.select().getData();
        PageInfo<OfflineStoreServiceScopes> pageInfo = new PageInfo<>(list);
        return new ResponsePageInfoData<>(ResponseDataCode.STATUS_NORMAL, "", list, pageInfo.getTotal());
    }

    @RequiresAuthentication
    @RequestMapping(value = "/select/{offlineStoreId}", method = RequestMethod.GET)
    @ApiOperation(value = "通过Id查询线下门店服务范围")
    public ResponseData<List<OfflineStoreServiceScopes>> selectById(@PathVariable("offlineStoreId") Long offlineStoreId) {

        ServiceStatusInfo<List<OfflineStoreServiceScopes>> serviceStatusInfo = offlineStoreServiceScopesServiceImpl.selectByofflineStoreId(offlineStoreId);
        if (serviceStatusInfo.isSuccess()) {
            return new ResponseData(ResponseDataCode.STATUS_NORMAL, "", serviceStatusInfo.getData());
        }
        return new ResponseData(ResponseDataCode.STATUS_ERROR, serviceStatusInfo.getMsg(), null);

    }
}


