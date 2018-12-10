package com.zwdbj.server.mobileapi.service.user.mapper;

import com.zwdbj.server.mobileapi.service.user.model.*;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface IUserMapper {
    @Select("select * from core_users where phone=#{phone}")
    UserModel findUserByPhone(@Param("phone") String phone);
    @Select("select * from core_users where username=#{username}")
    UserModel findUserByUserName(@Param("username") String username);
    @Select("select * from core_users where id=#{id}")
    UserModel findUserById(@Param("id") long id);
    @Select("select * from core_users where thirdOpenId=#{openId} and loginType=#{type}")
    UserModel findUserByOpenId(@Param("openId") String openId,@Param("type") int type);

    @Select("select * from core_users where username=#{username} and password=#{password}")
    UserModel findUserByUserPwd(@Param("username") String username, @Param("password") String password);
    @Insert("insert into core_users(id,phone,username,nickName,avatarUrl,IsPhoneVerification) values(#{id},#{phone},#{username},'爪子用户'," +
            "'http://res.pet.zwdbj.com/default_avatar.png',true)")
    long regByPhone(@Param("phone") String phone,@Param("id") long id,@Param("username") String username);
    @Insert("insert into core_users(id,username,nickName,avatarUrl,loginType,thirdOpenId,sex) " +
            "values(#{id},#{username},#{input.nickName},#{input.avaterUrl},#{input.thirdType},#{input.openUserId},#{input.sex})")
    long regByOpenId(@Param("id") long id,@Param("username") String username,@Param("input") BindThirdPartyAccountInput input);
    @Update("update core_users set totalHearts=totalHearts+(#{num}) where id=#{id}")
    long addHeart(@Param("id") long id,@Param("num") int num);
    @Select("SELECT *, (select count(*) from core_pets as pet where pet.userId = u.id) as petCount," +
            "(select count(*) from core_videos as vd where vd.userId = u.id) as videoCount," +
            "(select count(*) from core_livings as li where li.userId = u.id) as liveCount FROM " +
            "dbj_server_db.core_users as u where u.id=#{userId}")
    UserDetailInfoDto getUserDetail(@Param("userId") long userId);
    @UpdateProvider(type = UserSqlProvider.class,method = "updateInfo")
    long updateInfo(@Param("userId") long userId, @Param("input") UpdateUserInfoInput input);


    @UpdateProvider(type = UserSqlProvider.class,method = "updateField")
    long updateField(@Param("fields") String fields,@Param("id") long id);

    // 关注
    @Select("select count(*) from core_followers where userId=#{userId} and followerUserId=#{followerUserId}")
    int checkFollowed(@Param("userId") long userId,@Param("followerUserId") long followerUserId);
    @Insert("insert into core_followers(id,userId,followerUserId) values(#{id},#{userId},#{followerUserId})")
    long follow(@Param("id") long id, @Param("userId") long userId,@Param("followerUserId") long followerUserId);
    @Delete("delete from core_followers where userId=#{userId} and followerUserId=#{followerUserId}")
    long unFollow(@Param("userId") long userId,@Param("followerUserId") long followerUserId);
    @Select("select t3.*,(select count(*) from core_followers as t2 where t2.userId = t1.followerUserId and t2.followerUserId=#{userId}) as isFollowedMe " +
            "from core_followers as t1 inner join core_users as t3 on t3.id = t1.followerUserId where t1.userId=#{userId}")
    List<FollowerUserInfoDto> myFollowers(@Param("userId") long userId);
    @Select("select t3.*,(select count(*) from core_followers as t2 where t2.followerUserId = t1.userId and t2.userId=#{userId}) as isFollowedMe " +
            "from core_followers as t1 inner join core_users as t3 on t3.id = t1.userId where t1.followerUserId=#{userId}")
    List<FollowerUserInfoDto> myFollowed(@Param("userId") long userId);
    @Select("select (select count(*) from core_followers where userId=#{input.toUserId} and followerUserId=#{input.userId}) as isFollowed, " +
            "(select count(*) from core_followers where followerUserId=#{input.toUserId} and userId=#{input.userId}) as isMyFollower  " +
            "from core_followers where ( userId=#{input.userId} and followerUserId=#{input.toUserId}) or (followerUserId=#{input.userId} and userId=#{input.toUserId})  limit 0,1")
    UserFollowInfoDto followStatusSearch(@Param("input") UserFollowInfoSearchInput input);

    @Select("select count(id) from core_users where username=#{username}")
    int userNameIsExist(@Param("username")String username);

    @Update("update core_livings set getFriends=getFriends+1 where id=#{id}")
    Long addFanCount(@Param("id") Long livingId);
    @Select("select nickName from core_users where id=#{id}")
    String getUserNickName(@Param("id") long id);

    //点赞
    @Select("select heart.id as id, " +
            " user.id as userId," +
            "video.id as videoId," +
            "video.userId as ownUserId," +
            "user.nickName as nickName, " +
            "user.avatarUrl as avatarUrl," +
            "video.title as title," +
            "heart.createTime as createTime" +
            " from core_hearts as heart inner join core_videos as video " +
            "on heart.resourceOwnerId = video.id inner join core_users as user " +
            "on heart.userId = user.id where video.userId=#{userId} and video.status=0 order by heart.createTime desc")
    List<AllHeartsForUserVideosMessageDto> getAllHeartsForMyVideos(@Param("userId") long userId);

    @Update("update core_users set loginType=1,thirdOpenId='' where id=#{id}")
    int updateThirdInfo(@Param("id")Long id);

    @Select("select count(id) as totalFans FROM core_followers where userId=#{userId}")
    Long findMyFansCount(@Param("userId")Long userId);
    @Select("select count(id) as totalMyFocuses FROM core_followers where followerUserId=#{userId}")
    Long findMyFocusesCount(@Param("userId")Long userId);

    @Select("select count(id) from core_users where phone=#{phone} and isManualData=true")
    int phoneIsTrue(@Param("phone") String phone);

}
