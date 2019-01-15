package com.zwdbj.server.mobileapi.service.legalSubject.mapper;

import com.zwdbj.server.mobileapi.service.legalSubject.model.LegalSubjectModel;
import org.apache.ibatis.annotations.*;


@Mapper
public interface ILegalSubjectMapper {

    @Select("select * from shop_legalSubjects where id=#{id} and isDeleted=0")
    LegalSubjectModel getLegalSubjectById(@Param("id") long id);
}
