package com.zwdbj.server.adminserver.service.shop.service.productOrder.mapper;

import com.zwdbj.server.adminserver.service.shop.service.productOrder.model.DeliverOrderInput;
import com.zwdbj.server.adminserver.service.shop.service.productOrder.model.ProductOrderDetailModel;
import com.zwdbj.server.adminserver.service.shop.service.productOrder.model.ProductOrderInput;
import com.zwdbj.server.adminserver.service.shop.service.productOrder.model.ProductOrderModel;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface IProductOrderMapper {

    @SelectProvider(type = ProductOrderSqlProvider.class,method = "getStoreOrders")
    List<ProductOrderModel> getStoreOrders(@Param("storeId")long storeId, @Param("input")ProductOrderInput input);
    @Select("select o.*,oi.productId,oi.productskuId,oi.num,oi.title,oi.price from shop_productOrders o " +
            "left join shop_productOrderItems oi on oi.orderId=o.id left join shop_products p on p.id=oi.productId where o.id=#{id}")
    ProductOrderDetailModel getOrderById(@Param("id")long id);
    @Update("update shop_productOrders set `status`='STATE_SELLER_DELIVERIED',statusStr='已发货',deliveryName=#{input.deliveryName},deliveryType=#{input.deliveryType}," +
            "deliveryCode=#{input.deliveryCode},updateTime=now() where id=#{id} and storeId=#{storeId} and `status`='STATE_BUYER_PAYED'")
    int deliverOrder(@Param("id") long orderId, @Param("input") DeliverOrderInput input,@Param("storeId")long storeId);
    @Update("update shop_productOrders set `status`='STATE_BUYER_DELIVERIED',statusStr='已签收',updateTime=now() " +
            " where id=#{id} and `status`='STATE_SELLER_DELIVERIED'")
    int deliverOrderByUser(@Param("id") long orderId);

    @Update("update shop_productOrders set `status`='STATE_USED',statusStr='待评价',updateTime=now(),endTime=now() where id=#{id} and `status`='STATE_UNUSED'")
    int updateOrderSuccess(@Param("id")long orderId);
    @Select("select verifyCode from shop_productOrders where id=#{id}")
    String getVerifyCode(@Param("id")long orderId);
    @Select("select o.*,oi.productId,oi.productskuId,oi.num,oi.title,oi.price from shop_productOrders o " +
            "left join shop_productOrderItems oi on oi.orderId=o.id where o.orderNo=#{orderNo}")
    ProductOrderDetailModel getOrderByOrderNo(@Param("orderNo")String orderNo);


    @Update("update shop_productOrders set `status`='STATE_CLOSED',statusStr='订单关闭',updateTime=now(),closeTime=now() " +
            "where id=#{id} and userId=#{userId} and `status`='STATE_WAIT_BUYER_PAY'")
    int updateOrderUnPay(@Param("id")long orderId,@Param("userId")long userId);
    @Update("update shop_productOrders set `status`='STATE_SUCCESS',statusStr='交易成功',updateTime=now(),endTime=now(),buyerRate=true where id=#{id} and `status`='STATE_USED'")
    int updateOrderUnComment(@Param("id")long orderId);
}
