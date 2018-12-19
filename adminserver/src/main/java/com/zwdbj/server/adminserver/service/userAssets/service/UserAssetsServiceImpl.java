package com.zwdbj.server.adminserver.service.userAssets.service;


import com.zwdbj.server.adminserver.service.userAssets.mapper.UserAssetsMapper;
import com.zwdbj.server.adminserver.service.userAssets.model.EnCashMentDetailModel;
import com.zwdbj.server.adminserver.service.userAssets.model.UserAssets;
import com.zwdbj.server.adminserver.service.userAssets.model.UserCoinDetail;
import com.zwdbj.server.adminserver.service.userAssets.model.UserCoinType;
import com.zwdbj.server.pay.alipay.AlipayService;
import com.zwdbj.server.pay.alipay.model.AliTransferInput;
import com.zwdbj.server.pay.alipay.model.AliTransferResult;
import com.zwdbj.server.utility.common.shiro.JWTUtil;
import com.zwdbj.server.utility.model.ServiceStatusInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service

public class UserAssetsServiceImpl implements UserAssetsService {

    @Resource
    private UserAssetsMapper userAssetsMapper;
    @Autowired
    AlipayService alipayService;

    @Override
    public ServiceStatusInfo<UserAssets> searchUserAssetsByUserId(Long userId) {
        UserAssets result = null;
        try {
            result = this.userAssetsMapper.searchUserAssetsByUserId(userId);
            return new ServiceStatusInfo<>(0, "", result);

        } catch (Exception e) {
            return new ServiceStatusInfo<>(1, "查询用户资产失败" + e.getMessage(), result);
        }
    }

    @Override
    public ServiceStatusInfo<List<UserAssets>> searchAllUserAssets() {
        List<UserAssets> result = null;
        try {
            result = this.userAssetsMapper.searchAllUserAssets();
            return new ServiceStatusInfo<>(0, "", result);
        } catch (Exception e) {
            return new ServiceStatusInfo<>(1, "查询所有用户资产失败" + e.getMessage(), result);
        }
    }

    @Override
    public ServiceStatusInfo<List<UserCoinDetail>> searchAllUserCoinDetail() {
        List<UserCoinDetail> result = null;
        try {
            result = this.userAssetsMapper.searchAllUserCoinDetail();
            return new ServiceStatusInfo<>(0, "", result);
        } catch (Exception e) {
            return new ServiceStatusInfo<>(1, "查询用户金币明细失败" + e.getMessage(), result);
        }
    }

    @Override
    public ServiceStatusInfo<List<UserCoinDetail>> searchUserCoinDetailByUserId(Long userId) {
        List<UserCoinDetail> result = null;
        try {
            result = this.userAssetsMapper.searchUserCoinDetailByUserId(userId);
            return new ServiceStatusInfo<>(0, "", result);
        } catch (Exception e) {
            return new ServiceStatusInfo<>(1, "通过用户id查询用户金币明细失败" + e.getMessage(), result);
        }
    }

    @Override
    public ServiceStatusInfo<List<UserCoinType>> searchAllUserCoinTyoe() {
        List<UserCoinType> result = null;
        try {
            result = this.userAssetsMapper.searchAllUserCoinTypes();
            System.out.println(result);
            return new ServiceStatusInfo<>(0, "", result);
        } catch (Exception e) {
            return new ServiceStatusInfo<>(1, "分类型查询用户金币总额失败" + e.getMessage(), result);
        }
    }

    @Override
    public ServiceStatusInfo<UserCoinType> searchUserCoinTypeByUserId(Long userId) {
        UserCoinType result = null;
        try {
            result = this.userAssetsMapper.searchUserCoinTpyesByUserId(userId);
            return new ServiceStatusInfo<>(0, "", result);
        } catch (Exception e) {
            return new ServiceStatusInfo<>(1, "通过用户id分类型查询用户金币总额失败" + e.getMessage(), result);
        }
    }

    @Override
    public List<EnCashMentDetailModel> getAllVerifyEnCashs() {
        List<EnCashMentDetailModel> models = this.userAssetsMapper.getAllVerifyEnCashs();
        return models;
    }

