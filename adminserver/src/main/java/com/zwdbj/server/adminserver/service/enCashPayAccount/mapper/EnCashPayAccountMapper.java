package com.zwdbj.server.adminserver.service.enCashPayAccount.mapper;

import com.zwdbj.server.adminserver.service.enCashPayAccount.model.EnCashPayAccount;
import com.zwdbj.server.adminserver.service.enCashPayAccount.model.ModifyEnCashPayAccount;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface EnCashPayAccountMapper {
    @Select("select * from core_enCashAccounts where isDeleted=0 order by createTime")
    List<EnCashPayAccount> findAll();

    @Select("select * from core_enCashAccounts where userId=#{userId} ")
    EnCashPayAccount findByUserId(@Param("userId") Long userId);

    @Update("update core_enCashAccounts set type=#{enCashPayAccount.type}," +
            "uniqueId=#{enCashPayAccount.uniqueId},name=#{enCashPayAccount.name}," +
            "avatarUrl=#{enCashPayAccount.avatarUrl},accessToken=#{enCashPayAccount.accessToken}," +
            "expireIn=#{enCashPayAccount.expireIn},isLocked=#{enCashPayAccount.isLocked} " +
            "where userId=#{userId}")
    Long update(@Param("userId") Long userId, @Param("enCashPayAccount") ModifyEnCashPayAccount enCashPayAccount);

    @Insert("insert into core_enCashAccounts (id,type,uniqueId,name,avatarUrl," +
            "accessToken,expireIn,userId,isLocked) values(#{id},#{enCashPayAccount.type}," +
            "#{enCashPayAccount.uniqueId},#{enCashPayAccount.name}," +
            "#{enCashPayAccount.avatarUrl},#{enCashPayAccount.accessToken}," +
            "#{enCashPayAccount.expireIn},#{enCashPayAccount.userId}," +
            "#{enCashPayAccount.isLocked})")
    Long create(@Param("id") Long id, @Param("enCashPayAccount") EnCashPayAccount enCashPayAccount);

    @Delete("update  core_enCashAccounts set isDeleted =1, deleteTime=now() where userId=#{userId} ")
    Long deleteByUserId(@Param("userId") Long userId);


}
