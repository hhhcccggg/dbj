package com.zwdbj.server.mobileapi.service.userInvitation.service;

import com.zwdbj.server.mobileapi.service.userInvitation.model.SearchUserInvitation;
import com.zwdbj.server.mobileapi.service.userInvitation.model.UserInvitationModel;
import com.zwdbj.server.basemodel.model.ServiceStatusInfo;

import java.util.List;

public interface UserInvitationService {

    /**
     * 完成邀请
     * @param initiatorUserId
     * @return
     */
    ServiceStatusInfo<Long> createUserInvitation(long initiatorUserId);

    /**
     * 分页查询数据
     * @param searchUserInvitation
     * @return
     */
    ServiceStatusInfo<List<UserInvitationModel>> searchUserInvitation(SearchUserInvitation searchUserInvitation);
}
