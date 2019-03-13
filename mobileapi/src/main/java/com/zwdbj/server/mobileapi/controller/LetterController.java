package com.zwdbj.server.mobileapi.controller;

import com.zwdbj.server.basemodel.model.ResponseData;
import com.zwdbj.server.basemodel.model.ResponseDataCode;
import com.zwdbj.server.basemodel.model.ServiceStatusInfo;
import com.zwdbj.server.mobileapi.service.Letter.service.ILetterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/letter")
@Api(description = "私信相关")
public class LetterController {

    @Autowired
    private ILetterService letterService;

    @RequiresAuthentication
    @RequestMapping(value = "/isSend",method = RequestMethod.GET)
    @ApiOperation("判断是否还能继续发送私信：超过规定条数则不能")
    public ResponseData<Integer> letterAccount(long receiverId) {
        //ServiceStatusInfo<Integer> serviceStatusInfo = this.letterService.isSend(receiverId);
        return new ResponseData<>(ResponseDataCode.STATUS_NORMAL,"",this.letterService.isSend(receiverId).getData());
    }

    @RequiresAuthentication
    @RequestMapping(value = "/saveCount",method = RequestMethod.POST)
    @ApiOperation("保存私信条数")
    public ResponseData<Boolean> saveLetterCount(long receiverId) {
        ServiceStatusInfo<Boolean> serviceStatusInfo = this.letterService.saveLetterCount(receiverId);
        if(!serviceStatusInfo.isSuccess())
            return new ResponseData<>(ResponseDataCode.STATUS_ERROR,serviceStatusInfo.getMsg(),serviceStatusInfo.getData());
        return new ResponseData<>(ResponseDataCode.STATUS_NORMAL,"",serviceStatusInfo.getData());
    }
}
