package com.zwdbj.server.adminserver.service.shop.service.discountCoupon.mapper;

import com.zwdbj.server.adminserver.service.shop.service.discountCoupon.model.SearchDiscountCoupon;
import org.apache.ibatis.jdbc.SQL;

import java.util.Map;

public class DiscountCouponSqlProvider {

    /**
     * 批量删除
     * @param map
     * @return
     */
    public String deleteDisountCount(Map map){
        long[] ids = (long[]) map.get("ids");
        long storeId = (long)map.get("storeId");
        long legalSubjectId = (long)map.get("legalSubjectId");
        SQL sql  = new SQL().UPDATE("shop_discountcoupons").SET("isDeleted=1")
                .SET("deleteTime=now()");
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < ids.length; i++) {
            stringBuffer.append("id="+ids[i]);
            if(i+1 != ids.length)stringBuffer.append(" or ");
        }
        sql.WHERE(stringBuffer.toString());
        sql.WHERE("isDeleted=0");
        System.out.println(sql.toString());
        return sql.toString();
    }

    /**
     * 分页查询
     * @param map
     * @return
     */
    public String selectByPage(Map map){
        SearchDiscountCoupon searchDiscountCoupon = (SearchDiscountCoupon) map.get("searchDiscountCoupon");
        SQL sql  = new SQL().SELECT("id","name","couponCount","discountType","discountValue","limitMoney"
                ,"limitGetPerPerson","useInfo","onlySupportOriginProduct","validStartTime","validEndTime","storeId","legalSubjectId")
                .FROM("shop_discountcoupons");
        if( searchDiscountCoupon.getDiscountType() !=null ){
            sql.WHERE("discountType = #{searchDiscountCoupon.discountType}");
        }
        if(searchDiscountCoupon.getName() != null && searchDiscountCoupon.getName().trim().length()>0){
            sql.WHERE("`name` like concat(#{searchDiscountCoupon.name},'%')");
        }
        if(searchDiscountCoupon.getValidEndTime() != null){
            sql.WHERE(" (validEndTime <= #{searchDiscountCoupon.validEndTime} or validEndTime is null)");
        }
        if(searchDiscountCoupon.getValidStartTime() != null){
            sql.WHERE(" (validStartTime >= #{searchDiscountCoupon.validStartTime} or validStartTime is null)");
        }
        sql.WHERE("storeId = #{searchDiscountCoupon.storeId} and " +
                "legalSubjectId = #{searchDiscountCoupon.legalSubjectId} and isDeleted=0");
        sql.ORDER_BY("createTime desc");
        System.out.println(sql.toString());
        return sql.toString();
    }
}
