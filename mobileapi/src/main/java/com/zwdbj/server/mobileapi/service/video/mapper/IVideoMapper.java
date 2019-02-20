package com.zwdbj.server.mobileapi.service.video.mapper;


import com.zwdbj.server.mobileapi.service.share.model.ShareDto;
import com.zwdbj.server.mobileapi.service.shop.comments.model.CommentVideoInfo;
import com.zwdbj.server.mobileapi.service.video.model.*;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface IVideoMapper {
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
    long publicVideo(@Param("id") long id, @Param("userId") long userId, @Param("dataInput") VideoPublishInput dataInput);

    @Insert("insert into core_videos(id,title,coverImageUrl,videoUrl," +
            "longitude,latitude,userId," +
            "coverImageWidth,coverImageHeight,address," +
            "firstFrameUrl,firstFrameWidth,firstFrameHeight,type) values(#{id},'店铺服务评价'," +
            "#{dataInput.coverImageUrl}," +
            "#{dataInput.videoUrl}," +
            "#{dataInput.longitude}," +
            "#{dataInput.latitude}," +
            "#{userId}," +
            "#{dataInput.coverImageWidth}," +
            "#{dataInput.coverImageHeight}," +
            "#{dataInput.address}," +
            "#{dataInput.firstFrameUrl}," +
            "#{dataInput.firstFrameWidth}," +
            "#{dataInput.firstFrameHeight}," +
            "'SHOPCOMMENT')")
    long publishCommentVideo(@Param("id") long id, @Param("dataInput") CommentVideoInfo dataInput, @Param("userId") long userId);


    @SelectProvider(type = VideoSqlProvider.class, method = "videoByIds")
    List<VideoInfoDto> listIds(@Param("ids") String ids);

    @SelectProvider(type = VideoSqlProvider.class, method = "hotVideo")
    List<VideoInfoDto> listHot(@Param("conditionValue") String conditionValue);

    @Select("select * from core_videos where status=0 order by createTime desc")
    List<VideoInfoDto> listLatest();

    @Select("select * from core_videos where tags like concat('%',#{tags},'%') order by heartCount desc")
    List<VideoInfoDto> listByTag(@Param("tags") String tag);

    @Select("select * from core_videos where status=0 and (tags like concat('%',#{tags},'%')) order by heartCount desc")
    List<VideoInfoDto> listByTagName1(@Param("tags") String tagName);

    @Select("select * from core_videos where status=0 and (tags like concat('%',#{tags},'%')) order by createTime desc")
    List<VideoInfoDto> listByTagName2(@Param("tags") String tagName);

    @Select("select * from core_videos where id=#{id}")
    VideoDetailInfoDto video(@Param("id") long id);

    //    @Select("SELECT *  FROM core_videos AS t1 " +
//            "JOIN (SELECT ROUND(RAND() * ((SELECT MAX(id) FROM core_videos where status=0 order by id)-(SELECT MIN(id) FROM core_videos where status=0 order by id))+" +
//            "(SELECT MIN(id) FROM core_videos where status=0 order by id)) AS id) " +
//            "AS t2 " +
//            "WHERE t1.id >= t2.id and t1.id <> #{id} and t1.status=0 order by t1.id " +
//            "LIMIT 0,5")
//    List<VideoInfoDto> next(@Param("id") long id);
    //TODO 优化
    @Select("select * from core_videos where id<>#{id} and status=0 order by rand() limit 0,5")
    List<VideoInfoDto> next(@Param("id") long id);

    @Update("update core_videos set heartCount=heartCount+(#{num}) where id=#{videoId}")
    long addHeart(@Param("videoId") long videoId, @Param("num") int num);

    @Select("select userId from core_videos where id=#{id}")
    Long findUserIdByVideoId(@Param("id") Long id);

    @SelectProvider(type = VideoSqlProvider.class, method = "nearby")
    List<VideoInfoDto> nearby(@Param("longitude") double longitude, @Param("latitude") double latitude, @Param("distance") float distance);

    @Select("select videoTbl.*,t3.nickName as userNickName,t3.avatarUrl as userAvatarUrl,(select count(*) from core_followers as t2 where t2.followerUserId = t1.userId and t2.userId=#{userId}) as " +
            "isFollowedMe from core_followers as t1 inner join core_users as t3 on t3.id = t1.userId inner join core_videos as " +
            "videoTbl on videoTbl.userId =t1.userId where t1.followerUserId=#{userId} and videoTbl.status=0 order by videoTbl.createTime desc")
    List<VideoDetailInfoDto> myFollowedVideos(@Param("userId") long userId);

    @Select("select * from core_videos where userId=#{userId} order by createTime desc")
    List<VideoInfoDto> videosByUser(@Param("userId") long userId);

    @Select("select * from core_videos where userId=#{userId} and status=0 order by createTime desc")
    List<VideoInfoDto> videosByUser1(@Param("userId") long userId);

    @Select("select video.* from core_videos as video inner join core_hearts as heart on video.id = heart.resourceOwnerId where heart.userId=#{userId}")
    List<VideoInfoDto> videosByHearted(@Param("userId") long userId);

    @UpdateProvider(type = VideoSqlProvider.class, method = "updateVideoField")
    long updateVideoField(@Param("fields") String fields, @Param("id") long id);

    @Select("select v.status, v.coverImageUrl,v.videoUrl,v.title,v.tags,u.avatarUrl as avatarUrl,u.nickName as nickName " +
            "from core_videos v inner join core_users u on v.userId=u.id where v.id=#{id}")
    ShareDto doShare(@Param("id") Long id);

    @Update("update core_videos set shareCount=shareCount+1 where id=#{id}")
    int addShareCount(@Param("id") Long id);

    @Select("select linkPets from core_videos where id=#{id}")
    String findLinkPets(@Param("id") Long id);

    @Delete("delete from core_videos where id=#{id} and userId=#{userId}")
    Long deleteVideo(@Param("id") Long id, @Param("userId") Long userId);

    @Select("select tipcount from core_videos where id=#{id}")
    long searchTipCount(@Param("id") Long videoId);

    @Update("update core_videos set tipCount=tipCount+1 where id=#{id}")
    int addTipCount(@Param("id") Long videoId);

    /**
     * 用户是否为每天的首次发布视频
     */
    @Select("select count(id) from core_videos where userId=#{userId} and to_days(createTime)=to_days(now())")
    int isFirstPublicVideo(@Param("userId")long userId);

    @Select("select * from core_videos where find_in_set(#{petId},`linkPets`) and isDeleted=0 and status=0 order by createTime desc")
    List<VideoInfoDto> getPetsVideo(@Param("petId") long petId);

    @Select("select ifnull(sum(heartCount),0) from core_videos where userId=#{userId} and isDeleted=0 and status=0")
    Long getUserVideosHeartCount(@Param("userId") long userId);
    @Select("select count(id) from core_videos where userId=#{userId} and status=0")
    int userVideosNum(@Param("userId")long userId);

    /**
     * 首页测试sql
     * @return
     */
    @Select("SELECT *" +
            "FROM " +
            "core_videos " +
            "ORDER BY " +
            "rand() " +
            "limit 10")
    List<VideoMain> mainVideo();

    /**
     * 查询ES所需数据
     * @return
     */
    @Select("SELECT cv.id,CONCAT(cv.latitude, ',', cv.longitude) AS location,cv.title,cv.coverImageUrl,cv.coverImageWidth,cv.coverImageHeight,cv.firstFrameUrl," +
            "cv.firstFrameWidth,cv.firstFrameHeight,cv.videoUrl,cv.linkPets,cv.tags,cv.`status`,cv.rejectMsg,cv.playCount,cv.commentCount," +
            "cv.heartCount,cv.shareCount,cv.userId,cv.musicId,cv.linkProductCount,cv.tipCount,cv.type,cu.nickName as userNickName," +
            "cu.avatarUrl as userAvatarUrl FROM core_videos AS cv LEFT  JOIN core_users AS cu ON cv.userId = cu.id")
    List<Map<String,String>> selectES();
}
