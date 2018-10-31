package com.zwdbj.server.adminserver.service.video.mapper;


import com.zwdbj.server.adminserver.service.share.model.ShareDto;
import com.zwdbj.server.adminserver.service.video.model.*;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface IVideoMapper {
    @SelectProvider(type = VideoSqlProvider.class,method = "searchComplainVideosSql")
    List<VideoInfoDto> complainVideosAd(@Param("model") AdVideoSearchComplainInput input);
    @Update("update core_videos set status=#{model.status},rejectMsg=#{model.rejectMsg} where id=#{id}")
    Long verityAd(@Param("id") Long id,@Param("model") AdVideoVerityInput input);

    @SelectProvider(type = VideoSqlProvider.class,method = "verityListAd")
    List<AdVideoVerityInfoDto> verityListAd(@Param("model") AdVideoVerityInfoInput input);

    @Select("select c.*,r.title complainReason,r.description,v.complainCount,u.nickName,u.userName from core_complains as c " +
            "inner join core_complainReasons as r on c.reasonId=r.id " +
            "inner join core_videos as v on c.toResId=v.id " +
            "inner join core_users as u on c.fromUserId =u.id " +
            "where c.toResId=#{toResId}")
    List<AdVideoComplainInfoDto> complainInfoAd(@Param("toResId")Long toResId);

    @Update("update core_videos set status=#{model.treatment} where id=#{id}")
    Long doComplainInfoAd(@Param("id")Long toResId,@Param("model") AdVideoDoComplainInput input);

    @SelectProvider(type = VideoSqlProvider.class,method = "searchSql")
    List<VideoInfoDto> searchAd(@Param("model")SearchVideoAdInput model);
    @Insert("insert into core_videos(id,title,coverImageUrl,videoUrl,linkPets," +
            "tags,longitude,latitude,isHiddenLocation,musicId,userId," +
            "coverImageWidth,coverImageHeight,address," +
            "firstFrameUrl,firstFrameWidth,firstFrameHeight) values(#{id}," +
            "#{dataInput.title}," +
            "#{dataInput.coverImageKey}," +
            "#{dataInput.videoKey}," +
            "#{dataInput.linkPets}," +
            "#{dataInput.tags}," +
            "#{dataInput.longitude}," +
            "#{dataInput.latitude}," +
            "#{dataInput.isHiddenLocation}," +
            "#{dataInput.musicId}," +
            "#{userId}," +
            "#{dataInput.coverImageWidth}," +
            "#{dataInput.coverImageHeight}," +
            "#{dataInput.address}," +
            "#{dataInput.firstFrameUrl}," +
            "#{dataInput.firstFrameWidth}," +
            "#{dataInput.firstFrameHeight})")
    long publicVideo(@Param("id") long id,@Param("userId") long userId,@Param("dataInput")VideoPublishInput dataInput);
    @Select("select * from core_videos where status=0 order by createTime desc")
    List<VideoInfoDto> listHot();
    @Select("select * from core_videos where status=0 order by createTime desc")
    List<VideoInfoDto> listLatest();
    @Select("select * from core_videos where id=#{id} and status=0")
    VideoDetailInfoDto video(@Param("id") long id);
    @Select("SELECT *  FROM core_videos AS t1 " +
            "JOIN (SELECT ROUND(RAND() * ((SELECT MAX(id) FROM core_videos where status=0)-(SELECT MIN(id) FROM core_videos where status=0))+" +
            "(SELECT MIN(id) FROM core_videos where status=0)) AS id) " +
            "AS t2 " +
            "WHERE t1.id >= t2.id and t1.id <> #{id} and t1.status=0 " +
            "ORDER BY t1.id LIMIT 0,5")
    List<VideoInfoDto> next(@Param("id") long id);
    @Update("update core_videos set heartCount=heartCount+(#{num}) where id=#{videoId}")
    long addHeart(@Param("videoId") long videoId,@Param("num") int num);
    @SelectProvider(type = VideoSqlProvider.class,method = "nearby")
    List<VideoInfoDto> nearby(@Param("longitude") double longitude,@Param("latitude") double latitude,@Param("distance") float distance);
    @Select("select videoTbl.*,t3.nickName as userNickName,t3.avatarUrl as userAvatarUrl,(select count(*) from core_followers as t2 where t2.followerUserId = t1.userId and t2.userId=#{userId}) as " +
            "isFollowedMe from core_followers as t1 inner join core_users as t3 on t3.id = t1.userId inner join core_videos as " +
            "videoTbl on videoTbl.userId =t1.userId where t1.followerUserId=#{userId} and videoTbl.status=0 order by videoTbl.createTime desc")
    List<VideoInfoDto> myFollowedVideos(@Param("userId") long userId);
    @Select("select * from core_videos where userId=#{userId} order by createTime desc")
    List<VideoInfoDto> videosByUser(@Param("userId") long userId);
    @Select("select video.* from core_videos as video inner join core_hearts as heart on video.id = heart.resourceOwnerId where heart.userId=#{userId}")
    List<VideoInfoDto> videosByHearted(@Param("userId") long userId);
    @UpdateProvider(type = VideoSqlProvider.class,method = "updateVideoField")
    long updateVideoField(@Param("fields") String fields,@Param("id") long id);
    @Select("select v.videoUrl,v.title,v.tags,u.avatarUrl,u.nickName from core_videos v inner join core_users u on v.userId=u.id where v.id=#{id}")
    ShareDto doShare(@Param("id")Long id);

    @Select("select linkPets from core_videos where id=#{id}")
    String findLinkPets(@Param("id")Long id);

    //后台首页

    @SelectProvider(type = VideoSqlProvider.class,method = "findIncreasedVideoAd")
    Long findIncreasedVideoAd(@Param("input") int input);
    //待审核
    @SelectProvider(type = VideoSqlProvider.class,method = "findIncreasedVideoingAd")
    Long findIncreasedVideoingAd(@Param("input") int input);


    @Select("select count(id) from core_videos where status=0 and date(createTime)=curDate()-1")
    Long everyIncreasedVideos();

    //审核相关

    @Update("update core_videos set status=0 where id=#{id}")
    int passVideoReview(@Param("id")Long id);
    @Update("update core_videos set status=2,rejectMsg='此视频涉嫌违规' where id=#{id}")
    int blockVideoReview(@Param("id")Long id);
    @Update("update core_videos set status=4 where id=#{id}")
    int peopleVideoReview(@Param("id")Long id);

    //定时任务
    @Select("select id,userId,playCount,heartCount,shareCount from core_videos where status=0 ")
    List<VideoHeartAndPlayCountDto> findHeartAndPlayCount();

}
