package com.zwdbj.server.shop_admin_service.setting.mapper;

import com.zwdbj.server.shop_admin_service.setting.model.BasicMsg;
import com.zwdbj.server.shop_admin_service.setting.model.ExtraServices;
import com.zwdbj.server.shop_admin_service.setting.model.OpeningHour;
import com.zwdbj.server.shop_admin_service.setting.model.ServiceScopes;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.mapstruct.Mapper;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Mapper
public interface SettingMapper {
    @Select("")
    BasicMsg selectBasicMsg();

    @Select("select day, openTime,closeTime from o2o_offlineStoreOpeningHours as o2o where o2o.offlineStoreId=#{offlineStoreId}")
    OpeningHour selectOpenHour(@PathVariable("offlineStoreId") long offlineStoreId);

    @Select("SELECT c.name as serviceScope,c.id as serviceScopeId from " +
            "dbj_server_db.core_categories as c,o2o_offlineStoreServiceScopes as o2o " +
            "where o2o.offlineStoreId=#{offlineStoreId} and c.id =o2o.serviceScopeId and o2o.status=0")
    List<ServiceScopes> selectServiceScopes(@PathVariable("offlineStoreId") long offlineStoreId);

    @Select("SELECT c.id as extraServicesId ,c.name=extraService from o2o_offlineStoreExtraServices as o, " +
            "dbj_server_db.core_categories as c where o.offlineStoreId=#{offlineStoreId} and o.status=0 and o.extraServiceId=c.id ")
    List<ExtraServices> selectExtraServices(@PathVariable("offlineStoreId") long offlineStoreId);

    @Update("update o2o_offlineStoreOpeningHours as o set o.openTime=#{openingHour.openTime}," +
            "o.closeTime=#{openingHour.closeTime},o.day=#{openingHour.day} where " +
            "o.offlineStoreId=#{offlineStoreId}")
    Long updateOpenHour(@PathVariable("offlineStoreId") long offlineStoreId, @PathVariable("openingHour") OpeningHour openingHour);
}
