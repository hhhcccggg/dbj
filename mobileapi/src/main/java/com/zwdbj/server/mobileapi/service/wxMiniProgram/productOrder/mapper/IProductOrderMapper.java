package com.zwdbj.server.mobileapi.service.wxMiniProgram.productOrder.mapper;

import com.zwdbj.server.mobileapi.service.wxMiniProgram.productOrder.model.AddOrderInput;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface IProductOrderMapper {

    @Insert("insert into shop_productOrders(id,orderNo,payment,actualPayment,useCoin,paymentType,thirdPaymentTradeNo," +
            "deliveryFee,status,updateTime,paymentTime,userId,storeId,buyerComment,buyerRate,receiveAddressId) " +
            "values(#{id},#{id},#{payment},0,#{input.useCoin},'NONE','NONE',#{input.deliveryFee},'STATE_BUYER_PAYED'," +
            "now(),now(),#{userId},#{input.storeId},#{input.buyerComment},0,#{input.receiveAddressId})")
    int createOrder(@Param("id")long id,@Param("userId")long userId, @Param("input")AddOrderInput input,@Param("payment")int payment);
    @Insert("insert into shop_productOrderItems(id,productId,productskuId,orderId,num,title,price,totalFee) " +
            "values(#{id},#{input.productId},#{input.productskuId},#{orderId},#{input.num},#{input.title},#{price},#{totalFee})")
    int createOrderItem(@Param("id")long id,@Param("orderId")long orderId,@Param("input")AddOrderInput input,
                        @Param("price")int price,@Param("totalFee")int totalFee);
    @Select("select sum(oi.num) from shop_productOrderItems oi left join shop_productOrders o on o.id=oi.orderId " +
            "where o.userId=#{userId} and oi.productId=#{productId} ")
    int userBuyProductAccounts(@Param("userId")long userId,@Param("productId")long productId);
}