    @Override
    public ServiceStatusInfo<EnCashMentDetailModel> getVerifyEnCashById(long id) {
        try {
            EnCashMentDetailModel model = this.userAssetsMapper.getVerifyEnCashById(id);
            return new ServiceStatusInfo<>(0, "", model);
        }catch (Exception e){
            return new ServiceStatusInfo<>(1, "通过id查询提现详情失败:" + e.getMessage(), null);
        }
    }

    @Transactional
    public int updateEnCashStatus(long id,String status,boolean isAllowedEnCash){
        return this.userAssetsMapper.updateEnCashStatus(id,status,isAllowedEnCash);
    }


    @Transactional
    public int updateCoinDetailStatus(String tradeNo,String status){
        return this.userAssetsMapper.updateCoinDetailStatus(tradeNo,status);
    }

    @Transactional
    public int updateUserCoinTypeByUserId(long userId,String type,int coins,int lockedCoins){
        return this.userAssetsMapper.updateUserCoinTypeByUserId(userId,type,coins,lockedCoins);
    }

    @Transactional
    public int updateUserCoinByUserId(long userId,int coins){
        return this.userAssetsMapper.updateUserCoinByUserId(userId,coins);
    }



    @Override
    @Transactional
    public ServiceStatusInfo<Integer> verifyEnCash(long id,long userId) {
        try {
            EnCashMentDetailModel model = this.getVerifyEnCashById(id).getData();
            if (userId!=model.getUserId()){
                this.updateEnCashStatus(id,"FAILED",false);
                this.updateCoinDetailStatus(String.valueOf(id),"FAILED");
                this.updateUserCoinTypeByUserId(userId,"INCOME",model.getCoins(),-model.getCoins());
                this.updateUserCoinByUserId(userId,model.getCoins());
                return new ServiceStatusInfo<>(1, "提现失败0:用户id不一致:", null);
            }
            String uniqueId =  this.userAssetsMapper.getUniqueIdById(model.getPayAccountId());
            AliTransferInput input = new AliTransferInput();
            input.setOutBizNo(String.valueOf(id));
            input.setPayeeType("ALIPAY_USERID");
            input.setPayeeAccount(uniqueId);
            input.setAmount(String.valueOf(model.getRmbs()/100.0));
            input.setRemark("爪子APP提现");
            long lockedCoins = this.searchUserCoinTypeByUserId(userId).getData().getLockedCoins();
            if (lockedCoins<model.getCoins()){
                this.updateEnCashStatus(id,"FAILED",false);
                this.updateCoinDetailStatus(String.valueOf(id),"FAILED");
                this.updateUserCoinTypeByUserId(userId,"INCOME",model.getCoins(),-model.getCoins());
                this.updateUserCoinByUserId(userId,model.getCoins());
                return new ServiceStatusInfo<>(1, "提现失败1:", null);
            }
            ServiceStatusInfo<AliTransferResult> aliInfo = this.alipayService.transfer(input);
            long enCashDetailId = Long.valueOf(aliInfo.getData().getOutBizNo());
            if (aliInfo.isSuccess() && aliInfo.getData().isTransferred()){
                this.updateEnCashStatus(enCashDetailId,"SUCCESS",true);
                this.updateCoinDetailStatus(aliInfo.getData().getOutBizNo(),"SUCCESS");
                this.updateUserCoinTypeByUserId(userId,"INCOME",0,-model.getCoins());
                return new ServiceStatusInfo<>(0,"",1);
            }else {
                this.updateEnCashStatus(enCashDetailId,"FAILED",false);
                this.updateCoinDetailStatus(aliInfo.getData().getOutBizNo(),"FAILED");
                this.updateUserCoinTypeByUserId(userId,"INCOME",model.getCoins(),-model.getCoins());
                this.updateUserCoinByUserId(userId,model.getCoins());
                return new ServiceStatusInfo<>(1,"提现失败2:第三方返回失败",0);
            }

        }catch (Exception e){
            return new ServiceStatusInfo<>(1, "提现失败:" + e.getMessage(), null);
        }
    }
}
