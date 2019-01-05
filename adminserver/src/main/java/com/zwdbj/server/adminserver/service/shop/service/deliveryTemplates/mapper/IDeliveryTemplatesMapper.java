package com.zwdbj.server.adminserver.service.shop.service.deliveryTemplates.mapper;

import com.zwdbj.server.adminserver.service.shop.service.deliveryTemplates.model.DeliveryTemplatesModel;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface IDeliveryTemplatesMapper {
    @Select("select id,createTime,isDeleted,deleteTime,isManualData,sellerId,name," +
            "billType,deliveryTemplateScopeId from shop_deliveryTemplates")
    List<DeliveryTemplatesModel> findAllDeliveryTemplates();
    @Select("select id,createTime,isDeleted,deleteTime,isManualData,sellerId,name," +
            "billType,deliveryTemplateScopeId from shop_deliveryTemplates where id=#{id}")
    DeliveryTemplatesModel getDeliveryTemplatesById(@Param("id") long id);

    @Insert("INSERT INTO shop_deliveryTemplates(id,sellerId,name,billType,deliveryTemplateScopeId) VALUES " +
            "#{id}" +
            "#{model.createTime}" +
            "#{model.isDeleted}" +
            "#{model.deleteTime}" +
            "#{model.isManualData}" +
            "#{model.sellerId}" +
            "#{model.name}" +
            "#{model.billType}" +
            "#{model.deliveryTemplateScopeld} " )
    int addDeliveryTemplates(@Param("id") long id, @Param("model") DeliveryTemplatesModel model);

    @Delete("DELETE FROM shop_deliveryTemplates where id=#{id}")
    int deleteDeliveryTemplatesById(@Param("id") long id);

    @Update("update shop_deliveryTemplates set sellerId=#{model.sellerId},name=#{model.name}," +
            "billType=#{model.billType},deliveryTemplateScopeId=#{model.deliveryTemplateScopeId}" +
            " where id=#{model.id}")
    int updateDeliveryTemplates(@Param("model") DeliveryTemplatesModel model);

}
