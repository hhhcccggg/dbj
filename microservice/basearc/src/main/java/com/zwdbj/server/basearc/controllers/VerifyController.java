package com.zwdbj.server.basearc.controllers;

import com.zwdbj.server.common.sms.ISendSmsService;
import com.zwdbj.server.basemodel.model.ResponseData;
import com.zwdbj.server.basemodel.model.ResponseDataCode;
import com.zwdbj.server.basemodel.model.ServiceStatusInfo;
import com.zwdbj.server.basemodel.model.VerifyPhoneInput;
import com.zwdbj.server.config.settings.AliyunConfigs;
import com.zwdbj.server.serviceinterface.basearc.scprovider.IVerifyRemoteService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@Api(value = "认证相关(手机号&邮件等相关)")
@RequestMapping("/api/verify")
public class VerifyController implements IVerifyRemoteService {
    @Autowired
    private ISendSmsService sendSmsService;
    @Autowired
    private AliyunConfigs aliyunConfigs;
    /**
     * 获取手机验证码
     *
     * @param phone 手机号
     * @param area  国际区号，比如中国+86
     * @return
     */
    @Override
    @GetMapping("/fetchPhoneCode")
    @ApiOperation("获取手机验证码")
    public ResponseData<Map<String,String>> fetchPhoneCode(@RequestParam("phone") String phone, @RequestParam(value = "area",defaultValue = "+86") String area) {
        ServiceStatusInfo<Object> result = this.sendSmsService.sendCode(phone,
                this.aliyunConfigs.getSmsCodeSignName(),
                this.aliyunConfigs.getSmsTemplateCode());
        if (result.isSuccess()) {
            Map<String,String> resParams = new HashMap<>();
            if (result.getData()!=null) {
                resParams.put("code",(String)result.getData());
            }
            return new ResponseData<>(ResponseDataCode.STATUS_NORMAL,"OK",resParams);
        } else {
            return new ResponseData<>(ResponseDataCode.STATUS_ERROR,result.getMsg(),null);
        }
    }

    @Override
    @PostMapping("/verifyPhone")
    @ApiOperation("校验手机号")
    public ResponseData<Object> verifyPhone(@RequestBody VerifyPhoneInput phoneInput) {
        ServiceStatusInfo<Object> result = this.sendSmsService.checkPhoneCode(phoneInput.getPhone(),phoneInput.getCode());
        if (result.isSuccess()) {
            return new ResponseData<>(ResponseDataCode.STATUS_NORMAL,"OK",null);
        } else {
            return new ResponseData<>(ResponseDataCode.STATUS_ERROR,result.getMsg(),null);
        }
    }
}
