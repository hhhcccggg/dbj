package com.zwdbj.server.adminserver.controller.shop;

import com.zwdbj.server.adminserver.service.category.model.StoreServiceCategory;
import com.zwdbj.server.adminserver.service.shop.service.shopdetail.model.LocationInfo;
import com.zwdbj.server.adminserver.service.shop.service.shopdetail.model.QualificationInput;
import com.zwdbj.server.adminserver.service.shop.service.shopdetail.model.StoreDto;
import com.zwdbj.server.adminserver.service.shop.service.shopdetail.model.StoreImage;
import com.zwdbj.server.adminserver.service.shop.service.shopdetail.service.ShopDetailService;
import com.zwdbj.server.basemodel.model.ResponseData;
import com.zwdbj.server.basemodel.model.ServiceStatusInfo;
import com.zwdbj.server.tokencenter.TokenCenterManager;
import com.zwdbj.server.tokencenter.model.AuthUser;
import com.zwdbj.server.utility.common.shiro.JWTUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api("店铺信息相关")
@RequestMapping("/api/shop/storeInfo/dbj")
@RestController
public class ShopDetailController {
    @Autowired
    private ShopDetailService shopDetailServiceImpl;
    @Autowired
    private TokenCenterManager tokenCenterManager;

    @RequiresAuthentication
    @RequestMapping(value = "/basicInfo", method = RequestMethod.GET)
    @ApiOperation(value = "展示店铺基本信息")
    public ResponseData<StoreDto> basicInfo() {
        long userId = JWTUtil.getCurrentId();

        ServiceStatusInfo<AuthUser> statusInfo1 = tokenCenterManager.fetchUser(String.valueOf(userId));
        AuthUser authUser = statusInfo1.getData();
//        long legalSubjectId = authUser.getLegalSubjectId();
        // long legalSubjectId = tokenCenterManager.fetchUser(String.valueOf(userId)).getData().getLegalSubjectId();
        ServiceStatusInfo<StoreDto> statusInfo = shopDetailServiceImpl.findStoreDetail(authUser.getLegalSubjectId());
        if (statusInfo.isSuccess()) {
            return new ResponseData<>(0, "", statusInfo.getData());

        }
        return new ResponseData<>(1, statusInfo.getMsg(), null);


    }

    @RequiresAuthentication
    @RequestMapping(value = "/createOpeningHours/{openTime}/{closeTime}/{days}", method = RequestMethod.GET)
    @ApiOperation(value = "新增时间段")
    public ResponseData<Long> createOpeningHours(@PathVariable("openTime") int openTime,
                                                 @PathVariable("closeTime") int closeTime,
                                                 @PathVariable("days") String days) {
        long userId = JWTUtil.getCurrentId();

        long legalSubjectId = tokenCenterManager.fetchUser(String.valueOf(userId)).getData().getLegalSubjectId();
        ServiceStatusInfo<Long> statusInfo = this.shopDetailServiceImpl.createOpeningHours(openTime, closeTime, days, legalSubjectId);
        if (statusInfo.isSuccess()) {
            return new ResponseData<>(0, "", statusInfo.getData());
        }
        return new ResponseData<>(1, statusInfo.getMsg(), null);

    }

    @RequiresAuthentication
    @RequestMapping(value = "/modifyOpeningHours/{openTime}/{closeTime}/{days}", method = RequestMethod.GET)
    @ApiOperation(value = "修改营业时间")
    public ResponseData<Long> modifyOpeningHours(@PathVariable("openTime") int openTime,
                                                 @PathVariable("closeTime") int closeTime,
                                                 @PathVariable("days") String days) {
        long userId = JWTUtil.getCurrentId();

        long legalSubjectId = tokenCenterManager.fetchUser(String.valueOf(userId)).getData().getLegalSubjectId();
        ServiceStatusInfo<Long> statusInfo = this.shopDetailServiceImpl.modifyOpeningHours(openTime, closeTime, days, legalSubjectId);
        if (statusInfo.isSuccess()) {
            return new ResponseData<>(0, "", statusInfo.getData());
        }
        return new ResponseData<>(1, statusInfo.getMsg(), null);

    }


