package com.zwdbj.server.mobileapi.controller;

import com.zwdbj.server.mobileapi.service.purchase.model.RequestMsg;
import com.zwdbj.server.mobileapi.service.purchase.service.PurchaseService;
import com.zwdbj.server.utility.model.ResponseData;
import com.zwdbj.server.utility.model.ServiceStatusInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Api(description = "ios内购验证相关")
@RestController
@RequestMapping(value = "/api/ios/dbj")
public class IOSPurchaseController {

    @Autowired
    private PurchaseService purchaseServiceImpl;

    @RequestMapping(value = "/purchase", method = RequestMethod.POST)
    @ApiOperation(value = "二次验证")
    public ResponseData<Map<String, Object>> purchase (@RequestBody RequestMsg requestMsg, @RequestParam long userId) throws Exception{
        ServiceStatusInfo statusInfo = this.purchaseServiceImpl.doIosRequest(requestMsg.getTransactionIdentifier(),requestMsg.getReceipt(),userId);
        if (statusInfo.isSuccess()) {
            return new ResponseData(0, "", statusInfo.getData());
        }

        return new ResponseData<>(1, statusInfo.getMsg(), null);
    }

}
