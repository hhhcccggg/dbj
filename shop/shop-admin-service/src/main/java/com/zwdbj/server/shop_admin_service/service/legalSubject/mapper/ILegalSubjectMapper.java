package com.zwdbj.server.shop_admin_service.service.legalSubject.mapper;

import com.zwdbj.server.probuf.middleware.mq.QueueWorkInfoModel;
import com.zwdbj.server.shop_admin_service.service.legalSubject.model.LegalSubjectModel;
import org.apache.ibatis.annotations.*;

@Mapper
public interface ILegalSubjectMapper {
    @Insert("insert into core_legalSubjects(id,name,leagalRepresentativeName,leagalRepresentativeID,cityId,contactName,contactPhone,type) " +
            "values(#{data.legalSubjectId},#{data.name},#{data.leagalRepresentativeName},#{data.leagalRepresentativeID},#{data.cityId}," +
            "#{data.contactPerson},#{data.phone},#{data.legalType})")
    int addLegalSubject(@Param("data") QueueWorkInfoModel.QueueWorkShopLegalSubjectData data);
    @Update("update core_legalSubjects set name=#{data.name},leagalRepresentativeName=#{data.leagalRepresentativeName}," +
            "leagalRepresentativeID=#{data.leagalRepresentativeID},cityId=#{data.cityId},contactName=#{data.contactPerson}," +
            "contactPhone=#{data.phone},type=#{data.legalType} where id=#{data.legalSubjectId}")
    int modifyBasicLegalSubject(@Param("data") QueueWorkInfoModel.QueueWorkShopLegalSubjectData data);

    @Update("update core_legalSubjects set isDeleted=true,deleteTime=now() where id=#{id}")
    int delLegalSubject(@Param("id")long id);

    @Select("select * from core_legalSubjects where id=#{id} and isDeleted=0")
    LegalSubjectModel getLegalSubjectById(@Param("id")long id);
}
