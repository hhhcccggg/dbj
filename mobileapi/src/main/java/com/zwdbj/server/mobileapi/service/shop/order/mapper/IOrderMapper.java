package com.zwdbj.server.mobileapi.service.shop.order.mapper;

import com.zwdbj.server.mobileapi.service.shop.order.model.AddNewOrderInput;
import com.zwdbj.server.mobileapi.service.shop.order.model.CancelOrderInput;
import com.zwdbj.server.mobileapi.service.shop.order.model.ProductOrderDetailModel;
import com.zwdbj.server.mobileapi.service.shop.order.model.ProductOrderModel;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface IOrderMapper {
    @SelectProvider(type = OrderSqlProvider.class,method = "getMyOrders")
    List<ProductOrderModel> getMyOrders(@Param("userId")long userId,@Param("status")int status);

    @Select("select o.*,oi.productId,oi.productskuId,oi.num,oi.title,oi.price ,s.name as storeName ,s.mainConverImage as mainConverImage " +
            "from shop_productOrders o " +
            "left join shop_productOrderItems oi on oi.orderId=o.id left join shop_stores s on s.id=o.storeId where o.id=#{id}")
    ProductOrderDetailModel getOrderById(@Param("id")long id);

    @Insert("insert into shop_productOrders(id,orderNo,payment,paymentType,actualPayment,useCoin,verifyCode," +
            "deliveryFee,status,updateTime,userId,storeId,buyerComment,buyerRate,receiveAddressId,thirdPaymentTradeNo) " +
            "values(#{id},#{id},#{payment},'NONE',#{payment},#{input.useCoin},#{verifyCode},#{input.deliveryFee},'STATE_WAIT_BUYER_PAY'," +
            "now(),#{userId},#{input.storeId},#{input.buyerComment},0,#{input.receiveAddressId},'NONE')")
    int createOrder(@Param("id")long id, @Param("userId")long userId, @Param("input") AddNewOrderInput input,
                    @Param("payment")int payment,@Param("verifyCode")String verifyCode);
    @Update("update shop_productOrders set `status`='STATE_CLOSED',updateTime=now(),closeTime=now(),cancelReason=#{input.cancelReason} " +
            "where id=#{input.orderId} and `status`='STATE_WAIT_BUYER_PAY'")
    int cancelOrder(@Param("input") CancelOrderInput input);
    @Select("select verifyCode from shop_productOrders where id=#{id}")
    String getVerifyCode(@Param("id")long orderId);
    @Insert("insert into shop_productOrderItems(id,productId,productskuId,orderId,num,title,price,totalFee) " +
            "values(#{id},#{input.productId},#{input.productskuId},#{orderId},#{input.num},#{input.title},#{price},#{totalFee})")
    int createOrderItem(@Param("id")long id,@Param("orderId")long orderId,@Param("input")AddNewOrderInput input,
                        @Param("price")int price,@Param("totalFee")int totalFee);
    @Select("select ifNull(sum(oi.num),0) from shop_productOrderItems oi left join shop_productOrders o on o.id=oi.orderId " +
            "where o.userId=#{userId} and oi.productId=#{productId} ")
    int userBuyProductAccounts(@Param("userId")long userId,@Param("productId")long productId);
    @Update("update shop_productOrders set paymentType=#{paymentType},thirdPaymentTradeNo=#{thirdPaymentTradeNo}," +
            "thirdPaymentTradeNotes=#{thirdPaymentTradeNotes},`status`=#{status},updateTime=now(),paymentTime=now() " +
            "where id=#{id}")
    int updateOrderPay(@Param("id")long id,@Param("paymentType")String paymentType,@Param("thirdPaymentTradeNo")String thirdPaymentTradeNo,
                       @Param("thirdPaymentTradeNotes")String thirdPaymentTradeNotes,@Param("status")String status);

    @Update("update shop_productOrders set `status`=#{status} where id=#{id} and thirdPaymentTradeNo=#{thirdPaymentTradeNo}")
    int updateOrderState(@Param("id")long id,@Param("thirdPaymentTradeNo")String thirdPaymentTradeNo,@Param("status")String status);
    @Update("update shop_productOrders set `status`='STATE_BUYER_DELIVERIED',updateTime=now(),endTime=now() " +
            "where id=#{id} and userId=#{userId} and `status`='STATE_SELLER_DELIVERIED'")
    int takeOverGoods(@Param("id")long orderId,@Param("userId")long userId);
}
