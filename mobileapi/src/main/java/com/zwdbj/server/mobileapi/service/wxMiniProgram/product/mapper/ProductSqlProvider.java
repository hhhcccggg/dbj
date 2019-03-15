package com.zwdbj.server.mobileapi.service.wxMiniProgram.product.mapper;

import com.zwdbj.server.mobileapi.service.wxMiniProgram.product.model.ProductInput;
import org.apache.ibatis.jdbc.SQL;

import java.util.Date;
import java.util.Map;

public class ProductSqlProvider {

    /**
     * 小程序商品列表
     * @param map
     * @return
     */
    public String seleteList(Map map){
        ProductInput productInput = (ProductInput) map.get("productInput");
        String storeId="";
        if(productInput.getStoreId() != 0){
            storeId="and storeId="+productInput.getStoreId();
        }
        long specifyPublishTime = new Date().getTime();
        SQL sql = new SQL().SELECT("id","productType","productDetailType","name","storeId","categoryId", "sales",
                "brandId","inventory","imageUrls","limitPerPerson","detailDescription","supportCoin","ruleDescription","createTime");
        sql.FROM("shop_products");
        sql.WHERE(" productDetailType = 'DELIVERY' and (publish=1 or (publish=0 and specifyPublishTime!=0 and  specifyPublishTime<"+specifyPublishTime+")) and isDeleted=0 "+storeId);

        if(productInput.getType() == 1){
            sql.ORDER_BY("sales desc");
        }else if(productInput.getType() == 2){
            sql.ORDER_BY("specifyPublishTime desc");
        }else if(productInput.getType() == 0){
            sql.ORDER_BY("createTime desc");
        }
        return sql.toString();
    }
}
