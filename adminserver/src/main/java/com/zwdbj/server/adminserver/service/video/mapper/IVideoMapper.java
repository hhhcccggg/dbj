package com.zwdbj.server.adminserver.service.video.mapper;


import com.zwdbj.server.adminserver.service.video.model.*;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface IVideoMapper {
    @SelectProvider(type = VideoSqlProvider.class, method = "searchComplainVideosSql")
    List<VideoInfoDto> complainVideosAd(@Param("model") AdVideoSearchComplainInput input);

    @Update("update core_videos set tags=#{tags} where id=#{id}")
    int addTagForVideo(@Param("id")long videoId,@Param("tags")String tags);

    @Update("update core_videos set status=#{model.status},rejectMsg=#{model.rejectMsg} where id=#{id}")
    Long verityAd(@Param("id") Long id, @Param("model") AdVideoVerityInput input);

    @SelectProvider(type = VideoSqlProvider.class, method = "verityListAd")
    List<AdVideoVerityInfoDto> verityListAd(@Param("model") AdVideoVerityInfoInput input);

    @Select("select c.*,r.title complainReason,r.description,v.complainCount,u.nickName,u.userName from core_complains as c " +
            "inner join core_complainReasons as r on c.reasonId=r.id " +
            "inner join core_videos as v on c.toResId=v.id " +
            "inner join core_users as u on c.fromUserId =u.id " +
            "where c.toResId=#{toResId}")
    List<AdVideoComplainInfoDto> complainInfoAd(@Param("toResId") Long toResId);

    @Update("update core_videos set status=#{model.treatment} where id=#{id}")
    Long doComplainInfoAd(@Param("id") Long toResId, @Param("model") AdVideoDoComplainInput input);

    @SelectProvider(type = VideoSqlProvider.class, method = "searchSql")
    List<VideoInfoDto> searchAd(@Param("model") SearchVideoAdInput model);

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

    @Select("select *,(select sum(tipCoin) from core_video_videoTipDetails where videoId=#{id}) " +
            "as tipCoins from core_videos where id=#{id}")
    VideoDetailInfoDto video(@Param("id") long id);
    @Select("select * from core_videos where id=#{id}")
    VideoInfoDto video2(@Param("id")long id);


    @UpdateProvider(type = VideoSqlProvider.class, method = "updateVideoField")
    long updateVideoField(@Param("fields") String fields, @Param("id") long id);

    @Select("select v.*,u.nickName from core_video_videoTipDetails as v," +
            "(select id,nickName from core_users) as u where v.videoId=#{videoId} and v.userId=u.id")
    List<VideoTipDetails> findVideoTipDetails(@Param("videoId") Long videoId);
    //后台首页

    @SelectProvider(type = VideoSqlProvider.class, method = "findIncreasedVideoAd")
    Long findIncreasedVideoAd(@Param("input") int input);

    //待审核
    @SelectProvider(type = VideoSqlProvider.class, method = "findIncreasedVideoingAd")
    Long findIncreasedVideoingAd(@Param("input") int input);


    @Select("select count(id) from core_videos where status=0 and date(createTime)=curDate()-1 and isManualData=false")
    Long everyIncreasedVideos();

    //审核相关

    @Update("update core_videos set status=0 where id=#{id}")
    int passVideoReview(@Param("id") Long id);

    @Update("update core_videos set status=2,rejectMsg='此视频涉嫌违规' where id=#{id}")
    int blockVideoReview(@Param("id") Long id);

    @Update("update core_videos set status=4 where id=#{id}")
    int peopleVideoReview(@Param("id") Long id);

    //定时任务
    @Select("select id,userId,playCount,heartCount,shareCount,commentCount from core_videos where status=0 and playCount<8000")
    List<VideoHeartAndPlayCountDto> findHeartAndPlayCount();

    @Select("select playCount from core_videos where id=#{id}")
    Long getVideoPlayCount(@Param("id") Long id);

    @Select("select heartCount from core_videos where id=#{id}")
    Long findVideoHeartCount(@Param("id") Long id);

    @SelectProvider(type = VideoSqlProvider.class, method = "findAllVideoNum")
    int findAllVideoNum(@Param("model") SearchVideoAdInput model);

    @SelectProvider(type = VideoSqlProvider.class, method = "searchSuperStarVideos")
    List<SuperStarVideos> searchSuperStarVideos(@Param("userId") long userId, @Param("rank") String rank, @Param("sort") String sort);

    /**
     * 查询ES
     * @return
     */
    @Select("SELECT cv.id,CONCAT(cv.latitude, ',', cv.longitude) AS location,cv.title,cv.coverImageUrl,cv.coverImageWidth,cv.coverImageHeight,cv.firstFrameUrl," +
            "cv.firstFrameWidth,cv.firstFrameHeight,cv.videoUrl,cv.linkPets,cv.tags,cv.`status`,cv.rejectMsg,cv.playCount,cv.commentCount," +
            "cv.heartCount,cv.shareCount,cv.userId,cv.musicId,cv.linkProductCount,cv.tipCount,cv.type,cu.nickName as userNickName," +
            "cu.avatarUrl as userAvatarUrl FROM core_videos AS cv LEFT  JOIN core_users AS cu ON cv.userId = cu.id where cv.id=#{id}")
    Map<String, String> selectByIdES(@Param("id") long id);
}
