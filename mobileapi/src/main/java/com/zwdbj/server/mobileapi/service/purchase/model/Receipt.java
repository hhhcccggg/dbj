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

    public String getOriginal_purchase_date_pst() {
        return original_purchase_date_pst;
    }

    public void setOriginal_purchase_date_pst(String original_purchase_date_pst) {
        this.original_purchase_date_pst = original_purchase_date_pst;
    }

    public String getPurchase_date_ms() {
        return purchase_date_ms;
    }

    public void setPurchase_date_ms(String purchase_date_ms) {
        this.purchase_date_ms = purchase_date_ms;
    }

    public String getUnique_identifier() {
        return unique_identifier;
    }

    public void setUnique_identifier(String unique_identifier) {
        this.unique_identifier = unique_identifier;
    }

    public String getOriginal_transaction_id() {
        return original_transaction_id;
    }

    public void setOriginal_transaction_id(String original_transaction_id) {
        this.original_transaction_id = original_transaction_id;
    }

    public String getBvrs() {
        return bvrs;
    }

    public void setBvrs(String bvrs) {
        this.bvrs = bvrs;
    }

    public String getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(String transaction_id) {
        this.transaction_id = transaction_id;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getUnique_vendor_identifier() {
        return unique_vendor_identifier;
    }

    public void setUnique_vendor_identifier(String unique_vendor_identifier) {
        this.unique_vendor_identifier = unique_vendor_identifier;
    }

    public String getItem_id() {
        return item_id;
    }

    public void setItem_id(String item_id) {
        this.item_id = item_id;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getPurchase_date() {
        return purchase_date;
    }

    public void setPurchase_date(String purchase_date) {
        this.purchase_date = purchase_date;
    }

    public String getOriginal_purchase_date() {
        return original_purchase_date;
    }

    public void setOriginal_purchase_date(String original_purchase_date) {
        this.original_purchase_date = original_purchase_date;
    }

    public String getPurchase_date_pst() {
        return purchase_date_pst;
    }

    public void setPurchase_date_pst(String purchase_date_pst) {
        this.purchase_date_pst = purchase_date_pst;
    }

    public String getBid() {
        return bid;
    }

    public void setBid(String bid) {
        this.bid = bid;
    }

    public String getOriginal_purchase_date_ms() {
        return original_purchase_date_ms;
    }

    public void setOriginal_purchase_date_ms(String original_purchase_date_ms) {
        this.original_purchase_date_ms = original_purchase_date_ms;
    }
}
