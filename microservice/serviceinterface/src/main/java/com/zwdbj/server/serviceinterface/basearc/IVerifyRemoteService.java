package com.zwdbj.server.serviceinterface.basearc;

import com.zwdbj.server.basemodel.model.ResponseData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(value = "basearc")
@RequestMapping("/api/verify")
public interface IVerifyRemoteService {
    /**
     * 获取手机验证码
     * @param phone 手机号
     * @param area 国际区号，比如中国+86
     * @return
     */
    @GetMapping("/fetchPhoneCode")
    ResponseData<Map<String,String>> fetchPhoneCode(@RequestParam("phone") String phone,
                                                    @RequestParam("area") String area);
}
