package com.zwdbj.server.shop_admin_service.service.deliveryTemplateScopes.mapper;

import com.zwdbj.server.shop_admin_service.service.deliveryTemplateScopes.model.DeliveryTemplateScopesModel;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface IDeliveryTemplateScopesMapper {

    @Select("select * from shop_deliveryTemplateScopes")
    List<DeliveryTemplateScopesModel> findAllDeliveryTemplateScopes();

    @Select("select * from shop_deliveryTemplateScopes where id=#{id}")
    DeliveryTemplateScopesModel findDeliveryTemplateScopesById(@Param("id") long id);

    @Insert("insert into shop_deliveryTemplateScopes(id,deliveryArea,item,itemPrice,overItem,overItemPrice)" +
            " values(#{id},#{model.deliveryArea},#{model.item},#{model.itemPrice},#{model.overItem},#{model.overItemPrice})")
    int addDeliveryTemplateScopes(@Param("id") long id, @Param("model") DeliveryTemplateScopesModel model);

    @Delete("delete from shop_deliveryTemplateScopes where id=#{id}")
    int deleteDeliveryTemplateScopesById(@Param("id") long id);

    @Update("update shop_deliveryTemplateScopes set deliveryArea=#{model.deliveryArea},item=#{model.item},itemPrice=#{model.itemPrice}," +
            "overItem=#{model.overItem},overItemPrice=#{model.overItemPrice} where id=#{model.id}")
    int updateDeliveryTemplateScopes(@Param("model") DeliveryTemplateScopesModel model);
}
