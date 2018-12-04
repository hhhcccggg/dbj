package com.zwdbj.server.shop_admin_service.storeReview.mapper;

import com.zwdbj.server.shop_admin_service.storeReview.model.BusinessSellerReviewModel;
import com.zwdbj.server.shop_admin_service.storeReview.model.StoreReviewAddInput;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface IStoreReviewMapper {
    @Select("select * from shop_offlineStoreReviews where isDeleted=0")
    List<BusinessSellerReviewModel> findAllStoreReviews();
    @Update("update shop_offlineStoreReviews set(identifyId=#{input.identifyId},title=#{input.title}," +
            "reviewData=#{input.reviewData},businessSellerId=#{input.businessSellerId} where id=#{id}) and isDeleted=0")
    int modifyStoreReview(@Param("id")long id, @Param("input") StoreReviewAddInput input);
    @Insert("insert into shop_offlineStoreReviews(id,identifyId,title,reviewData,businessSellerId) " +
            "values(#{id},#{input.identifyId},#{input.title},#{input.reviewData},#{input.businessSellerId})")
    int addStoreReview(@Param("id") long id,@Param("input") StoreReviewAddInput input);
    @Delete("delete from shop_offlineStoreReviews where id=#{id}")
    int deleteStoreReview(@Param("id") long id);
    @Update("update shop_offlineStoreReviews set deleteTime=true,deleteTime=now() where id=#{id}")
    int notRealDeleteStoreReview(@Param("id") long id);
    @Select("select * from shop_offlineStoreReviews where businessSellerId=#{businessSellerId}")
    BusinessSellerReviewModel getStoreReviewById(@Param("businessSellerId") long businessSellerId);

}
