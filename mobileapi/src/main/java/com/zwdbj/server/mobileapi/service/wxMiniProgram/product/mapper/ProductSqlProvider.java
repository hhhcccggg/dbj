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
            return "(SELECT " +
                    "shop_products.id, " +
                    "shop_products.productType, " +
                    "shop_products.specifyPublishTime, " +
                    "shop_products.name, " +
                    "shop_products.storeId, " +
                    "shop_products.sales, " +
                    "shop_products.categoryId, " +
                    "shop_products.brandId, " +
                    "shop_products.inventory, " +
                    "shop_products.imageUrls, " +
                    "shop_products.detailDescription, " +
                    "shop_products.supportCoin, "+
                    "shop_products.ruleDescription, "+
                    "shop_products.limitPerPerson " +
                    "FROM " +
                    "shop_products " +
                    "where  " +
                    " productDetailType = 'DELIVERY' and (publish=1 or (publish=0 and specifyPublishTime!=0 and specifyPublishTime<"+specifyPublishTime+"))  and isDeleted=0 and inventory>0 "+storeId+") " +
                    "union all " +
                    "(SELECT " +
                    "shop_products.id, " +
                    "shop_products.productType, " +
                    "shop_products.specifyPublishTime, " +
                    "shop_products.name, " +
                    "shop_products.storeId, " +
                    "shop_products.sales, " +
                    "shop_products.categoryId, " +
                    "shop_products.brandId, " +
                    "shop_products.inventory, " +
                    "shop_products.imageUrls, " +
                    "shop_products.detailDescription, " +
                    "shop_products.supportCoin, "+
                    "shop_products.ruleDescription, "+
                    "shop_products.limitPerPerson " +
                    "FROM " +
                    "shop_products " +
                    "where  " +
                    " productDetailType = 'DELIVERY' and (publish=1 or (publish=0 and specifyPublishTime!=0 and specifyPublishTime<"+specifyPublishTime+")) and isDeleted=0  "+storeId+") " +
                    "union all " +
                    "(SELECT " +
                    "shop_products.id, " +
                    "shop_products.productType, " +
                    "shop_products.specifyPublishTime, " +
                    "shop_products.name, " +
                    "shop_products.storeId, " +
                    "shop_products.sales, " +
                    "shop_products.categoryId, " +
                    "shop_products.brandId, " +
                    "shop_products.inventory, " +
                    "shop_products.imageUrls, " +
                    "shop_products.detailDescription, " +
                    "shop_products.supportCoin, "+
                    "shop_products.ruleDescription, "+
                    "shop_products.limitPerPerson " +
                    "FROM " +
                    "shop_products " +
                    "where  " +
                    " productDetailType = 'DELIVERY'  and (publish=1 or (publish=0 and specifyPublishTime!=0 and specifyPublishTime<"+specifyPublishTime+")) and isDeleted=0  "+storeId+" and  inventory=0) ";
        }
        return sql.toString();
    }
}
