package com.zwdbj.server.mobileapi.service.store.mapper;

import com.zwdbj.server.mobileapi.service.store.model.StoreModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface IStoreMapper {

    /**
     *
     * 根据D查询店铺
     * @param id
     * @return
     */
    @Select("select *  from shop_stores where id=#{id} and isDeleted=0 limit 1")
    StoreModel selectById(@Param("id") long id);
}
