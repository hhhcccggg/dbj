package com.zwdbj.server.adminserver.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zwdbj.server.adminserver.service.userCoinDetail.model.UserCoinDetail;
import com.zwdbj.server.adminserver.service.userCoinDetail.service.UserCoinDetailService;
import com.zwdbj.server.utility.model.ResponseData;
import com.zwdbj.server.utility.model.ResponseDataCode;
import com.zwdbj.server.utility.model.ResponsePageInfoData;
import com.zwdbj.server.utility.model.ServiceStatusInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RequestMapping(value = "/api/userCoinDetail/dbj")
@RestController
@Api(description = "用户金币明细相关")
public class UserCoinDetailController {
    @Resource
    private UserCoinDetailService userCoinDetailServiceImpl;

    @ApiOperation(value = "查询所有用户金币明细")
    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public ResponsePageInfoData<List<UserCoinDetail>> searchAll(@RequestParam(value = "pageNo", defaultValue = "1", required = true) int pageNo,
                                                                @RequestParam(value = "rows", defaultValue = "30", required = true) int rows) {

        PageHelper.startPage(pageNo, rows);
        List<UserCoinDetail> result = this.userCoinDetailServiceImpl.searchAll().getData();
        PageInfo pageInfo = new PageInfo(result);
        return new ResponsePageInfoData<>(ResponseDataCode.STATUS_NORMAL, "", result, pageInfo.getTotal());
    }

    @RequestMapping(value = "/search/{userId}",method = RequestMethod.GET)
    @ApiOperation(value = "通过id查询用户资产")
    public ResponseData<UserCoinDetail> searchByUserId(@PathVariable("userId") Long userId) {
        ServiceStatusInfo<UserCoinDetail> serviceStatusInfo = this.userCoinDetailServiceImpl.searchByUserId(userId);
        if (serviceStatusInfo.isSuccess()) {
            return new ResponseData<>(ResponseDataCode.STATUS_NORMAL, "", serviceStatusInfo.getData());
        }
        return new ResponseData<>(ResponseDataCode.STATUS_ERROR, serviceStatusInfo.getMsg(), null);
    }
}
