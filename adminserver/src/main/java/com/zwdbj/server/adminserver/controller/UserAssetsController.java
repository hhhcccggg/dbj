package com.zwdbj.server.adminserver.controller;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zwdbj.server.adminserver.service.userAssets.model.UserAssets;
import com.zwdbj.server.adminserver.service.userAssets.service.UserAssetsService;
import com.zwdbj.server.utility.model.ResponseData;
import com.zwdbj.server.utility.model.ResponseDataCode;
import com.zwdbj.server.utility.model.ResponsePageInfoData;
import com.zwdbj.server.utility.model.ServiceStatusInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RequestMapping(value = "/api/userAssets/dbj")
@RestController
@Api(description = "用户资产相关")
public class UserAssetsController {
    @Resource
    private UserAssetsService userAssetsServiceImpl;

    @ApiOperation(value = "查询所有用户资产相关")
    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public ResponsePageInfoData<List<UserAssets>> searchAll(@RequestParam(value = "pageNo", defaultValue = "1", required = true) int pageNo,
                                                            @RequestParam(value = "rows", defaultValue = "30", required = true) int rows) {

        PageHelper.startPage(pageNo, rows);
        List<UserAssets> result = this.userAssetsServiceImpl.searchAll().getData();
        PageInfo pageInfo = new PageInfo(result);
        return new ResponsePageInfoData<>(ResponseDataCode.STATUS_NORMAL, "", result, pageInfo.getTotal());
    }

    @RequestMapping(value = "/search/{userId}")
    @ApiOperation(value = "通过id查询用户资产")
    public ResponseData<UserAssets> searchByUserId(@PathVariable("userId") Long userId) {
        ServiceStatusInfo<UserAssets> serviceStatusInfo = this.userAssetsServiceImpl.searchByUserId(userId);
        if (serviceStatusInfo.isSuccess()) {
            return new ResponseData<>(ResponseDataCode.STATUS_NORMAL, "", serviceStatusInfo.getData());
        }
        return new ResponseData<>(ResponseDataCode.STATUS_ERROR, serviceStatusInfo.getMsg(), null);
    }
}
