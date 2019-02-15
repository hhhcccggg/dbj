package com.zwdbj.server.mobileapi.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zwdbj.server.mobileapi.service.wxMiniProgram.receiveAddress.model.ReceiveAddressId;
import com.zwdbj.server.mobileapi.service.wxMiniProgram.receiveAddress.model.ReceiveAddressInput;
import com.zwdbj.server.mobileapi.service.wxMiniProgram.receiveAddress.model.ReceiveAddressModel;
import com.zwdbj.server.mobileapi.service.wxMiniProgram.receiveAddress.service.ReceiveAddressService;
import com.zwdbj.server.utility.model.ResponseData;
import com.zwdbj.server.utility.model.ResponseDataCode;
import com.zwdbj.server.utility.model.ResponsePageInfoData;
import com.zwdbj.server.utility.model.ServiceStatusInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/receiveAddress")
@Api(description = "收货地址")
public class ReceiveAddressController {

    @Autowired
    private ReceiveAddressService receiveAddressServiceImpl;

    @GetMapping(value = "findPage")
    @ApiOperation(value = "我的收货地址")
    @RequiresAuthentication
    public ResponsePageInfoData<List<ReceiveAddressModel>> findPage(@RequestParam(value = "pageNo", required = true, defaultValue = "1") int pageNo,
                                                              @RequestParam(value = "rows", required = true, defaultValue = "10") int rows){
        PageHelper.startPage(pageNo,rows);
        ServiceStatusInfo<List<ReceiveAddressModel>> serviceStatusInfo= receiveAddressServiceImpl.findByPage();
        if( !serviceStatusInfo.isSuccess()){
            return new ResponsePageInfoData(ResponseDataCode.STATUS_ERROR,serviceStatusInfo.getMsg(),null,0);
        }
        PageInfo<ReceiveAddressModel> pageInfo = new PageInfo<>(serviceStatusInfo.getData());
        return new ResponsePageInfoData(ResponseDataCode.STATUS_NORMAL,"",pageInfo.getList(),pageInfo.getTotal());
    }

    @GetMapping(value = "find/{id}")
    @ApiOperation(value = "根据id查询收货地址")
    @RequiresAuthentication
    public ResponseData<ReceiveAddressModel> findById(@PathVariable long id){
        ServiceStatusInfo<ReceiveAddressModel> serviceStatusInfo= receiveAddressServiceImpl.findById(id);
        if( !serviceStatusInfo.isSuccess()){
            return new ResponseData(ResponseDataCode.STATUS_ERROR,serviceStatusInfo.getMsg(),null);
        }
        return new ResponseData(ResponseDataCode.STATUS_NORMAL,"",serviceStatusInfo.getData());
    }

    @PostMapping(value = "addReceiveAddress")
    @ApiOperation(value = "新增收货地址")
    @RequiresAuthentication
    public ResponseData<Long> addReceiveAddress(@RequestBody @Valid ReceiveAddressInput receiveAddressInput){
        ServiceStatusInfo<Long> serviceStatusInfo= receiveAddressServiceImpl.createReceiveAddress(receiveAddressInput);
        if( !serviceStatusInfo.isSuccess()){
            return new ResponseData(ResponseDataCode.STATUS_ERROR,serviceStatusInfo.getMsg(),null);
        }
        return new ResponseData(ResponseDataCode.STATUS_NORMAL,"",serviceStatusInfo.getData());
    }

    @PostMapping(value = "updateReceiveAddress")
    @ApiOperation(value = "修改收货地址")
    @RequiresAuthentication
    public ResponseData<Long> updateReceiveAddress(@RequestBody @Valid ReceiveAddressInput receiveAddressInput){
        ServiceStatusInfo<Long> serviceStatusInfo= receiveAddressServiceImpl.updateReceiveAddress(receiveAddressInput);
        if( !serviceStatusInfo.isSuccess()){
            return new ResponseData(ResponseDataCode.STATUS_ERROR,serviceStatusInfo.getMsg(),null);
        }
        return new ResponseData(ResponseDataCode.STATUS_NORMAL,"",serviceStatusInfo.getData());
    }

    @PostMapping(value = "setDefault")
    @ApiOperation(value = "设置默认收货地址")
    @RequiresAuthentication
    public ResponseData<Long> setDefault(@RequestBody @Valid ReceiveAddressId receiveAddressId){
        ServiceStatusInfo<Long> serviceStatusInfo= receiveAddressServiceImpl.setDefalue(receiveAddressId.getId());
        if( !serviceStatusInfo.isSuccess()){
            return new ResponseData(ResponseDataCode.STATUS_ERROR,serviceStatusInfo.getMsg(),null);
        }
        return new ResponseData(ResponseDataCode.STATUS_NORMAL,"",serviceStatusInfo.getData());
    }

    @PostMapping(value = "removeReceiveAddress")
    @ApiOperation(value = "删除收货地址")
    @RequiresAuthentication
    public ResponseData<Long> removeReceiveAddress(@RequestBody @Valid ReceiveAddressId receiveAddressId){
        ServiceStatusInfo<Long> serviceStatusInfo= receiveAddressServiceImpl.deleteById(receiveAddressId.getId());
        if( !serviceStatusInfo.isSuccess()){
            return new ResponseData(ResponseDataCode.STATUS_ERROR,serviceStatusInfo.getMsg(),null);
        }
        return new ResponseData(ResponseDataCode.STATUS_NORMAL,"",serviceStatusInfo.getData());
    }

    @GetMapping(value = "getDefault")
    @ApiOperation(value = "获取默认收货地址")
    @RequiresAuthentication
    public  ResponseData<ReceiveAddressModel> getDefault(){
        ServiceStatusInfo<ReceiveAddressModel> serviceStatusInfo= receiveAddressServiceImpl.getDefault();
        return new ResponseData(serviceStatusInfo.isSuccess()?ResponseDataCode.STATUS_NORMAL:ResponseDataCode.STATUS_ERROR,
                serviceStatusInfo.getMsg(),serviceStatusInfo.getData());
    }
}
