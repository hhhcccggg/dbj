package com.zwdbj.server.adminserver.service.shop.service.productAttriLinks.mapper;

import com.zwdbj.server.adminserver.service.shop.service.productAttriLinks.model.ProductAttriLinks;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface IProductAttriLinksMapper {
    @Insert("insert into shop_productAttriLinks (id,productId,attriId,attriValueId,extraData)" +
            "values(#{id},#{productAttriLinks.productId},#{productAttriLinks.attriId}," +
            "#{productAttriLinks.attriValueId},#{productAttriLinks.extraData})")
    Long createProductAttriLinks(@Param("id") Long id, @Param("productAttriLinks") ProductAttriLinks productAttrLinks);

    @Update("update shop_productAttriLinks set isDeleted=1,deleteTime=now() where id=#{id}")
    Long deleteById(@Param("id") Long id);

    @Update("update shop_productAttriLinks set productId=#{productAttriLinks.productId}," +
            "attriId=#{productAttriLinks.attriId},attriValueId=#{productAttriLinks.attriValueId}," +
            "extraData=#{productAttriLinks.extraData} where id=#{productAttriLinks.id}")
    Long updateProductAttriLinks(@Param("productAttriLinks") ProductAttriLinks productAttrLinks);

    @Select("select * from shop_productAttriLinks where isDeleted =0 order by createTime")
    List<ProductAttriLinks> select();
}
