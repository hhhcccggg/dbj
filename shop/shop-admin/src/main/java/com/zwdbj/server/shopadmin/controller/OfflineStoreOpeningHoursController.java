package com.zwdbj.server.shopadmin.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zwdbj.server.shop_admin_service.offlineStoreOpeningHour.model.OfflineStoreOpeningHours;
import com.zwdbj.server.shop_admin_service.offlineStoreOpeningHour.service.OfflineStoreOpeningHoursService;
import com.zwdbj.server.utility.model.ResponseData;
import com.zwdbj.server.utility.model.ResponseDataCode;
import com.zwdbj.server.utility.model.ResponsePageInfoData;
import com.zwdbj.server.utility.model.ServiceStatusInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(value = "/api/offlineStoreOpeningHours/dbj")
@RestController
@Api(description = "营业时间相关")
public class OfflineStoreOpeningHoursController {

    @Autowired
    private OfflineStoreOpeningHoursService offlineStoreOpeningHoursServiceImpl;

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ApiOperation(value = "添加营业时间")
    public ResponseData<Long> create(@RequestBody OfflineStoreOpeningHours offlineStoreOpeningHours) {
        ServiceStatusInfo<Long> serviceStatusInfo = offlineStoreOpeningHoursServiceImpl.create(offlineStoreOpeningHours);
        if (serviceStatusInfo.isSuccess()) {
            return new ResponseData<>(ResponseDataCode.STATUS_NORMAL, "", serviceStatusInfo.getData());
        }
        return new ResponseData<>(ResponseDataCode.STATUS_ERROR, serviceStatusInfo.getMsg(), null);
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "删除营业时间")
    public ResponseData<Long> deleteById(@PathVariable("id") Long id) {
        ServiceStatusInfo<Long> serviceStatusInfo = offlineStoreOpeningHoursServiceImpl.deleteById(id);
        if (serviceStatusInfo.isSuccess()) {
            return new ResponseData<>(ResponseDataCode.STATUS_NORMAL, "", serviceStatusInfo.getData());
        }
        return new ResponseData<>(ResponseDataCode.STATUS_ERROR, serviceStatusInfo.getMsg(), null);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ApiOperation(value = "修改营业时间")
    public ResponseData<Long> update(@RequestBody OfflineStoreOpeningHours offlineStoreOpeningHours) {
        ServiceStatusInfo<Long> serviceStatusInfo = offlineStoreOpeningHoursServiceImpl.update(offlineStoreOpeningHours);
        if (serviceStatusInfo.isSuccess()) {
            return new ResponseData<>(ResponseDataCode.STATUS_NORMAL, "", serviceStatusInfo.getData());
        }
        return new ResponseData<>(ResponseDataCode.STATUS_NORMAL, serviceStatusInfo.getMsg(), null);
    }

    @RequestMapping(value = "/select/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "通过id查询营业时间")
    public ResponseData<OfflineStoreOpeningHours> selectById(@PathVariable("id") Long id) {
        ServiceStatusInfo<OfflineStoreOpeningHours> serviceStatusInfo = offlineStoreOpeningHoursServiceImpl.selectById(id);
        if (serviceStatusInfo.isSuccess()) {
            return new ResponseData<>(ResponseDataCode.STATUS_NORMAL, "", serviceStatusInfo.getData());
        }
        return new ResponseData<>(ResponseDataCode.STATUS_NORMAL, serviceStatusInfo.getMsg(), null);
    }

    @RequestMapping(value = "/select", method = RequestMethod.GET)
    @ApiOperation(value = "查询所有营业时间")
    public ResponsePageInfoData<List<OfflineStoreOpeningHours>> select(@RequestParam(value = "pageNo", defaultValue = "1", required = true) int pageNo,
                                                                       @RequestParam(value = "rows", defaultValue = "30", required = true) int rows) {

        PageHelper.startPage(pageNo, rows);
        List<OfflineStoreOpeningHours> list =offlineStoreOpeningHoursServiceImpl.select().getData();
        PageInfo<OfflineStoreOpeningHours> pageInfo = new PageInfo<>(list);

        return new ResponsePageInfoData<>(ResponseDataCode.STATUS_NORMAL, "", list, pageInfo.getTotal());

    }
}
