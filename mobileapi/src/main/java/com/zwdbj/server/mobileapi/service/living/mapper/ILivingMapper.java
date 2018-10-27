package com.zwdbj.server.mobileapi.service.living.mapper;

import com.zwdbj.server.mobileapi.service.living.model.*;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ILivingMapper {
    @Insert("INSERT INTO `core_livings`(`id`," +
            "`title`," +
            "`coverUrl`," +
            "`linkPets`," +
            "`longitude`," +
            "`latitude`," +
            "`userId`," +
            "`pushUrl`," +
            "`pullUrl`," +
            "`hlsUrl`," +
            "`isLiving`," +
            "`address`) VALUES(" +
            "#{model.id}," +
            "#{model.title}," +
            "#{model.coverUrl}," +
            "#{model.linkPets}," +
            "#{model.longitude}," +
            "#{model.latitude}," +
            "#{model.userId}," +
            "#{model.rtmpPublishUrl}," +
            "#{model.rtmpPlayUrl}," +
            "#{model.hlsPlayUrl}," +
            "true," +
            "#{model.address})")
    long add(@Param("model") CreateLivingModel model);
    @Select("select * from core_livings where id=#{id}")
    LivingDetailInfoDto get(@Param("id") long id);
    @Select("select * from core_livings where isLiving=true order by onlinePeopleCount desc")
    List<LivingInfoDto> living();
    @Select("select livingTbl.*,t3.nickName as userNickName,t3.avatarUrl as userAvatarUrl," +
            "(select count(*) from core_followers as t2 where t2.followerUserId = t1.userId and t2.userId=#{userId}) as isFollowedMe " +
            "from core_followers as t1 inner join core_users as t3 on t3.id = t1.userId inner join core_livings as livingTbl" +
            " on livingTbl.userId =t1.userId where t1.followerUserId=#{userId} and livingTbl.isLiving =true order by livingTbl.createTime desc")
    List<LivingInfoDto> myFollowedLiving(@Param("userId") long userId);
    @UpdateProvider(type = LivingSqlProvider.class,method = "updateField")
    long updateField(@Param("fields") String fields, @Param("id") long id);


    @Select("select chatRoomId from core_livings where isLiving=true and id=#{id}")
    String timedQueryChatRoomId(@Param("id") Long id);

    @Select("select chatRoomId from core_livings where id=#{id}")
    String findChatRoomIdById(@Param("id")Long id);




}
