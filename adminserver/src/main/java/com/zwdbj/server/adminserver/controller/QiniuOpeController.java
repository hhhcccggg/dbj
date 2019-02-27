package com.zwdbj.server.adminserver.controller;

import com.zwdbj.server.adminserver.service.qiniu.service.QiniuOperService;
import com.zwdbj.server.basemodel.model.ResponseData;
import com.zwdbj.server.basemodel.model.ResponseDataCode;
import com.zwdbj.server.basemodel.model.ServiceStatusInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/qiniu/opr")
@Api(description = "增加猫和狗的种类")
public class QiniuOpeController {
    @Autowired
    private QiniuOperService qiniuOperService;

    @RequestMapping(value = "/cat",method = RequestMethod.POST)
    @ApiOperation(value = "添加猫的种类")
    @RequiresAuthentication
    public ResponseData<Integer> addCat(){
        ServiceStatusInfo<Integer> statusInfo = this.qiniuOperService.catOpe();
        if (statusInfo.isSuccess()){
            return new ResponseData<>(ResponseDataCode.STATUS_NORMAL,"",statusInfo.getData());
        }else {
            return new ResponseData<>(ResponseDataCode.STATUS_ERROR,"添加失败",null);
        }
    }
    @RequestMapping(value = "/dog",method = RequestMethod.POST)
    @ApiOperation(value = "添加狗的种类")
    @RequiresAuthentication
    public ResponseData<Integer> addDog(){
        ServiceStatusInfo<Integer> statusInfo = this.qiniuOperService.dogOpe();
        if (statusInfo.isSuccess()){
            return new ResponseData<>(ResponseDataCode.STATUS_NORMAL,"",statusInfo.getData());
        }else {
            return new ResponseData<>(ResponseDataCode.STATUS_ERROR,"添加失败",null);
        }
    }
}
