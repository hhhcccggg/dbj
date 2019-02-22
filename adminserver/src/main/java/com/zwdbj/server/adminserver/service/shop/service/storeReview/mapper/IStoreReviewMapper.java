package com.zwdbj.server.adminserver.service.shop.service.storeReview.mapper;

import com.zwdbj.server.adminserver.service.shop.service.store.model.ReviewStoreInput;
import com.zwdbj.server.adminserver.service.shop.service.storeReview.model.BusinessSellerReviewModel;
import com.zwdbj.server.adminserver.service.shop.service.storeReview.model.StoreReviewAddInput;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface IStoreReviewMapper {
    @Select("select * from shop_legalSubjectReviews where isDeleted=0")
    List<BusinessSellerReviewModel> findAllStoreReviews();
    @Update("update shop_legalSubjectReviews set identifyId=#{input.identifyId},title=#{input.title},keyId=#{input.keyId}," +
            "reviewData=#{input.reviewData},legalSubjectId=#{input.legalSubjectId} where id=#{id} and isDeleted=0")
    int modifyStoreReview(@Param("id") long id, @Param("input") StoreReviewAddInput input);
    @Insert("insert into shop_legalSubjectReviews(id,identifyId,keyId,title,reviewData,legalSubjectId) " +
            "values(#{id},#{input.identifyId},#{input.keyId},#{input.title},#{input.reviewData},#{input.legalSubjectId})")
    int addStoreReview(@Param("id") long id, @Param("input") StoreReviewAddInput input);
    @Delete("delete from shop_legalSubjectReviews where id=#{id}")
    int deleteStoreReview(@Param("id") long id);
    @Update("update shop_legalSubjectReviews set status=#{status},rejectMsg=#{input.rejectMsg} where legalSubjectId=#{legalSubjectId}")
    int reviewStore(@Param("legalSubjectId") long legalSubjectId,@Param("input") ReviewStoreInput input,@Param("status")int status);

    @Update("update shop_legalSubjectReviews set deleteTime=true,deleteTime=now() where id=#{id}")
    int notRealDeleteStoreReview(@Param("id") long id);
    @Select("select * from shop_legalSubjectReviews where legalSubjectId=#{legalSubjectId} and isDeleted=0")
    List<BusinessSellerReviewModel> getStoreReviewById(@Param("legalSubjectId") long legalSubjectId);

}
