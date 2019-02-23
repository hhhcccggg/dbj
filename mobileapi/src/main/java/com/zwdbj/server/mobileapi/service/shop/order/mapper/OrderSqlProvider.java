package com.zwdbj.server.mobileapi.service.shop.order.mapper;

import org.apache.ibatis.jdbc.SQL;

import java.util.Map;

public class OrderSqlProvider {
    public String getMyOrders(Map params){
        Long userId = (Long)params.get("userId");
        Integer status = (Integer)params.get("status");
        SQL sql = new SQL()
                .SELECT("o.*,oi.productId,oi.productskuId,oi.num,oi.title,oi.price")
                .FROM("shop_productOrders o ")
                .LEFT_OUTER_JOIN("shop_productOrderItems oi on oi.orderId=o.id")
                .WHERE("o.userId="+userId);
        if (status==1){
            sql.WHERE("o.status='STATE_WAIT_BUYER_PAY'");
        }else if (status==2){
            sql.WHERE("o.status='STATE_UNUSED'");
        }else if (status==3){
            sql.WHERE("o.buyerRate=false");
        }
        sql.ORDER_BY("createTime desc");
        return sql.toString();
    }
}
