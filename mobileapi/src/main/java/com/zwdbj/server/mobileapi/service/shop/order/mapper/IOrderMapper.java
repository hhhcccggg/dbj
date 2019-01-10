package com.zwdbj.server.mobileapi.service.shop.order.mapper;

import com.zwdbj.server.mobileapi.service.shop.order.model.AddNewOrderInput;
import com.zwdbj.server.mobileapi.service.shop.order.model.ProductOrderDetailModel;
import com.zwdbj.server.mobileapi.service.shop.order.model.ProductOrderModel;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface IOrderMapper {
    @SelectProvider(type = OrderSqlProvider.class,method = "getMyOrders")
    List<ProductOrderModel> getMyOrders(@Param("userId")long userId,@Param("status")int status);

    @Select("select o.*,oi.productId,oi.productskuId,oi.num,oi.title,oi.price from shop_productOrders o " +
            "left join shop_productOrderItems oi on oi.orderId=o.id where o.id=#{id}")
    ProductOrderDetailModel getOrderById(@Param("id")long id);

    @Insert("insert into shop_productOrders(id,orderNo,payment,actualPayment,useCoin," +
            "deliveryFee,status,updateTime,userId,storeId,buyerComment,buyerRate,receiveAddressId) " +
            "values(#{id},#{id},#{payment},{input.actualPayment},#{input.useCoin},#{input.deliveryFee},'STATE_WAIT_BUYER_PAY'," +
            "now(),#{userId},#{input.storeId},#{input.buyerComment},0,#{input.receiveAddressId})")
    int createOrder(@Param("id")long id, @Param("userId")long userId, @Param("input") AddNewOrderInput input, @Param("payment")int payment);
    @Insert("insert into shop_productOrderItems(id,productId,productskuId,orderId,num,title,price,totalFee) " +
            "values(#{id},#{input.productId},#{input.productskuId},#{orderId},#{input.num},#{input.title},#{price},#{totalFee})")
    int createOrderItem(@Param("id")long id,@Param("orderId")long orderId,@Param("input")AddNewOrderInput input,
                        @Param("price")int price,@Param("totalFee")int totalFee);
    @Select("select ifNull(sum(oi.num),0) from shop_productOrderItems oi left join shop_productOrders o on o.id=oi.orderId " +
            "where o.userId=#{userId} and oi.productId=#{productId} ")
    int userBuyProductAccounts(@Param("userId")long userId,@Param("productId")long productId);
    @Update("update shop_productOrders set paymentType=#{paymentType},thirdPaymentTradeNo=#{thirdPaymentTradeNo}," +
            "thirdPaymentTradeNotes=#{thirdPaymentTradeNotes},`status`='STATE_BUYER_PAYED',updateTime=now(),paymentTime=now() " +
            "where id=#{id}")
    int updateOrderPay(@Param("id")long id,@Param("paymentType")String paymentType,@Param("thirdPaymentTradeNo")String thirdPaymentTradeNo,
                       @Param("thirdPaymentTradeNotes")String thirdPaymentTradeNotes);
    @Update("update shop_productOrders set `status`='STATE_BUYER_DELIVERIED',updateTime=now(),endTime=now() " +
            "where id=#{id} and userId=#{userId} and `status`='STATE_SELLER_DELIVERIED'")
    int takeOverGoods(@Param("id")long orderId,@Param("userId")long userId);
}
