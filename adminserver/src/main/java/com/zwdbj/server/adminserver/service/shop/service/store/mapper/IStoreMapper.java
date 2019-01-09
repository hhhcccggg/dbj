package com.zwdbj.server.adminserver.service.shop.service.store.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface IStoreMapper {

    /**
     * TODO 目前一对一后期可能会变,
     * 根据商户ID查询店铺
     * @param legalSubjectId
     * @return
     */
    @Select("select id  from shop_stores where legalSubjectId=#{legalSubjectId} and isDeleted=0 limit 1")
    Long selectByLegalSubjectId(@Param("legalSubjectId") long legalSubjectId);
}
