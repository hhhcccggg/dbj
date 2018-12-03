package com.zwdbj.server.shop_admin_service.shoppingCart.mapper;

import com.zwdbj.server.shop_admin_service.shoppingCart.model.ProductCartAddInput;
import com.zwdbj.server.shop_admin_service.shoppingCart.model.ProductCartModel;
import com.zwdbj.server.shop_admin_service.shoppingCart.model.ProductCartModifyInput;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface IShoppingCartMapper {
    @Select("select * from shop_productCarts")
    List<ProductCartModel> findAllShoppingCarts();
    @Select("select * from shop_productCarts where id=#{id}")
    ProductCartModel getShoppingCartById(@Param("id")long id);
    @Update("update shop_productCarts set deleteTime=true,deleteTime=now() where id=#{id}")
    int notRealDeleteShoppingCart(@Param("id") long id);
    @Insert("insert into shop_productCarts(id,userId,sellerId,productId,productskuId,item,price,ip,ua,expireTime) " +
            "values(#{id},#{input.userId},#{input.sellerId},#{input.productId},#{input.productskuId},#{input.item}," +
            "#{input.price},#{input.ip},#{input.ua},#{input.expireTime})")
    int addShoppingCart(@Param("id")long id,@Param("input") ProductCartAddInput input);
    @Update("update shop_productCarts set userId=#{input.userId},sellerId=#{input.sellerId},productId=#{input.productId}," +
            "productskuId=#{input.productskuId},item=#{input.item},price=#{input.price},ip=#{input.ip},ua=#{input.ua}," +
            "expireTime=#{input.expireTime} where id=#{id}")
    int modifyShoppingCart(@Param("id")long id, @Param("input")ProductCartModifyInput input);
}
