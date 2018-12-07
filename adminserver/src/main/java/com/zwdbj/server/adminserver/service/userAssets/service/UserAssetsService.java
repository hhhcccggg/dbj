package com.zwdbj.server.adminserver.service.userAssets.service;

import com.zwdbj.server.adminserver.service.userAssets.model.UserAssets;
import com.zwdbj.server.utility.model.ServiceStatusInfo;

import java.util.List;

public interface UserAssetsService {
    ServiceStatusInfo<UserAssets> searchByUserId(Long userId);

    ServiceStatusInfo<List<UserAssets>> searchAll();


}
