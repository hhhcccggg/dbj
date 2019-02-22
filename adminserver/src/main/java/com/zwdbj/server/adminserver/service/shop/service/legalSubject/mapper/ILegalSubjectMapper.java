package com.zwdbj.server.adminserver.service.shop.service.legalSubject.mapper;

import com.zwdbj.server.adminserver.service.shop.service.legalSubject.model.*;
import com.zwdbj.server.adminserver.service.shop.service.store.model.ReviewStoreInput;
import com.zwdbj.server.adminserver.service.userTenant.model.ModifyUserTenantInput;
import com.zwdbj.server.adminserver.service.userTenant.model.UserTenantInput;
import com.zwdbj.server.probuf.middleware.mq.QueueWorkInfoModel;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ILegalSubjectMapper {
    @Insert("insert into shop_legalSubjects(id,name,leagalRepresentativeName,leagalRepresentativeID,cityId,contactName,contactPhone,type) " +
            "values(#{id},#{data.name},#{data.leagalRepresentativeName},#{data.leagalRepresentativeID},#{data.cityId}," +
            "#{data.username},#{data.phone},#{data.legalType})")
    int addLegalSubject(@Param("id")long id,@Param("data") UserTenantInput input);
    @Update("update shop_legalSubjects set name=#{data.name},leagalRepresentativeName=#{data.leagalRepresentativeName}," +
            "leagalRepresentativeID=#{data.leagalRepresentativeID},cityId=#{data.cityId},contactName=#{data.username}," +
            "contactPhone=#{data.phone},type=#{data.legalType} where id=#{id}")
    int modifyBasicLegalSubject(@Param("id")long id,@Param("data") ModifyUserTenantInput input);

    @Update("update shop_legalSubjects set isDeleted=true,deleteTime=now() where id=#{id}")
    int delLegalSubject(@Param("id") long id);

    @Select("select * from shop_legalSubjects where id=#{id} and isDeleted=0")
    LegalSubjectModel getLegalSubjectById(@Param("id") long id);

    @SelectProvider(type = LegalSubjectSQLProvider.class ,method = "searchSql")
    List<LegalSubjectModel> getLegalSubjects(@Param("input") LegalSubjectSearchInput input);
    @SelectProvider(type = LegalSubjectSQLProvider.class ,method = "searchUnReviewedSql")
    List<LegalSubjectModel> getUnReviewedLegalSubjects(@Param("input") LegalSubjectSearchInput input);
    @Update("update shop_legalSubjects set reviewed=#{input.reviewed},rejectMsg=#{input.rejectMsg} where id=#{id}")
    int verityUnReviewedLegalSubject(@Param("id") long id, @Param("input") ReviewStoreInput input);

    @Select("select * from shop_legalSubjectReviews where legalSubjectId=#{legalSubjectId}")
    List<LegalSubjectReviewModel> getReviewsByLegalSubjectId(@Param("legalSubjectId") long legalSubjectId);
    @Insert("insert into shop_stores(id,`name`,`type`,categoryId,cityId,expireTime,contactName,contactPhone,legalSubjectId) " +
            "values(#{id},#{input.name},#{input.storeType},#{input.categoryId},#{input.cityId},#{input.expireTime}," +
            "#{input.username},#{input.phone},#{legalSubjectId})")
    int addShopStore(@Param("id")long id,@Param("input")UserTenantInput input,@Param("legalSubjectId")long legalSubjectId);

    @Update("update shop_stores set name=#{input.name},type=#{input.storeType},categoryId=#{input.categoryId}," +
            "cityId=#{input.cityId},expireTime=#{input.expireTime},contactName=#{input.username}," +
            "contactPhone=#{input.phone} where legalSubjectId=#{legalSubjectId}")
    int modifyShopStore(@Param("input")ModifyUserTenantInput input,@Param("legalSubjectId")long legalSubjectId);
    @Update("update shop_stores set isDeleted=1,deleteTime=now() where legalSubjectId=#{legalSubjectId}")
    int delShopStore(@Param("legalSubjectId")long legalSubjectId);


    @Select("select l.leagalRepresentativeName,l.contactName,l.contactPhone,l.leagalRepresentativeID,l.type as legalType,l.cityId,s.type as storeType," +
            "s.categoryId,s.expireTime from shop_legalSubjects l left join shop_stores s on s.legalSubjectId=l.id " +
            "where l.id=#{id}")
    ShopTenantModel getDetailTenant(@Param("id") long legalSubjectId);

    //根据id查询商家需要审核的资料
    @Select("select * from shop_legalSubjectReviews where id=#{id}")
    LegalSubjectReviewModel getLegalSubjectReviewById(@Param("id") long id);

    //审核商家需要审核的资料
    @Update("update shop_legalSubjectReviews set `status`=#{input.status},rejectMsg=#{input.rejectMsg} where id=#[id]")
    int verityLegalSubjectReview(@Param("id")long id,@Param("input")LegalSubjectReviewVerityInput input);

    @Update("update shop_legalSubjects set `status`=#{status} where id=#{id} and `status`<>#{status}")
    int updateStatusById(@Param("id") long legalSubjectId,@Param("status")int status);
}
