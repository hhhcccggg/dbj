package com.zwdbj.server.mobileapi.controller.wxMini;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
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
@RequestMapping("/api/wx/mini/receiveAddress")
@Api(description = "收货地址")
public class WXMiniReceiveAddressController {

    @Autowired
    private ReceiveAddressService receiveAddressServiceImpl;

    @GetMapping(value = "findPage")
    @ApiOperation(value = "我的收货地址")
    @RequiresAuthentication
    public ResponsePageInfoData<ReceiveAddressModel> findPage(@RequestParam(value = "pageNo", required = true, defaultValue = "1") int pageNo,
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
    public ResponseData<Long> findById(@PathVariable long id){
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

    @PostMapping(value = "setByReceiveAddress")
    @ApiOperation(value = "修改收货地址")
    @RequiresAuthentication
    public ResponseData<Long> setByReceiveAddress(@RequestBody @Valid ReceiveAddressInput receiveAddressInput ,@RequestParam(value = "id" ,required = true) long id){
        ServiceStatusInfo<Long> serviceStatusInfo= receiveAddressServiceImpl.updateReceiveAddress(receiveAddressInput,id);
        if( !serviceStatusInfo.isSuccess()){
            return new ResponseData(ResponseDataCode.STATUS_ERROR,serviceStatusInfo.getMsg(),null);
        }
        return new ResponseData(ResponseDataCode.STATUS_NORMAL,"",serviceStatusInfo.getData());
    }

    @PostMapping(value = "setDefault")
    @ApiOperation(value = "设置默认收货地址")
    @RequiresAuthentication
    public ResponseData<Long> setDefault(@RequestBody long id){
        ServiceStatusInfo<Long> serviceStatusInfo= receiveAddressServiceImpl.setDefalue(id);
        if( !serviceStatusInfo.isSuccess()){
            return new ResponseData(ResponseDataCode.STATUS_ERROR,serviceStatusInfo.getMsg(),null);
        }
        return new ResponseData(ResponseDataCode.STATUS_NORMAL,"",serviceStatusInfo.getData());
    }

    @PostMapping(value = "removeByReceiveAddress")
    @ApiOperation(value = "删除收货地址")
    @RequiresAuthentication
    public ResponseData<Long> removeByReceiveAddress(@RequestBody long id){
        ServiceStatusInfo<Long> serviceStatusInfo= receiveAddressServiceImpl.deleteById(id);
        if( !serviceStatusInfo.isSuccess()){
            return new ResponseData(ResponseDataCode.STATUS_ERROR,serviceStatusInfo.getMsg(),null);
        }
        return new ResponseData(ResponseDataCode.STATUS_NORMAL,"",serviceStatusInfo.getData());
    }
}
