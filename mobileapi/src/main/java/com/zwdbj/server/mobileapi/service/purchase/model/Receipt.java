package com.zwdbj.server.mobileapi.service.purchase.model;

import io.swagger.annotations.ApiModel;

//服务器返回的验证信息
@ApiModel(description = "ios服务器返回的购买信息")
public class Receipt {

    private String original_purchase_date_pst; //购买时间,太平洋标准时间

    private String purchase_date_ms; //购买时间毫秒

    private String unique_identifier;//唯一标识符

    private String original_transaction_id;//原始交易ID

    private String bvrs;//iPhone程序的版本号

    private String transaction_id; //交易的标识

    private String quantity;

    private String unique_vendor_identifier; //开发商交易ID

    private String item_id;

    private String product_id;//商品的标识

    private String purchase_date;//购买时间

    private String original_purchase_date; //原始购买时间

    private String purchase_date_pst;//太平洋标准时间

    private String bid;//iPhone程序的bundle标识

    private String original_purchase_date_ms;//毫秒
}
