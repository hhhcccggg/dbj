package com.zwdbj.server.adminserver.service.shop.service.accountInfo.service;

import com.zwdbj.server.adminserver.service.user.model.AdModifyManagerPasswordInput;
import com.zwdbj.server.adminserver.service.user.model.AdNewlyPwdInput;
import com.zwdbj.server.basemodel.model.ServiceStatusInfo;

public interface AccountInfoService {
    ServiceStatusInfo<Object> checkPhoneCode(String phone, String code);

    ServiceStatusInfo<String> sendPhoneCode(String phone);

    ServiceStatusInfo<Long> modifyPwdAd(Long id, AdModifyManagerPasswordInput input);
    ServiceStatusInfo<Long> newlyPwdAd(AdNewlyPwdInput input);
}
