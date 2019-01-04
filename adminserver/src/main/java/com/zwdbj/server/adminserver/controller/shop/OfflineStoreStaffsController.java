package com.zwdbj.server.adminserver.controller.shop;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zwdbj.server.shop_admin_service.service.offlineStoreStaffs.model.OfflineStoreStaffs;
import com.zwdbj.server.shop_admin_service.service.offlineStoreStaffs.service.OfflineStoreStaffsService;
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
@RequestMapping(value = "/api/shop/offlineStoreStaffs/dbj")
@Api(description = "门店员工，代言人相关")
public class OfflineStoreStaffsController {
    @Autowired
    private OfflineStoreStaffsService offlineStoreStaffsServiceImpl;

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ApiOperation(value = "添加员工，代言人")
    public ResponseData<Long> create(@RequestBody OfflineStoreStaffs offlineStoreStaffs) {
        ServiceStatusInfo<Long> serviceStatusInfo = offlineStoreStaffsServiceImpl.create(offlineStoreStaffs);
        if (serviceStatusInfo.isSuccess()) {
            return new ResponseData<>(ResponseDataCode.STATUS_NORMAL, "", serviceStatusInfo.getData());
        }
        return new ResponseData<>(ResponseDataCode.STATUS_ERROR, serviceStatusInfo.getMsg(), null);


    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ApiOperation(value = "修改门店员工，代言人")
    public ResponseData<Long> update(@RequestBody OfflineStoreStaffs offlineStoreStaffs) {
        ServiceStatusInfo<Long> serviceStatusInfo = offlineStoreStaffsServiceImpl.update(offlineStoreStaffs);
        if (serviceStatusInfo.isSuccess()) {
            return new ResponseData<>(ResponseDataCode.STATUS_NORMAL, "", serviceStatusInfo.getData());
        }
        return new ResponseData<>(ResponseDataCode.STATUS_ERROR, serviceStatusInfo.getMsg(), null);
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "删除门店员工，代言人")
    public ResponseData<Long> deleteById(@PathVariable("id") Long id) {

        ServiceStatusInfo<Long> serviceStatusInfo = offlineStoreStaffsServiceImpl.deleteById(id);
        if (serviceStatusInfo.isSuccess()) {
            return new ResponseData<>(ResponseDataCode.STATUS_NORMAL, "", serviceStatusInfo.getData());
        }
        return new ResponseData<>(ResponseDataCode.STATUS_ERROR, serviceStatusInfo.getMsg(), null);
    }

    @RequestMapping(value = "/select", method = RequestMethod.GET)
    @ApiOperation(value = "查询所有门店员工，代言人")
    public ResponsePageInfoData<List<OfflineStoreStaffs>> select(@RequestParam(value = "pageNo", defaultValue = "1", required = true) int pageNo,
                                                                 @RequestParam(value = "rows", defaultValue = "30", required = true) int rows) {
        PageHelper.startPage(pageNo, rows);
        List<OfflineStoreStaffs> list = offlineStoreStaffsServiceImpl.select().getData();
        PageInfo<OfflineStoreStaffs> pageInfo = new PageInfo<>(list);
        return new ResponsePageInfoData<>(ResponseDataCode.STATUS_NORMAL, "", list, pageInfo.getTotal());
    }

    @RequestMapping(value = "/select/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "通过Id查询线下门店服务范围")
    public ResponseData<OfflineStoreStaffs> selectById(@PathVariable("id") Long id) {

        ServiceStatusInfo<OfflineStoreStaffs> serviceStatusInfo = offlineStoreStaffsServiceImpl.selectById(id);
        if (serviceStatusInfo.isSuccess()) {
            return new ResponseData(ResponseDataCode.STATUS_NORMAL, "", serviceStatusInfo.getData());
        }
        return new ResponseData(ResponseDataCode.STATUS_ERROR, serviceStatusInfo.getMsg(), null);

    }
}
