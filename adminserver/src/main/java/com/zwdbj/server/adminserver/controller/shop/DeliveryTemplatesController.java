package com.zwdbj.server.adminserver.controller.shop;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zwdbj.server.adminserver.service.shop.service.deliveryTemplates.model.DeliveryTemplatesModel;
import com.zwdbj.server.adminserver.service.shop.service.deliveryTemplates.service.IDeliveryTemplatesService;
import com.zwdbj.server.basemodel.model.ResponseData;
import com.zwdbj.server.basemodel.model.ResponseDataCode;
import com.zwdbj.server.basemodel.model.ResponsePageInfoData;
import com.zwdbj.server.basemodel.model.ServiceStatusInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "api/shop/deliveryTemplates/dbj")
@Api("物流相关")
public class DeliveryTemplatesController {
    @Autowired
    IDeliveryTemplatesService deliveryTemplatesServiceImpl;

    @RequestMapping(value = "/select",method = RequestMethod.GET)
    @ApiOperation("查询所有物流信息")
    public ResponsePageInfoData<List<DeliveryTemplatesModel>> findAllDeliveryTemplates(@RequestParam(value = "pageNo", required = true, defaultValue = "1") int pageNo,
                                                                                       @RequestParam(value = "rows", required = true, defaultValue = "30") int rows){
        Page<DeliveryTemplatesModel> pageInfo = PageHelper.startPage(pageNo,rows);
        List<DeliveryTemplatesModel> deliveryTemplatesModels = deliveryTemplatesServiceImpl.findAllDeliveryTemplates();
        return new ResponsePageInfoData(ResponseDataCode.STATUS_NORMAL,"",deliveryTemplatesModels,pageInfo.getTotal());
    }

    @RequestMapping(value = "/select/{id}",method = RequestMethod.GET)
    @ApiOperation("查询一条物流信息")
    public ResponseData<DeliveryTemplatesModel> getDeliveryTemplatesById(@PathVariable long id){
        ServiceStatusInfo<DeliveryTemplatesModel> deliveryTemplatesModel = deliveryTemplatesServiceImpl.getDeliveryTemplatesById(id);
        if (deliveryTemplatesModel.isSuccess()) {
            return new ResponseData<>(ResponseDataCode.STATUS_NORMAL,deliveryTemplatesModel.getMsg(),deliveryTemplatesModel.getData());
        }
        return new ResponseData<>(ResponseDataCode.STATUS_ERROR,deliveryTemplatesModel.getMsg(),null);
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ApiOperation(value = "创建物流信息")
    @ResponseBody
    public ResponseData<Integer> addDeliveryTemplates(DeliveryTemplatesModel model){
        ServiceStatusInfo<Integer> serviceStatusInfo = deliveryTemplatesServiceImpl.addDeliveryTemplates(model);
        if (serviceStatusInfo.isSuccess()) {
            return new ResponseData<>(ResponseDataCode.STATUS_NORMAL,serviceStatusInfo.getMsg(),serviceStatusInfo.getData());
        }
        return new ResponseData<>(ResponseDataCode.STATUS_ERROR,serviceStatusInfo.getMsg(),null);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    @ApiOperation(value = "删除物流信息")
    @ResponseBody
    public ResponseData<Integer> deleteDeliveryTemplatesById(@PathVariable("id") long id){
        ServiceStatusInfo<Integer> serviceStatusInfo = deliveryTemplatesServiceImpl.deleteDeliveryTemplatesById(id);
        if (serviceStatusInfo.isSuccess()) {
            return new ResponseData<>(ResponseDataCode.STATUS_NORMAL,serviceStatusInfo.getMsg(),serviceStatusInfo.getData());
        }
        return new ResponseData<>(ResponseDataCode.STATUS_ERROR,serviceStatusInfo.getMsg(),null);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ApiOperation(value = "修改物流信息")
    @ResponseBody
    public ResponseData<Integer> updateDeliveryTemplates(DeliveryTemplatesModel model){
        ServiceStatusInfo<Integer> serviceStatusInfo = deliveryTemplatesServiceImpl.updateDeliveryTemplates(model);
        if (serviceStatusInfo.isSuccess()) {
            return new ResponseData<>(ResponseDataCode.STATUS_NORMAL,serviceStatusInfo.getMsg(),serviceStatusInfo.getData());
        }
        return new ResponseData<>(ResponseDataCode.STATUS_ERROR,serviceStatusInfo.getMsg(),null);
    }
}
