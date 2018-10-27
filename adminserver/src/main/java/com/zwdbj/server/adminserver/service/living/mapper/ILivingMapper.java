package com.zwdbj.server.adminserver.service.living.mapper;

import com.zwdbj.server.adminserver.service.living.model.*;
import org.apache.ibatis.annotations.*;

import java.util.Date;
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
    @SelectProvider(type = LivingSqlProvider.class,method = "todayLivingAd")
    List<AdLivingInfoDto> todayLivingAd(@Param("input")AdTodayLivingInput input);
    @Select("select c.*,r.title,r.description,l.complainCount,u.userName,u.nickName from core_complains c " +
            "inner join core_complainReasons r on c.reasonId=r.id " +
            "inner join core_livings l on c.toResId=l.id " +
            "inner join core_users u on c.fromUserId=u.id " +
            "where c.toResId=#{toResId}")
    List<AdLivingComplainInfoDto> livingComplainAd(@Param("toResId")Long toResId);

    @UpdateProvider(type = LivingSqlProvider.class,method = "doLivingComplainAd")
    Long doLivingComplainAd(@Param("id")Long id,@Param("input")AdDoLivingInput input);
    @SelectProvider(type = LivingSqlProvider.class,method = "historyLiving")
    List<AdLivingInfoDto> historyLiving(@Param("input")AdHistoryLivingInput input);

    @Select("select id from core_livings where isLiving=true")
    List<Long> timedQueryIsLivingIds();
    @Select("select chatRoomId from core_livings where isLiving=true and id=#{id}")
    String timedQueryChatRoomId(@Param("id") Long id);

    @Update("update core_livings set isLiving=false where id=#{id}")
    int modifyIsLiving(@Param("id") Long id);

    @Update("update core_livings set onlinePeopleCount=#{onlinePeopleCount},liveingTotalTime=#{liveingTotalTime} where id=#{id}")
    int updateOnlinePeopleCount(@Param("id")Long id,@Param("onlinePeopleCount")Integer num,@Param("liveingTotalTime")Long liveingTotalTime);
    @Select("select chatRoomId from core_livings where id=#{id}")
    String findChatRoomIdById(@Param("id")Long id);

    @Select("select createTime from core_livings where id=#{id}")
    Date findCreatedTimeById(@Param("id")Long id);

    //直播鉴黄回调
    @Update("update core_livings set status=1 where id=#{id}")
    int updateLivingStatus(@Param("id")Long id);

}
