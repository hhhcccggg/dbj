package com.zwdbj.server.basearc.controllers;

import com.zwdbj.server.basearc.sms.ISendSmsService;
import com.zwdbj.server.basemodel.model.ResponseData;
import com.zwdbj.server.basemodel.model.ResponseDataCode;
import com.zwdbj.server.basemodel.model.ServiceStatusInfo;
import com.zwdbj.server.config.settings.AliyunConfigs;
import com.zwdbj.server.serviceinterface.basearc.provider.IVerifyRemoteService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
                "1234",
                this.aliyunConfigs.getSmsCodeSignName(),
                this.aliyunConfigs.getSmsTemplateCode());
        if (result.isSuccess()) {
            return new ResponseData<>(ResponseDataCode.STATUS_NORMAL,"OK",null);
        } else {
            return new ResponseData<>(ResponseDataCode.STATUS_ERROR,result.getMsg(),null);
        }
    }
}
