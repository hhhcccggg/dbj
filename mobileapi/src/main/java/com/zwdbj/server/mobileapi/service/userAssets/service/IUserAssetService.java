package com.zwdbj.server.mobileapi.service.userAssets.service;

import com.zwdbj.server.mobileapi.service.userAssets.model.UserCoinDetailAddInput;
import com.zwdbj.server.mobileapi.service.userAssets.model.UserCoinDetailModifyInput;

public interface IUserAssetService {
    long addUserCoinDetail(UserCoinDetailAddInput input);
    long addUserCoinDetail(long userId,UserCoinDetailAddInput input);
    int updateUserCoinDetail(UserCoinDetailModifyInput input);
}
