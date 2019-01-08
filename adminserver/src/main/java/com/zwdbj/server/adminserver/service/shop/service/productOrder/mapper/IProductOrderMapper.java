package com.zwdbj.server.adminserver.service.shop.service.productOrder.mapper;

import com.zwdbj.server.adminserver.service.shop.service.productOrder.model.ProductOrderDetailModel;
import com.zwdbj.server.adminserver.service.shop.service.productOrder.model.ProductOrderInput;
import com.zwdbj.server.adminserver.service.shop.service.productOrder.model.ProductOrderModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

@Mapper
public interface IProductOrderMapper {

    @SelectProvider(type = ProductOrderSqlProvider.class,method = "getStoreOrders")
    List<ProductOrderModel> getStoreOrders(@Param("storeId")long storeId, @Param("input")ProductOrderInput input);
    @Select("select o.*,oi.productId,oi.productskuId,oi.num,oi.title,oi.price from shop_productOrders o " +
            "left join shop_productOrderItems oi on oi.orderId=o.id where o.id=#{id}")
    ProductOrderDetailModel getOrderById(@Param("id")long id);
}
