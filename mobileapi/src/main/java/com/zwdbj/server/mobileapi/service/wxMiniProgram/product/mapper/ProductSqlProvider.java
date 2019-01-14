package com.zwdbj.server.mobileapi.service.wxMiniProgram.product.mapper;

import com.zwdbj.server.mobileapi.service.wxMiniProgram.product.model.ProductInput;
import org.apache.ibatis.jdbc.SQL;

import java.util.Map;

public class ProductSqlProvider {

    /**
     * 小程序商品列表
     * @param map
     * @return
     */
    public String seleteList(Map map){
        ProductInput productInput = (ProductInput) map.get("productInput");
        SQL sql = new SQL().SELECT("id","productType","productDetailType","name","categoryId",
                "brandId","inventory","imageUrls","limitPerPerson");
        sql.FROM("shop_products");
        sql.WHERE("publish=1 and isDeleted=0 and storeId="+productInput.getStoreId()+" and specifyPublishTime<now()");

        if(productInput.getType() == 1){
            sql.ORDER_BY("sales desc");
        }else if(productInput.getType() == 2){
            sql.ORDER_BY("specifyPublishTime desc");
        }else if(productInput.getType() == 0){
            return "(SELECT " +
                    "shop_products.id, " +
                    "shop_products.inventory, " +
                    "shop_products.specifyPublishTime " +
                    "FROM " +
                    "shop_products " +
                    "where  " +
                    "publish=1 and isDeleted=0  and specifyPublishTime<now() and inventory>0 and storeId="+productInput.getStoreId()+" and specifyPublishTime<now()) " +
                    "union all " +
                    "(SELECT " +
                    "shop_products.id, " +
                    "shop_products.inventory, " +
                    "shop_products.specifyPublishTime " +
                    "FROM " +
                    "shop_products " +
                    "where  " +
                    "publish=1 and isDeleted=0  and specifyPublishTime<now() and storeId="+productInput.getStoreId()+" and  specifyPublishTime>now()) " +
                    "union all " +
                    "(SELECT " +
                    "shop_products.id, " +
                    "shop_products.inventory, " +
                    "shop_products.specifyPublishTime " +
                    "FROM " +
                    "shop_products " +
                    "where  " +
                    "publish=1 and isDeleted=0  and specifyPublishTime<now() and storeId="+productInput.getStoreId()+" and  inventory=0) ";
        }
        return sql.toString();
    }
}
