package com.zwdbj.server.mobileapi.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zwdbj.server.mobileapi.model.ResponseData;
import com.zwdbj.server.mobileapi.model.ResponseDataCode;
import com.zwdbj.server.mobileapi.model.ResponsePageInfoData;
import com.zwdbj.server.mobileapi.service.ServiceStatusInfo;
import com.zwdbj.server.mobileapi.service.complain.model.*;
import com.zwdbj.server.mobileapi.service.complain.service.ComplainService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/complain")
@Api(description = "举报相关基本信息",value = "basicComplain")
public class ComplainController {
    @Autowired
    protected ComplainService complainService;

    @RequestMapping(value = "/listReason",method = RequestMethod.POST)
    @ApiOperation(value = "获取举报的原因列表")
    public ResponsePageInfoData<List<ComplainReasonListInfoDto>> listReason(
            @RequestBody ComplainReasonListInput input,
            @RequestParam(value = "pageNo",required = true,defaultValue = "1") int pageNo,
            @RequestParam(value = "rows",required = true,defaultValue = "13") int rows
    ) {
        Page<ComplainReasonListInfoDto> pageInfo = PageHelper.startPage(pageNo,rows);
        List<ComplainReasonListInfoDto> dtos = this.complainService.list(input);
        return new ResponsePageInfoData<>(ResponseDataCode.STATUS_NORMAL,"",dtos,pageInfo.getTotal());
    }

    @RequestMapping(value = "/complain",method = RequestMethod.POST)
    @ApiOperation(value = "举报")
    public ResponseData<Object> complain(@RequestBody PubComplainInput input) {
        ServiceStatusInfo<Object> statusInfo = this.complainService.pubComplain(input);
        if (statusInfo.isSuccess()) {
            return new ResponseData<>(ResponseDataCode.STATUS_NORMAL,statusInfo.getMsg(),null);
        }
        return new ResponseData<>(ResponseDataCode.STATUS_ERROR,statusInfo.getMsg(),null);
    }
}
