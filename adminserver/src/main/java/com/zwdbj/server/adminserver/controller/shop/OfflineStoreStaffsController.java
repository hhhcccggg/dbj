package com.zwdbj.server.adminserver.controller.shop;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zwdbj.server.adminserver.service.shop.service.offlineStoreStaffs.model.*;
import com.zwdbj.server.adminserver.service.shop.service.offlineStoreStaffs.service.OfflineStoreStaffsService;
import com.zwdbj.server.usercommonservice.authuser.service.AuthUserManagerImpl;
import com.zwdbj.server.utility.common.shiro.JWTUtil;
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
@RequestMapping(value = "/api/shop/offlineStoreStaffs")
@Api(description = "门店员工，代言人相关")
public class OfflineStoreStaffsController {
    @Autowired
    private OfflineStoreStaffsService offlineStoreStaffsServiceImpl;
    @Autowired
    private AuthUserManagerImpl authUserManager;

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ApiOperation(value = "添加员工，代言人")
    public ResponseData<Long> create(@RequestBody StaffInput staffInput) {
        long userId = JWTUtil.getCurrentId();
        long legalSubjectId = authUserManager.get(String.valueOf(userId)).getLegalSubjectId();
        ServiceStatusInfo<Long> serviceStatusInfo = offlineStoreStaffsServiceImpl.create(staffInput, legalSubjectId);
        if (serviceStatusInfo.isSuccess()) {
            return new ResponseData<>(ResponseDataCode.STATUS_NORMAL, "", serviceStatusInfo.getData());
        }
        return new ResponseData<>(ResponseDataCode.STATUS_ERROR, serviceStatusInfo.getMsg(), null);


    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ApiOperation(value = "修改门店员工，代言人")
    public ResponseData<Long> update(@RequestBody ModifyStaff modifyStaff) {
        long userId = JWTUtil.getCurrentId();
        long legalSubjectId = authUserManager.get(String.valueOf(userId)).getLegalSubjectId();
        ServiceStatusInfo<Long> serviceStatusInfo = offlineStoreStaffsServiceImpl.update(modifyStaff, legalSubjectId);
        if (serviceStatusInfo.isSuccess()) {
            return new ResponseData<>(ResponseDataCode.STATUS_NORMAL, "", serviceStatusInfo.getData());
        }
        return new ResponseData<>(ResponseDataCode.STATUS_ERROR, serviceStatusInfo.getMsg(), null);
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "删除门店员工，代言人")
    public ResponseData<Long> deleteById(@PathVariable("id") long id, @RequestParam("isSuperStar") boolean isSuperStar) {
        long userId = JWTUtil.getCurrentId();
        long legalSubjectId = authUserManager.get(String.valueOf(userId)).getLegalSubjectId();

        ServiceStatusInfo<Long> serviceStatusInfo = offlineStoreStaffsServiceImpl.deleteById(id, legalSubjectId, isSuperStar);
        if (serviceStatusInfo.isSuccess()) {
            return new ResponseData<>(ResponseDataCode.STATUS_NORMAL, "", serviceStatusInfo.getData());
        }
        return new ResponseData<>(ResponseDataCode.STATUS_ERROR, serviceStatusInfo.getMsg(), null);
    }

    @RequestMapping(value = "/getStaffs", method = RequestMethod.GET)
    @ApiOperation(value = "获取员工，代言人")
    public ResponsePageInfoData<List<OfflineStoreStaffs>> getStaffs(@RequestParam(value = "pageNo", required = true, defaultValue = "1") int pageNo,
                                                                    @RequestParam(value = "rows", required = true, defaultValue = "30") int rows) {
        long userId = JWTUtil.getCurrentId();
        long legalSubjectId = authUserManager.get(String.valueOf(userId)).getLegalSubjectId();
        PageHelper.startPage(pageNo, rows);
        ServiceStatusInfo<List<OfflineStoreStaffs>> statusInfo = offlineStoreStaffsServiceImpl.getStaffs(legalSubjectId);
        PageInfo<OfflineStoreStaffs> pageInfo = new PageInfo<>(statusInfo.getData());
        return new ResponsePageInfoData<>(0, "", pageInfo.getList(), pageInfo.getTotal());
    }

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    @ApiOperation(value = "查询所有门店员工，代言人")
    public ResponsePageInfoData<List<OfflineStoreStaffs>> select(@RequestParam(value = "pageNo", defaultValue = "1", required = true) int pageNo,
                                                                 @RequestParam(value = "rows", defaultValue = "30", required = true) int rows,
                                                                 @RequestBody SearchStaffInfo searchStaffInfo) {
        long userId = JWTUtil.getCurrentId();
        long legalSubjectId = authUserManager.get(String.valueOf(userId)).getLegalSubjectId();
        PageHelper.startPage(pageNo, rows);
        List<OfflineStoreStaffs> list = offlineStoreStaffsServiceImpl.searchStaffs(searchStaffInfo, legalSubjectId).getData();
        PageInfo<OfflineStoreStaffs> pageInfo = new PageInfo<>(list);
        return new ResponsePageInfoData<>(ResponseDataCode.STATUS_NORMAL, "", pageInfo.getList(), pageInfo.getTotal());
    }

    @RequestMapping(value = "/bulkDeleteStaffs", method = RequestMethod.GET)
    @ApiOperation(value = "批量删除员工，代言人")
    public ResponseData<OfflineStoreStaffs> selectById(@RequestParam("userIds") long[] userIds,
                                                       @RequestParam("isSuperStar") boolean isSuperStar) {
        long userId = JWTUtil.getCurrentId();
        long legalSubjectId = authUserManager.get(String.valueOf(userId)).getLegalSubjectId();
        ServiceStatusInfo<Long> serviceStatusInfo = offlineStoreStaffsServiceImpl.bulkDeleteStaffs(userIds, legalSubjectId, isSuperStar);
        if (serviceStatusInfo.isSuccess()) {
            return new ResponseData(ResponseDataCode.STATUS_NORMAL, "", serviceStatusInfo.getData());
        }
        return new ResponseData(ResponseDataCode.STATUS_ERROR, serviceStatusInfo.getMsg(), null);

    }

    @RequestMapping(value = "/bulkSetSuperStar", method = RequestMethod.POST)
    @ApiOperation(value = "批量删除员工，代言人")
    public ResponseData<OfflineStoreStaffs> selectById(@RequestBody() IsSuperStar[] isSuperStars) {
        long userId = JWTUtil.getCurrentId();
        long legalSubjectId = authUserManager.get(String.valueOf(userId)).getLegalSubjectId();
        ServiceStatusInfo<Long> serviceStatusInfo = offlineStoreStaffsServiceImpl.bulkSetSuperStar(isSuperStars, legalSubjectId);
        if (serviceStatusInfo.isSuccess()) {
            return new ResponseData(ResponseDataCode.STATUS_NORMAL, "", serviceStatusInfo.getData());
        }
        return new ResponseData(ResponseDataCode.STATUS_ERROR, serviceStatusInfo.getMsg(), null);

    }
}
