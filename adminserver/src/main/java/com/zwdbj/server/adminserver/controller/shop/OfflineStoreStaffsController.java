package com.zwdbj.server.adminserver.controller.shop;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zwdbj.server.adminserver.service.shop.service.offlineStoreStaffs.model.*;
import com.zwdbj.server.adminserver.service.shop.service.offlineStoreStaffs.service.OfflineStoreStaffsService;
import com.zwdbj.server.adminserver.service.shop.service.store.service.StoreService;
import com.zwdbj.server.usercommonservice.authuser.service.AuthUserManagerImpl;
import com.zwdbj.server.utility.common.shiro.JWTUtil;
import com.zwdbj.server.basemodel.model.ResponseData;
import com.zwdbj.server.basemodel.model.ResponseDataCode;
import com.zwdbj.server.basemodel.model.ResponsePageInfoData;
import com.zwdbj.server.basemodel.model.ServiceStatusInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
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
    private StoreService storeServiceImpl;
    @Autowired
    private AuthUserManagerImpl authUserManager;

    @RequiresAuthentication
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ApiOperation(value = "添加员工，代言人")
    public ResponseData<Long> create(@RequestBody StaffInput staffInput) {
        long userId = JWTUtil.getCurrentId();
        long legalSubjectId = authUserManager.get(String.valueOf(userId)).getLegalSubjectId();
        ServiceStatusInfo<Long> serviceStatusInfo = offlineStoreStaffsServiceImpl.create(staffInput, legalSubjectId);
        if (serviceStatusInfo.isSuccess()) {
            return new ResponseData<>(ResponseDataCode.STATUS_NORMAL, serviceStatusInfo.getMsg(), serviceStatusInfo.getData());
        }
        return new ResponseData<>(ResponseDataCode.STATUS_ERROR, serviceStatusInfo.getMsg(), null);


    }

    @RequiresAuthentication
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ApiOperation(value = "修改门店员工，代言人")
    public ResponseData<Long> update(@RequestBody ModifyStaff modifyStaff) {
        ServiceStatusInfo<Long> serviceStatusInfo = offlineStoreStaffsServiceImpl.update(modifyStaff);
        if (serviceStatusInfo.isSuccess()) {
            return new ResponseData<>(ResponseDataCode.STATUS_NORMAL, serviceStatusInfo.getMsg(), serviceStatusInfo.getData());
        }
        return new ResponseData<>(ResponseDataCode.STATUS_ERROR, serviceStatusInfo.getMsg(), null);
    }

    @RequiresAuthentication
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "删除门店员工，代言人")
    public ResponseData<Long> deleteById(@PathVariable("id") long id, @RequestParam("isSuperStar") boolean isSuperStar) {
        long userId = JWTUtil.getCurrentId();
        long legalSubjectId = authUserManager.get(String.valueOf(userId)).getLegalSubjectId();

        ServiceStatusInfo<Long> serviceStatusInfo = offlineStoreStaffsServiceImpl.deleteById(id, legalSubjectId, isSuperStar);
        if (serviceStatusInfo.isSuccess()) {
            return new ResponseData<>(ResponseDataCode.STATUS_NORMAL, serviceStatusInfo.getMsg(), serviceStatusInfo.getData());
        }
        return new ResponseData<>(ResponseDataCode.STATUS_ERROR, serviceStatusInfo.getMsg(), null);
    }



    @RequiresAuthentication
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    @ApiOperation(value = "查询所有门店员工，代言人")
    public ResponsePageInfoData<List<OfflineStoreStaffs>> select(@RequestParam(value = "pageNo", defaultValue = "1", required = true) int pageNo,
                                                                 @RequestParam(value = "rows", defaultValue = "30", required = true) int rows,
                                                                 @RequestBody SearchStaffInfo searchStaffInfo) {
        long userId = JWTUtil.getCurrentId();
        long legalSubjectId = authUserManager.get(String.valueOf(userId)).getLegalSubjectId();
        long storeId = storeServiceImpl.selectByLegalSubjectId(legalSubjectId).getData().getId();
        PageHelper.startPage(pageNo, rows);
        List<OfflineStoreStaffs> list = offlineStoreStaffsServiceImpl.searchStaffs(searchStaffInfo, legalSubjectId, storeId).getData();
        PageInfo<OfflineStoreStaffs> pageInfo = new PageInfo<>(list);
        return new ResponsePageInfoData<>(ResponseDataCode.STATUS_NORMAL, "", pageInfo.getList(), pageInfo.getTotal());
    }

    @RequiresAuthentication
    @RequestMapping(value = "/bulkSetStaffs", method = RequestMethod.GET)
    @ApiOperation(value = "批量设置员工，代言人")
    public ResponseData<Long> bulkSetStaffs(@RequestParam("userIds") long[] userIds,
                                            @RequestParam("isSuperStar") boolean isSuperStar) {
        long userId = JWTUtil.getCurrentId();
        long legalSubjectId = authUserManager.get(String.valueOf(userId)).getLegalSubjectId();
        ServiceStatusInfo<Long> serviceStatusInfo = offlineStoreStaffsServiceImpl.bulkSetSuperStar(userIds, legalSubjectId, isSuperStar);
        if (serviceStatusInfo.isSuccess()) {
            return new ResponseData(ResponseDataCode.STATUS_NORMAL, serviceStatusInfo.getMsg(), serviceStatusInfo.getData());
        }
        return new ResponseData(ResponseDataCode.STATUS_ERROR, serviceStatusInfo.getMsg(), null);

    }

    @RequiresAuthentication
    @RequestMapping(value = "/bulkDeleteStaffs", method = RequestMethod.POST)
    @ApiOperation(value = "批量删除员工，代言人")
    public ResponseData<Long> selectById(@RequestBody() IsSuperStar[] isSuperStars) {
        long userId = JWTUtil.getCurrentId();
        long legalSubjectId = authUserManager.get(String.valueOf(userId)).getLegalSubjectId();
        ServiceStatusInfo<Long> serviceStatusInfo = offlineStoreStaffsServiceImpl.bulkDeleteStaffs(isSuperStars, legalSubjectId);
        if (serviceStatusInfo.isSuccess()) {
            return new ResponseData(ResponseDataCode.STATUS_NORMAL, serviceStatusInfo.getMsg(), serviceStatusInfo.getData());
        }
        return new ResponseData(ResponseDataCode.STATUS_ERROR, serviceStatusInfo.getMsg(), null);

    }

    @RequiresAuthentication
    @RequestMapping(value = "/getSuperStarDetail", method = RequestMethod.POST)
    @ApiOperation(value = "获取代言人详情")
    public ResponsePageInfoData<List<SuperStarInfo>> getSuperStarDetail(@RequestBody SearchSuperStarInput input) {
        long userId = JWTUtil.getCurrentId();
        long legalSubjectId = authUserManager.get(String.valueOf(userId)).getLegalSubjectId();
        PageHelper.startPage(input.getPageNo(), input.getRows());
        List<SuperStarInfo> list = offlineStoreStaffsServiceImpl.getSuperStarDetail(input.getSearch(), input.getRank(), input.getSort(), legalSubjectId).getData();
        PageInfo<SuperStarInfo> pageInfo = new PageInfo<>(list);
        return new ResponsePageInfoData<>(ResponseDataCode.STATUS_NORMAL, "", pageInfo.getList(), pageInfo.getTotal());

    }

    @RequiresAuthentication
    @RequestMapping(value = "/videoListStaff/{userId}", method = RequestMethod.GET)
    @ApiOperation(value = "代言人作品列表个人信息")
    public ResponseData<SuperStarDto> videoListStaff(@PathVariable long userId) {
        ServiceStatusInfo<SuperStarDto> statusInfo = offlineStoreStaffsServiceImpl.videoListStaff(userId);
        if (statusInfo.isSuccess()) {
            return new ResponseData<>(0, statusInfo.getMsg(), statusInfo.getData(), null);
        }
        return new ResponseData<>(1, statusInfo.getMsg(), null, null);
    }

    @RequiresAuthentication
    @GetMapping("/getOfflineStoreStaffsById/{id}")
    @ApiOperation(value = "根据员工ID获取数据")
    public ResponseData<OfflineStoreStaffs> getOfflineStoreStaffsById(@PathVariable long id){
        ServiceStatusInfo<OfflineStoreStaffs> serviceStatusInfo = offlineStoreStaffsServiceImpl.getOfflineStoreStaffsById(id);
        return new ResponseData<>(serviceStatusInfo.isSuccess()?0:1, serviceStatusInfo.getMsg(), serviceStatusInfo.getData());
    }
}
