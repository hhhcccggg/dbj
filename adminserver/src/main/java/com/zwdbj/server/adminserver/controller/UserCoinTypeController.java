package com.zwdbj.server.adminserver.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zwdbj.server.adminserver.service.userCoinType.model.UserCoinType;
import com.zwdbj.server.adminserver.service.userCoinType.service.UserCoinTypeService;
import com.zwdbj.server.utility.model.ResponseData;
import com.zwdbj.server.utility.model.ResponseDataCode;
import com.zwdbj.server.utility.model.ResponsePageInfoData;
import com.zwdbj.server.utility.model.ServiceStatusInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping(value = "/api/userCoinType/dbj")
@Api(description = "用户金币总额分类相关")
public class UserCoinTypeController {
    @Resource
    private UserCoinTypeService userCoinTypeServiceImpl;


    @ApiOperation(value = "分类查询用户金币总额")
    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public ResponsePageInfoData<List<UserCoinType>> searchAll(@RequestParam(value = "pageNo", defaultValue = "1", required = true) int pageNo,
                                                              @RequestParam(value = "rows", defaultValue = "30", required = true) int rows) {

        PageHelper.startPage(pageNo, rows);
        List<UserCoinType> result = this.userCoinTypeServiceImpl.searchAll().getData();
        PageInfo pageInfo = new PageInfo(result);
        System.out.println(result);
        return new ResponsePageInfoData<>(ResponseDataCode.STATUS_NORMAL, "", result, pageInfo.getTotal());
    }

    @RequestMapping(value = "/search/{userId}")
    @ApiOperation(value = "通过id分类查询用户金币总额")
    public ResponseData<UserCoinType> searchByUserId(@PathVariable("userId") Long userId) {
        ServiceStatusInfo<UserCoinType> serviceStatusInfo = this.userCoinTypeServiceImpl.searchByUserId(userId);
        if (serviceStatusInfo.isSuccess()) {
            return new ResponseData<>(ResponseDataCode.STATUS_NORMAL, "", serviceStatusInfo.getData());
        }
        return new ResponseData<>(ResponseDataCode.STATUS_ERROR, serviceStatusInfo.getMsg(), null);
    }

}
