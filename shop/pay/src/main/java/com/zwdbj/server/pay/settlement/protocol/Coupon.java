package com.zwdbj.server.pay.settlement.protocol;

import java.io.Serializable;

public class Coupon implements Serializable {
    private long id;
    private String name;
    /**
     * 优惠的形式
     * CASH: 现金优惠
     * DISCOUNT:折扣
     */
    private String discountType;
    /**
     * 优惠的值
     * discountType=CASH, 这里存的是金额，单位分
     * discountType=DISCOUNT,这里存的是折扣，具体折扣换算discountValue/10.f,比如discountValue=5，那么折扣就是5/10.0f=0.5f
     */
    private int discountValue;
    /**
     * 限制条件，满{limitMoney}金额后才可以使用
     * 单位分
     * 0:没有限制
     */
    private int limitMoney;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDiscountType() {
        return discountType;
    }

    public void setDiscountType(String discountType) {
        this.discountType = discountType;
    }

    public int getDiscountValue() {
        return discountValue;
    }

    public void setDiscountValue(int discountValue) {
        this.discountValue = discountValue;
    }

    public int getLimitMoney() {
        return limitMoney;
    }

    public void setLimitMoney(int limitMoney) {
        this.limitMoney = limitMoney;
    }
}
