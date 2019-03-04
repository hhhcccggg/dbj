package com.zwdbj.server.serviceinterface.basearc.scprovider;

import com.zwdbj.server.basemodel.model.ResponseData;
import com.zwdbj.server.basemodel.model.VerifyPhoneInput;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

public interface IVerifyRemoteService {
    /**
     * 获取手机验证码
     * @param phone 手机号
     * @param area 国际区号，比如中国+86
     * @return
     */
    @GetMapping("/api/verify/fetchPhoneCode")
    ResponseData<Map<String,String>> fetchPhoneCode(@RequestParam("phone") String phone,
                                                    @RequestParam("area") String area);
    @PostMapping("/api/verify/verifyPhone")
    ResponseData<Object> verifyPhone(@RequestBody VerifyPhoneInput phoneInput);
}
