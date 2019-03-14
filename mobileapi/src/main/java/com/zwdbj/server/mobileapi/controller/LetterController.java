package com.zwdbj.server.mobileapi.controller;

import com.zwdbj.server.basemodel.model.ResponseData;
import com.zwdbj.server.basemodel.model.ResponseDataCode;
import com.zwdbj.server.basemodel.model.ServiceStatusInfo;
import com.zwdbj.server.mobileapi.service.Letter.service.ILetterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/letter")
@Api(description = "私信相关")
public class LetterController {

    @Autowired
    private ILetterService letterServiceImpl;

    @RequiresAuthentication
    @RequestMapping(value = "/isSend",method = RequestMethod.GET)
    @ApiOperation("判断是否还能继续发送私信：超过规定条数则不能")
    public ResponseData<Integer> letterAccount(@RequestParam long receiverId) {
        ServiceStatusInfo<Integer> serviceStatusInfo = this.letterServiceImpl.isSend(receiverId);
        return new ResponseData<>(ResponseDataCode.STATUS_NORMAL,"",serviceStatusInfo.getData());
    }

    @RequiresAuthentication
    @RequestMapping(value = "/saveCount",method = RequestMethod.GET)
    @ApiOperation("保存私信条数")
    public ResponseData<Boolean> saveLetterCount(@RequestParam long receiverId) {
        ServiceStatusInfo<Boolean> serviceStatusInfo = this.letterServiceImpl.saveLetterCount(receiverId);
        if(!serviceStatusInfo.isSuccess())
            return new ResponseData<>(ResponseDataCode.STATUS_ERROR,serviceStatusInfo.getMsg(),serviceStatusInfo.getData());
        return new ResponseData<>(ResponseDataCode.STATUS_NORMAL,"",serviceStatusInfo.getData());
    }
}
