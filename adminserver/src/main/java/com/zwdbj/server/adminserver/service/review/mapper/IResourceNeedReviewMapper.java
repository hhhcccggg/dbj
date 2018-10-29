package com.zwdbj.server.adminserver.service.review.mapper;

import com.zwdbj.server.adminserver.service.review.model.ReviewModel;
import org.apache.ibatis.annotations.*;

@Mapper
public interface IResourceNeedReviewMapper {

    @Select("select count(id) from core_resourceNeedReviews where resContent=#{resContent}")
    int findByResContent(@Param("resContent")String resContent);


    @Select("select count(id) from core_resourceNeedReviews where resContent=#{resContent} and dataId=0")
    int findByResContent2(@Param("resContent")String resContent);
    @Select("select id from core_resourceNeedReviews where resContent=#{resContent}")
    Long getIdByKey(@Param("resContent")String resContent);

    @Insert("insert into core_resourceNeedReviews(id,resContent,reviewResultType) values(#{id},#{resContent},#{reviewResultType})")
    int newReview(@Param("id")Long id,@Param("resContent")String resContent,@Param("reviewResultType")String reviewResultType);

    @Update("update core_resourceNeedReviews set reviewResultType=#{reviewResultType} where id=#{id}")
    int updateReview(@Param("id")Long id,@Param("reviewResultType")String reviewResultType);

    @Select("select * from core_resourceNeedReviews where id=#{id}")
    ReviewModel selectReviewById(@Param("id")Long id);

    @Delete("delete from core_resourceNeedReviews where id=#{id}")
    int deleteReview(@Param("id")Long id);

    @Insert("insert into core_resourceNeedReviews(id,resContent,dataId,dataType,resType) values(#{id},#{resContent},#{dataId},#{dataType},#{resType})")
    int newReviewResData(@Param("id")Long id,@Param("resContent")String resContent,
                         @Param("dataId") Long dataId,@Param("dataType") int dataType,@Param("resType") int resType);

    @Update("update core_resourceNeedReviews set dataId=#{dataId},dataType=#{dataType},resType=#{resType} where id=#{id}")
    int updateReviewResData(@Param("id")Long id,@Param("dataId")Long dataId,@Param("dataType")int dataType,@Param("resType")int resType);

    @Delete("delete from core_resourceNeedReviews where resContent=#{resContent}")
    int deleteByInputKey(@Param("resContent")String resContent);

    @Delete("delete from core_resourceNeedReviews where resType=0")
    Long deleteResourceNeedReview();
}
