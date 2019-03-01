package com.zwdbj.server.adminserver.service.shop.service.productOrder.mapper;

import com.zwdbj.server.adminserver.service.shop.service.productOrder.model.ProductOrderInput;
import org.apache.ibatis.jdbc.SQL;

import java.util.Map;

public class ProductOrderSqlProvider {
    public String getStoreOrders(Map params){
        ProductOrderInput input = (ProductOrderInput)params.get("input");
        Long storeId = (Long)params.get("storeId");
        SQL sql = new SQL()
                .SELECT("o.*,oi.productId,oi.productskuId,oi.num,oi.title,oi.price,a.receiverName as receiverName," +
                        "p.imageUrls as imageUrls " +
                        "a.receiverMobile as receiverMobile,u.nickName as nickName")
                .FROM("shop_productOrders o ")
                .LEFT_OUTER_JOIN("shop_productOrderItems oi on oi.orderId=o.id")
                .LEFT_OUTER_JOIN("shop_receiveAddresses a on a.id=o.receiveAddressId")
                .LEFT_OUTER_JOIN("core_users u on u.id=o.userId")
                .LEFT_OUTER_JOIN("shop_products p on p.id=oi.productId")
                .WHERE("o.storeId="+storeId);
        if (input.getStartTime() !=null  && input.getStartTime().length()!=0 && input.getEndTime() !=null && input.getEndTime().length()!=0){
            sql.WHERE(String.format("o.createTime between '%s' and '%s'",input.getStartTime(),input.getEndTime()));
        }
        if (input.getKeyWords() !=null && input.getKeyWords().length()>0 ) {
            sql.WHERE(String.format("o.id like '%s'", ("%" + input.getKeyWords() + "%")))
                    .OR()
                    .WHERE(String.format("a.receiverName like '%s'", ("%" + input.getKeyWords() + "%")))
                    .OR()
                    .WHERE(String.format("a.receiverMobile like '%s'", ("%" + input.getKeyWords() + "%")));
        }
        if (input.getPaymentType()!=null && input.getPaymentType().length()>0){
            sql.WHERE(String.format("o.paymentType='%s'",input.getPaymentType()));
        }
        if (input.getStatus()!=null && input.getStatus().length()>0){
            sql.WHERE(String.format("o.status='%s'",input.getStatus()));
        }
        sql.ORDER_BY("o.createTime desc");
        return sql.toString();

    }

}