    @RequiresAuthentication
    @RequestMapping(value = "/location", method = RequestMethod.GET)
    @ApiOperation(value = "显示位置信息")
    public ResponseData<LocationInfo> showLocation() {
        long userId = JWTUtil.getCurrentId();

        long legalSubjectId = tokenCenterManager.fetchUser(String.valueOf(userId)).getData().getLegalSubjectId();
        ServiceStatusInfo<LocationInfo> statusInfo = this.shopDetailServiceImpl.showLocation(legalSubjectId);
        if (statusInfo.isSuccess()) {
            return new ResponseData<>(0, "", statusInfo.getData());
        }
        return new ResponseData<>(1, statusInfo.getMsg(), null);

    }

    @RequiresAuthentication
    @RequestMapping(value = "/modifylocation", method = RequestMethod.POST)
    @ApiOperation(value = "修改位置信息")
    public ResponseData<Long> modifylocation(@RequestBody LocationInfo info) {
        long userId = JWTUtil.getCurrentId();

        long legalSubjectId = tokenCenterManager.fetchUser(String.valueOf(userId)).getData().getLegalSubjectId();
        ServiceStatusInfo<Long> statusInfo = this.shopDetailServiceImpl.modifylocation(info, legalSubjectId);
        if (statusInfo.isSuccess()) {
            return new ResponseData<>(0, "", statusInfo.getData());

        }
        return new ResponseData<>(1, statusInfo.getMsg(), null);

    }


    @RequiresAuthentication
    @RequestMapping(value = "/modifyExtraService", method = RequestMethod.POST)
    @ApiOperation(value = "修改店铺额外服务范围")
    public ResponseData<Long> modifyExtraService(@RequestBody List<StoreServiceCategory> storeServiceCategory) {
        long userId = JWTUtil.getCurrentId();

        long legalSubjectId = tokenCenterManager.fetchUser(String.valueOf(userId)).getData().getLegalSubjectId();
        ServiceStatusInfo<Long> statusInfo = this.shopDetailServiceImpl.modifyExtraService(legalSubjectId, storeServiceCategory);
        if (statusInfo.isSuccess()) {
            return new ResponseData<>(0, "", statusInfo.getData());
        }
        return new ResponseData<>(1, statusInfo.getMsg(), null);
    }


    @RequiresAuthentication
    @RequestMapping(value = "/modifyServiceScopes", method = RequestMethod.POST)
    @ApiOperation(value = "修改店铺服务范围")
    public ResponseData<Long> modifyServiceScopes(@RequestBody List<StoreServiceCategory> storeServiceCategory) {
        long userId = JWTUtil.getCurrentId();

        long legalSubjectId = tokenCenterManager.fetchUser(String.valueOf(userId)).getData().getLegalSubjectId();
        ServiceStatusInfo<Long> statusInfo = this.shopDetailServiceImpl.modifyServiceScopes(legalSubjectId, storeServiceCategory);
        if (statusInfo.isSuccess()) {
            return new ResponseData<>(0, "", statusInfo.getData());
        }
        return new ResponseData<>(1, statusInfo.getMsg(), null);
    }

    @RequiresAuthentication
    @RequestMapping(value = "/uploadCheck", method = RequestMethod.POST)
    @ApiOperation("上传资料审核")
    public ResponseData<Long> uploadCheck(@RequestBody QualificationInput input) {
        long userId = JWTUtil.getCurrentId();

        long legalSubjectId = tokenCenterManager.fetchUser(String.valueOf(userId)).getData().getLegalSubjectId();
        ServiceStatusInfo<Long> statusInfo = this.shopDetailServiceImpl.uploadCheck(input, legalSubjectId);

        if (statusInfo.isSuccess()) {
            return new ResponseData<>(0, "", statusInfo.getData());
        }
        return new ResponseData<>(1, statusInfo.getMsg(), null);
    }

    @RequiresAuthentication
    @RequestMapping(value = "/modifyStoreImage", method = RequestMethod.POST)
    @ApiOperation(value = "修改店铺照片")
    public ResponseData<Long> modifyStoreImage(@RequestBody StoreImage storeImage) {
        long userId = JWTUtil.getCurrentId();

        long legalSubjectId = tokenCenterManager.fetchUser(String.valueOf(userId)).getData().getLegalSubjectId();
        ServiceStatusInfo<Long> statusInfo = this.shopDetailServiceImpl.modifyStoreImage(storeImage, legalSubjectId);
        if (statusInfo.isSuccess()) {
            return new ResponseData<>(0, "", statusInfo.getData());
        }
        return new ResponseData<>(1, statusInfo.getMsg(), null);
    }
}
