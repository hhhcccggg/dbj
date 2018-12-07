package com.zwdbj.server.adminserver.service.userCoinDetail.service;

import com.zwdbj.server.adminserver.service.userCoinDetail.model.UserCoinDetail;
import com.zwdbj.server.utility.model.ServiceStatusInfo;

import java.util.List;

public interface UserCoinDetailService {

    ServiceStatusInfo<List<UserCoinDetail>> searchAll();

    ServiceStatusInfo<UserCoinDetail> searchByUserId(Long userId);

}
