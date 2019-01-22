package com.zwdbj.server.mobileapi.service.userInvitation.mapper;

import com.zwdbj.server.mobileapi.service.userInvitation.commmon.UserInvitationsState;
import com.zwdbj.server.mobileapi.service.userInvitation.model.SearchUserInvitation;
import com.zwdbj.server.mobileapi.service.userInvitation.model.UserInvitationModel;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface IUserInvitationMapper {

    /**
     * 创建邀请并完成
     * @param userInvitationModel
     * @return
     */
    @Insert("INSERT INTO `core_userInvitations` (" +
            "`id`,`initiatorUserId`,`receivedUserId`,`state`,`message`)" +
            "VALUES(" +
            "#{model.id},#{model.initiatorUserId},#{model.receivedUserId},#{model.state},#{model.message}" +
            ");")
    long createUserInvitation(@Param("model") UserInvitationModel userInvitationModel);

    /**
     * 发起请求
     * @param id
     * @param initiatorUserId
     * @return
     */
    long createUserInvitation(@Param("id") long id, @Param("initiatorUserId") long initiatorUserId);

    /**
     * 修改邀请状态
     * @param receivedUserId
     * @param userInvitationsState
     * @return
     */
    long updateReceivedUserId(@Param("receivedUserId") long receivedUserId, @Param("userInvitationsState") UserInvitationsState userInvitationsState);

    /**
     * 删除邀请,但只能删除状态为NONE
     * @param receivedUserId
     * @return
     */
    long deleteReceivedUserId(@Param("receivedUserId") long receivedUserId);

    /**
     * 查询用户接受的邀请
     * @param receivedUserId
     * @return
     */
    UserInvitationModel selectReceivedUserId(@Param("receivedUserId") long receivedUserId);

    /**
     * 查询我邀请的记录
     * @param searchUserInvitation
     * @return
     */
    @Select("<script>select * from core_userinvitations " +
            "where isDeleted=0 "+
            "<if test=\"searchUserInvitation.initiatorUserId>0\">and initiatorUserId=#{searchUserInvitation.initiatorUserId}</if>"+
            "<if test=\"searchUserInvitation.state != null\">and state=#{searchUserInvitation.state}</if>"+
            "order by createTime"+
            "</script>")
    List<UserInvitationModel> searchUserInvitation(@Param("searchUserInvitation") SearchUserInvitation searchUserInvitation);
}
