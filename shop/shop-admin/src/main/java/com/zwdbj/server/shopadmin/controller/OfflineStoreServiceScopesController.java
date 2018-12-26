package com.zwdbj.server.shopadmin.controller;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zwdbj.server.shop_admin_service.offlineStoreServiceScopes.model.OfflineStoreServiceScopes;
import com.zwdbj.server.shop_admin_service.offlineStoreServiceScopes.service.OfflineStoreServiceScopesService;
import com.zwdbj.server.utility.model.ResponseData;
import com.zwdbj.server.utility.model.ResponseDataCode;
import com.zwdbj.server.utility.model.ResponsePageInfoData;
import com.zwdbj.server.utility.model.ServiceStatusInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/offlineStoreServiceScopes/dbj")
@Api(description = "线下门店服务范围相关")
public class OfflineStoreServiceScopesController {

    @Autowired
    private OfflineStoreServiceScopesService offlineStoreServiceScopesServiceImpl;

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ApiOperation(value = "添加线下门店服务范围")
    public ResponseData<Long> create(@RequestBody OfflineStoreServiceScopes offlineStoreServiceScopes) {
        ServiceStatusInfo<Long> serviceStatusInfo = offlineStoreServiceScopesServiceImpl.create(offlineStoreServiceScopes);
        if (serviceStatusInfo.isSuccess()) {
            return new ResponseData<>(ResponseDataCode.STATUS_NORMAL, "", serviceStatusInfo.getData());
        }
        return new ResponseData<>(ResponseDataCode.STATUS_ERROR, serviceStatusInfo.getMsg(), null);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ApiOperation(value = "修改线下门店服务范围")
    public ResponseData<Long> update(@RequestBody OfflineStoreServiceScopes offlineStoreServiceScopes) {
        ServiceStatusInfo<Long> serviceStatusInfo = offlineStoreServiceScopesServiceImpl.update(offlineStoreServiceScopes);
        if (serviceStatusInfo.isSuccess()) {
            return new ResponseData<>(ResponseDataCode.STATUS_NORMAL, "", serviceStatusInfo.getData());
        }
        return new ResponseData<>(ResponseDataCode.STATUS_ERROR, serviceStatusInfo.getMsg(), null);
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "删除线下门店服务范围")
    public ResponseData<Long> deleteById(@PathVariable("id") Long id) {
        ServiceStatusInfo<Long> serviceStatusInfo = offlineStoreServiceScopesServiceImpl.deleteById(id);
        if (serviceStatusInfo.isSuccess()) {
            return new ResponseData<>(ResponseDataCode.STATUS_NORMAL, "", serviceStatusInfo.getData());
        }
        return new ResponseData<>(ResponseDataCode.STATUS_ERROR, serviceStatusInfo.getMsg(), null);
    }

    @RequestMapping(value = "/select", method = RequestMethod.GET)
    @ApiOperation(value = "查询所有线下门店服务范围")
    public ResponsePageInfoData<List<OfflineStoreServiceScopes>> select(@RequestParam(value = "pageNo", defaultValue = "1", required = true) int pageNo,
                                                                        @RequestParam(value = "rows", defaultValue = "30", required = true) int rows) {
        PageHelper.startPage(pageNo, rows);
        List<OfflineStoreServiceScopes> list = offlineStoreServiceScopesServiceImpl.select().getData();
        PageInfo<OfflineStoreServiceScopes> pageInfo = new PageInfo<>(list);
        return new ResponsePageInfoData<>(ResponseDataCode.STATUS_NORMAL, "", list, pageInfo.getTotal());
    }

    @RequestMapping(value = "/select/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "通过Id查询线下门店服务范围")
    public ResponseData<OfflineStoreServiceScopes> selectById(@PathVariable("id") Long id) {

        ServiceStatusInfo<OfflineStoreServiceScopes> serviceStatusInfo = offlineStoreServiceScopesServiceImpl.selectById(id);
        if (serviceStatusInfo.isSuccess()) {
            return new ResponseData(ResponseDataCode.STATUS_NORMAL, "", serviceStatusInfo.getData());
        }
        return new ResponseData(ResponseDataCode.STATUS_ERROR, serviceStatusInfo.getMsg(), null);

    }
}


