package com.zwdbj.server.shop_admin_service.service.shopdetail.mapper;

import com.zwdbj.server.shop_admin_service.service.shopdetail.model.OpeningHours;
import com.zwdbj.server.shop_admin_service.service.shopdetail.model.StoreDto;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.mapstruct.Mapper;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Mapper
public interface ShopDetailMapper {
    @Select("select id,name,contactName,contactPhone from shop_stores where id=#{storeId}")
    StoreDto findStoreDetail(@PathVariable("storeId") long storeId);

    @Select("select day,storeId,openTime,colseTime from o2o_offlineStoreOpeningHours where storeId=#{storeId}")
    List<OpeningHours> findOpeningHours(@PathVariable("storeId") long storeId);

    //    @Select("select storeId,serviceScopeId,notes")
    @Update("update o2o_offlineStoreOpeningHours set day=#{openingHours.day}," +
            "openTime=#{openingHours.openTime},closeTime=#{openingHours.closeTime} " +
            "where id=#{openingHours.id} and storeId =#{openingHours.storeId}")
    Long modifyOpeningHours(@PathVariable("openingHours") OpeningHours openingHours);

    @Insert("insert into o2o_offlineStoreOpeningHours (id,storeId,day,openTime,closeTime) " +
            "#{id},#{storeId},#{openingHours.day},#{openingHours.openTime},#{openingHours.closeTime}")
    Long createOpeningHours(@PathVariable("id") long id, @PathVariable("storeId") long storeId,
                            @PathVariable("openingHours") OpeningHours openingHours);

    @Select("select address where storeId=#{storeId}")
    String showLocation(@PathVariable("storeId") long storeId);

}
