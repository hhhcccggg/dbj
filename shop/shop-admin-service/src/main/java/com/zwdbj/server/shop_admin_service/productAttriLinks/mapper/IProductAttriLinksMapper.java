package com.zwdbj.server.shop_admin_service.productAttriLinks.mapper;

import com.zwdbj.server.shop_admin_service.productAttriLinks.model.ProductAttriLinks;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface IProductAttriLinksMapper {
    @Insert("insert into shop_productAttriLinks (id,productId,attriId,attriValueId,extraData)" +
            "values(#{id},#{productAttriLinks.productId},#{productAttriLinks.attriId}" +
            "#{productAttriLinks.attriValued},#{productAttriLinks.extraData})")
    Long createProductAttriLinks(@Param("productAttriLinks") ProductAttriLinks productAttrLinks);

    @Update("update shop_productAttriLinks set isDeleted=1,deleteTime=now() where id=#{id}")
    Long deleteById(@Param("id") Long id);

    @Update("update shop_productAttiLinks set productId=#{productAttriLinks}," +
            "attriId=#{productAttriLinks.attriId},attriValued=#{productAttriLinks.attriValued}," +
            "extraData=#{productAttriLinks.extraData} where id=#{productAttriLinks.id}")
    Long updateProductAttriLinks(@Param("productAttriLinks") ProductAttriLinks productAttrLinks);

    @Select("select * from shop_productAttriLinks where isDeleted =0 order by createTime")
    List<ProductAttriLinks> select();
}
