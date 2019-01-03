package com.zwdbj.server.shopadmin.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zwdbj.server.shop_admin_service.service.receiveAddress.model.ReceiveAddressAddInput;
import com.zwdbj.server.shop_admin_service.service.receiveAddress.model.ReceiveAddressModel;
import com.zwdbj.server.shop_admin_service.service.receiveAddress.model.ReceiveAddressModifyInput;
import com.zwdbj.server.shop_admin_service.service.receiveAddress.service.ReceiveAddressService;
import com.zwdbj.server.utility.model.ResponseData;
import com.zwdbj.server.utility.model.ResponseDataCode;
import com.zwdbj.server.utility.model.ResponsePageInfoData;
import com.zwdbj.server.utility.model.ServiceStatusInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/receiveAddress/dbj")
@Api(description = "收货地址相关")
public class ReceiveAddressController {
    @Autowired
    ReceiveAddressService receiveAddressServiceImpl;

    @RequestMapping(value = "/select", method = RequestMethod.GET)
    @ApiOperation(value = "查询所有收货地址")
    public ResponsePageInfoData<List<ReceiveAddressModel>> findAllReceiveAddresses(@RequestParam(value = "pageNo", required = true, defaultValue = "1") int pageNo,
                                                                                  @RequestParam(value = "rows", required = true, defaultValue = "30") int rows) {
        Page<ReceiveAddressModel> pageInfo = PageHelper.startPage(pageNo,rows);
        List<ReceiveAddressModel> receiveAddressModels = this.receiveAddressServiceImpl.findAllReceiveAddresses();
        return new ResponsePageInfoData(ResponseDataCode.STATUS_NORMAL, "", receiveAddressModels, pageInfo.getTotal());

    }

    @RequestMapping(value = "/select/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "查询收货地址详情")
    public ResponseData<ReceiveAddressModel> getReceiveAddressById(@PathVariable long id) {
        ServiceStatusInfo<ReceiveAddressModel> receiveAddressModel = this.receiveAddressServiceImpl.getReceiveAddressById(id);
        if (receiveAddressModel.isSuccess()) {
            return new ResponseData<>(ResponseDataCode.STATUS_NORMAL,receiveAddressModel.getMsg(),receiveAddressModel.getData());
        }
        return new ResponseData<>(ResponseDataCode.STATUS_ERROR,receiveAddressModel.getMsg(),null);
    }

    @RequestMapping(value = "/modify/{id}", method = RequestMethod.POST)
    @ApiOperation(value = "修改收货地址")
    public ResponseData<Integer> modifyReceiveAddress(@PathVariable long id,
                                                       @RequestBody ReceiveAddressModifyInput input) {
        ServiceStatusInfo <Integer> statusInfo = this.receiveAddressServiceImpl.modifyReceiveAddress(id,input);
        if (statusInfo.isSuccess()) {
            return new ResponseData<>(ResponseDataCode.STATUS_NORMAL,statusInfo.getMsg(),null);
        }
        return new ResponseData<>(ResponseDataCode.STATUS_ERROR,statusInfo.getMsg(),null);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ApiOperation(value = "添加收货地址")
    public ResponseData<Integer> addReceiveAddress(@RequestBody ReceiveAddressAddInput input) {
        ServiceStatusInfo <Integer> statusInfo = this.receiveAddressServiceImpl.addReceiveAddress(input);
        if (statusInfo.isSuccess()) {
            return new ResponseData<>(ResponseDataCode.STATUS_NORMAL,statusInfo.getMsg(),null);
        }
        return new ResponseData<>(ResponseDataCode.STATUS_ERROR,statusInfo.getMsg(),null);
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "删除收货地址")
    public ResponseData<Integer> notRealDeleteReceiveAddress(@PathVariable(value = "id") long id) {
        ServiceStatusInfo <Integer> statusInfo = this.receiveAddressServiceImpl.notRealDeleteReceiveAddress(id);
        if (statusInfo.isSuccess()) {
            return new ResponseData<>(ResponseDataCode.STATUS_NORMAL,statusInfo.getMsg(),null);
        }
        return new ResponseData<>(ResponseDataCode.STATUS_ERROR,statusInfo.getMsg(),null);
    }

}
