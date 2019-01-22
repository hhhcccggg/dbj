package com.zwdbj.server.mobileapi.service.userInvitation.service;

import com.zwdbj.server.mobileapi.service.userAssets.model.UserCoinDetailAddInput;
import com.zwdbj.server.mobileapi.service.userAssets.service.UserAssetServiceImpl;
import com.zwdbj.server.mobileapi.service.userInvitation.commmon.UserInvitationsState;
import com.zwdbj.server.mobileapi.service.userInvitation.mapper.IUserInvitationMapper;
import com.zwdbj.server.mobileapi.service.userInvitation.model.SearchUserInvitation;
import com.zwdbj.server.mobileapi.service.userInvitation.model.UserInvitationModel;
import com.zwdbj.server.utility.common.UniqueIDCreater;
import com.zwdbj.server.utility.common.shiro.JWTUtil;
import com.zwdbj.server.utility.model.ServiceStatusInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserInvitationServiceImpl implements UserInvitationService {

    @Autowired
    private IUserInvitationMapper iUserInvitationMapper;
    @Autowired
    private UserAssetServiceImpl userAssetServiceImpl;

    @Override
    public ServiceStatusInfo<Long> createUserInvitation(long initiatorUserId) {
        try{
            //TODO 未发金币
            long userId = JWTUtil.getCurrentId();
            if(userId == 0)return new ServiceStatusInfo<>(1,"用户未登录",null);
            UserInvitationModel userInvitationModel = new UserInvitationModel(UniqueIDCreater.generateID(), initiatorUserId, userId, UserInvitationsState.PETS, "获取奖励25小饼干");
            long result = iUserInvitationMapper.createUserInvitation(userInvitationModel);
            if(result>0){
                this.userAssetServiceImpl.userIsExist(initiatorUserId);
                UserCoinDetailAddInput userCoinDetailAddInput = new UserCoinDetailAddInput();
                userCoinDetailAddInput.setStatus("SUCCESS");
                userCoinDetailAddInput.setNum(25);
                userCoinDetailAddInput.setTitle("邀请新用户获得小饼干"+25+"个");
                userCoinDetailAddInput.setType("TASK");
                this.userAssetServiceImpl.userPlayCoinTask(userCoinDetailAddInput,initiatorUserId,"TASK",25,"INVITENEWUSER","DONE");
                return new ServiceStatusInfo<>(0,"",result);
            }
            return new ServiceStatusInfo<>(1,"新增失败，影响行数"+result,null);
        }catch(Exception e){
            return new ServiceStatusInfo<>(1,"新增失败"+e.getMessage(),null);
        }
    }

    @Override
    public ServiceStatusInfo<List<UserInvitationModel>> searchUserInvitation(SearchUserInvitation searchUserInvitation) {
        try{
            long userId = JWTUtil.getCurrentId();
            if(userId == 0) return new ServiceStatusInfo<>(1,"用户未登录",null);
            searchUserInvitation.setInitiatorUserId(userId);
            List<UserInvitationModel> invitationModels = iUserInvitationMapper.searchUserInvitation(searchUserInvitation);
            return new ServiceStatusInfo<>(0,"",invitationModels);
        }catch(Exception e){
            return new ServiceStatusInfo<>(1,"查询失败"+e.getMessage(),null);
        }
    }
}
