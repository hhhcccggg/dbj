package com.zwdbj.server.mobileapi.controller;

import com.zwdbj.server.mobileapi.service.pay.alipay.model.ChargeCoinAlipayResult;
import com.zwdbj.server.mobileapi.service.pay.alipay.service.AlipayBizService;
import com.zwdbj.server.mobileapi.service.pay.model.ChargeCoinInput;
import com.zwdbj.server.pay.alipay.model.*;
import com.zwdbj.server.utility.common.shiro.JWTUtil;
import com.zwdbj.server.utility.model.ResponseData;
import com.zwdbj.server.utility.model.ResponseDataCode;
import com.zwdbj.server.utility.model.ServiceStatusInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@RestController
@Api("支付")
@RequestMapping("/api/pay/alipay")
public class PayAlipayController {
    @Autowired
    private AlipayBizService alipayBizService;

    @RequiresAuthentication
    @ApiOperation("生成充值订单")
    @RequestMapping(value = "/chargeCoins",method = RequestMethod.POST)
    public ResponseData<ChargeCoinAlipayResult> chargeCoins(@RequestBody ChargeCoinInput input) {
        ServiceStatusInfo<ChargeCoinAlipayResult> serviceStatusInfo = this.alipayBizService.chargeCoins(input,JWTUtil.getCurrentId());
        if (serviceStatusInfo.isSuccess()) {
            return new ResponseData<>(ResponseDataCode.STATUS_NORMAL,"OK",serviceStatusInfo.getData());
        }
        return new ResponseData<>(ResponseDataCode.STATUS_ERROR,serviceStatusInfo.getMsg(),null);
    }

    @RequiresAuthentication
    @ApiOperation("订单查询")
    @RequestMapping(value = "/orderQuery",method = RequestMethod.POST)
    public ResponseData<AliOrderQueryResult> orderQuery(@RequestBody AliOrderQueryInput input) {
        ServiceStatusInfo<AliOrderQueryResult> serviceStatusInfo = this.alipayBizService.orderQuery(input);
        if (serviceStatusInfo.isSuccess()) {
            return new ResponseData<>(ResponseDataCode.STATUS_NORMAL,"OK",serviceStatusInfo.getData());
        }
        return new ResponseData<>(ResponseDataCode.STATUS_ERROR,serviceStatusInfo.getMsg(),null);
    }

    @RequestMapping(value = "/payNotify",method = RequestMethod.POST)
    public void payNotify(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<String,String> params = new HashMap<>();
        Map requestParams = request.getParameterMap();
        for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用。
            //valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, valueStr);
        }
        ServiceStatusInfo<Object> serviceStatusInfo = this.alipayBizService.paramsRsaCheckV1(params);
        if (serviceStatusInfo.isSuccess()) {
            response.setStatus(200);
            OutputStream outputStream = response.getOutputStream();
            outputStream.write("success".getBytes("UTF-8"));
        } else {
            response.setStatus(500);
        }
    }

}
