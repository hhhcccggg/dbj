package com.zwdbj.server.adminserver.service.shop.service.store.mapper;

import com.zwdbj.server.adminserver.service.shop.service.store.model.StoreDetailDto;
import com.zwdbj.server.adminserver.service.shop.service.store.model.StoreInfo;
import com.zwdbj.server.adminserver.service.shop.service.store.model.StoreSearchInput;
import com.zwdbj.server.adminserver.service.shop.service.store.model.StoreSimpleInfo;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface IStoreMapper {

    /**
     * TODO 目前一对一后期可能会变,
     * 根据商户ID查询店铺
     *
     * @param legalSubjectId
     * @return
     */
    @Select("select * from shop_stores where legalSubjectId=#{legalSubjectId} and isDeleted=0 limit 1")
    StoreSimpleInfo selectByLegalSubjectId(@Param("legalSubjectId") long legalSubjectId);

    @Select("select * from shop_stores  where id=#{storeId}")
    StoreInfo selectByStoreId(@Param("storeId") long storeId);

    @SelectProvider(type = StoreSqlProvider.class,method = "searchStore")
    List<StoreSimpleInfo> searchStore(@Param("input")StoreSearchInput input);

    @Select("select id from core_user_tenants where legalSubjectId=#{legalSubjectId} and isDeleted=0")
    long selectTenantId(@Param("legalSubjectId") long legalSubjectId);
    @Update("update shop_stores set `status`=#{status} where id=#{storeId} and `status`<>#{status}")
    int updateStoreStatus(@Param("storeId") long storeId,@Param("status")int status);
}
