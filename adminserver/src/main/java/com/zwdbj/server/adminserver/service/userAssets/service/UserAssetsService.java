package com.zwdbj.server.adminserver.service.userAssets.service;

import com.zwdbj.server.adminserver.service.userAssets.model.EnCashMentDetailModel;
import com.zwdbj.server.adminserver.service.userAssets.model.UserAssets;
import com.zwdbj.server.adminserver.service.userAssets.model.UserCoinDetail;
import com.zwdbj.server.adminserver.service.userAssets.model.UserCoinType;
import com.zwdbj.server.utility.model.ServiceStatusInfo;

import java.util.List;

public interface UserAssetsService {
    ServiceStatusInfo<UserAssets> searchUserAssetsByUserId(Long userId);

    ServiceStatusInfo<List<UserAssets>> searchAllUserAssets();

    ServiceStatusInfo<List<UserCoinDetail>> searchAllUserCoinDetail();

    ServiceStatusInfo<List<UserCoinDetail>> searchUserCoinDetailByUserId(Long userId);

    ServiceStatusInfo<List<UserCoinType>> searchAllUserCoinTyoe();

    ServiceStatusInfo<UserCoinType> searchUserCoinTypeByUserId(Long userId,String type);
    List<EnCashMentDetailModel> getAllVerifyEnCashs();
    ServiceStatusInfo<EnCashMentDetailModel> getVerifyEnCashById(long id);

    ServiceStatusInfo<Integer> verifyEnCash(long id,long userId);
}
