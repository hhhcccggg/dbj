package com.zwdbj.server.shop_admin_service.products.mapper;

import com.zwdbj.server.shop_admin_service.products.model.SearchProducts;
import org.apache.ibatis.jdbc.SQL;

import java.util.Map;

public class ProductsSqlProvider {
    public String search(Map paras) {
        SearchProducts searchProduct = (SearchProducts) paras.get("searchProducts");
        SQL sql = new SQL()
                .SELECT("*")
                .FROM("shop_products");
        if (searchProduct.getName() != null) {
            sql.WHERE("name=" + searchProduct.getName());
        } else if (searchProduct.getNumberId() != null) {
            sql.WHERE("numberId=" + searchProduct.getNumberId());
        } else if (searchProduct.getPriceDown() != 0) {
            sql.WHERE("priceDown>=" + searchProduct.getPriceDown());
        } else if (searchProduct.getPriceUp() != 0) {
            sql.WHERE("priceUp<=" + searchProduct.getPriceUp());
        } else if (searchProduct.getProductGroupId() != 0) {
            sql.WHERE("productGroupId=" + searchProduct.getProductGroupId());
        } else if (searchProduct.getProductType() != 0) {
            sql.WHERE("productType=" + searchProduct.getProductType());
        } else if (searchProduct.getSalesUp() != 0) {
            sql.WHERE("salesUp<=" + searchProduct.getSalesUp());
        } else if (searchProduct.getSalseDown() != 0) {
            sql.WHERE("salesDown>=" + searchProduct.getSalseDown());
        }
        sql.ORDER_BY("createTime");
System.out.println(sql.toString());
        return sql.toString();
    }
}