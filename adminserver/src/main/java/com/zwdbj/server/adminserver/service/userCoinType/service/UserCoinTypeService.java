package com.zwdbj.server.adminserver.service.userCoinType.service;

import com.zwdbj.server.adminserver.service.userCoinType.model.UserCoinType;
import com.zwdbj.server.utility.model.ServiceStatusInfo;

import java.util.List;

public interface UserCoinTypeService {

    ServiceStatusInfo<List<UserCoinType>> searchAll();

    ServiceStatusInfo<UserCoinType> searchByUserId(Long userId);
}
