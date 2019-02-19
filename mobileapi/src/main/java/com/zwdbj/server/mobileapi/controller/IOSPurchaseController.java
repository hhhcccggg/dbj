package com.zwdbj.server.mobileapi.controller;

import com.zwdbj.server.mobileapi.service.purchase.model.RequestMsg;
import com.zwdbj.server.mobileapi.service.purchase.model.ResponseMsg;
import com.zwdbj.server.mobileapi.service.purchase.service.PurchaseService;
import com.zwdbj.server.utility.model.ResponseData;
import com.zwdbj.server.utility.model.ServiceStatusInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@Api(description = "ios内购验证相关")
@RestController
@RequestMapping(value = "/api/ios/dbj")
public class IOSPurchaseController {

    @Autowired
    private PurchaseService purchaseServiceImpl;

    @RequiresAuthentication
    @RequestMapping(value = "/purchase", method = RequestMethod.POST)
    @ApiOperation(value = "二次验证")
    public ResponseData<ResponseMsg> purchase (@RequestBody RequestMsg requestMsg, @RequestParam long userId) throws Exception{
        ServiceStatusInfo statusInfo = this.purchaseServiceImpl.doIosRequest(requestMsg.getTransactionIdentifier(),requestMsg.getReceipt(),userId);
        if (statusInfo.isSuccess()) {
            return new ResponseData(0, "", statusInfo.getData());
        }

        return new ResponseData<>(1, statusInfo.getMsg(), null);
    }

}
