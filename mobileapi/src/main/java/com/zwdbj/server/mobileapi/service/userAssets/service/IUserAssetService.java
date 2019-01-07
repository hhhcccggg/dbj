package com.zwdbj.server.mobileapi.service.userAssets.service;

import com.zwdbj.server.mobileapi.service.userAssets.model.BuyCoinConfigModel;
import com.zwdbj.server.mobileapi.service.userAssets.model.UserCoinDetailAddInput;
import com.zwdbj.server.mobileapi.service.userAssets.model.UserCoinDetailModifyInput;

public interface IUserAssetService {
    long addUserCoinDetail(UserCoinDetailAddInput input);
    long addUserCoinDetail(long userId,UserCoinDetailAddInput input);
    long addUserCoinDetailOnce(long userId,UserCoinDetailAddInput input);
    int updateUserCoinDetail(UserCoinDetailModifyInput input);
    BuyCoinConfigModel findCoinConfigByProductId(String productId,String type);
    boolean findCoinDetailByTrade(String tradeNo,String tradeType);
    boolean minusUserCoins(int coins,long userId,long productOrderId);
}
