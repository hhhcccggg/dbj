package com.zwdbj.server.adminserver.controller.shop;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zwdbj.server.adminserver.service.shop.service.deliveryTemplateScopes.model.DeliveryTemplateScopesModel;
import com.zwdbj.server.adminserver.service.shop.service.deliveryTemplateScopes.service.IDeliveryTemplateScopesService;
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
@RequestMapping(value = "api/shop/deliveryTemplateScopes/dbj")
@Api("物流配送区域")
public class DeliveryTemplateScopesController {

    @Autowired
    IDeliveryTemplateScopesService deliveryTemplateScopesServiceImpl;

    @RequestMapping(value = "/select", method = RequestMethod.GET)
    @ApiOperation("查询所有的配送区域")
    public ResponsePageInfoData<List<DeliveryTemplateScopesModel>> findAllDeliveryTemplateScopes(@RequestParam(value = "pageNo", required = true, defaultValue = "1") int pageNo,
                                                                                                 @RequestParam(value = "rows", required = true, defaultValue = "30") int rows) {
        Page<DeliveryTemplateScopesModel> pageInfo = PageHelper.startPage(pageNo, rows);
        List<DeliveryTemplateScopesModel> deliveryTemplateScopesModels = deliveryTemplateScopesServiceImpl.findAllDeliveryTemplateScopes();
        return new ResponsePageInfoData(ResponseDataCode.STATUS_NORMAL, "", deliveryTemplateScopesModels, pageInfo.getTotal());
    }

    @RequestMapping(value = "/select/{id}", method = RequestMethod.GET)
    @ApiOperation("查询一条配送区域信息")
    public ResponseData<DeliveryTemplateScopesModel> getDeliveryTemplatesById(@PathVariable long id) {
        ServiceStatusInfo<DeliveryTemplateScopesModel> deliveryTemplateScopesModel = deliveryTemplateScopesServiceImpl.findDeliveryTemplateScopesById(id);
        if (deliveryTemplateScopesModel.isSuccess()) {
            return new ResponseData<>(ResponseDataCode.STATUS_NORMAL, deliveryTemplateScopesModel.getMsg(), deliveryTemplateScopesModel.getData());
        }
        return new ResponseData<>(ResponseDataCode.STATUS_ERROR, deliveryTemplateScopesModel.getMsg(), null);
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ApiOperation(value = "创建配送区域信息")
    @ResponseBody
    public ResponseData<Integer> addDeliveryTemplates(DeliveryTemplateScopesModel model) {
        ServiceStatusInfo<Integer> serviceStatusInfo = deliveryTemplateScopesServiceImpl.addDeliveryTemplates(model);
        if (serviceStatusInfo.isSuccess()) {
            return new ResponseData<>(ResponseDataCode.STATUS_NORMAL, serviceStatusInfo.getMsg(), serviceStatusInfo.getData());
        }
        return new ResponseData<>(ResponseDataCode.STATUS_ERROR, serviceStatusInfo.getMsg(), null);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    @ApiOperation(value = "删除物流信息")
    @ResponseBody
    public ResponseData<Integer> deleteDeliveryTemplatesById(@RequestParam("id") long id) {
        ServiceStatusInfo<Integer> serviceStatusInfo = deliveryTemplateScopesServiceImpl.deleteDeliveryTemplateScopesById(id);
        if (serviceStatusInfo.isSuccess()) {
            return new ResponseData<>(ResponseDataCode.STATUS_NORMAL, serviceStatusInfo.getMsg(), serviceStatusInfo.getData());
        }
        return new ResponseData<>(ResponseDataCode.STATUS_ERROR, serviceStatusInfo.getMsg(), null);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ApiOperation(value = "修改物流信息")
    @ResponseBody
    public ResponseData<Integer> updateDeliveryTemplates(DeliveryTemplateScopesModel model) {
        ServiceStatusInfo<Integer> serviceStatusInfo = deliveryTemplateScopesServiceImpl.updateDeliveryTemplatesScopes(model);
        if (serviceStatusInfo.isSuccess()) {
            return new ResponseData<>(ResponseDataCode.STATUS_NORMAL, serviceStatusInfo.getMsg(), serviceStatusInfo.getData());
        }
        return new ResponseData<>(ResponseDataCode.STATUS_ERROR, serviceStatusInfo.getMsg(), null);
    }

}
