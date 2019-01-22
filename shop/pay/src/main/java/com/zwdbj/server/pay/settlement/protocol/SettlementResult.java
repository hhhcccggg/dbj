package com.zwdbj.server.pay.settlement.protocol;

import java.io.Serializable;

/**
 * 结算结果数据
 */
public class SettlementResult implements Serializable {
    /**
     * 订单编号
     */
    private long orderId;
    /**
     * 订单总金额
     */
    private int totalAmount;
    /**
     * 订单实际支付金额
     */
    private int actualAmount;
    /**
     * 订单优惠/节省金额
     */
    private int couponAmount;
    /**
     * 订单使用抵扣的金币数
     */
    private int usedCoins;
    /**
     * 订单使用的优惠券
     * 如果等于0， 则表示没有优惠券，或者提供的优惠券不符合规范
     */
    private long couponId;

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public int getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(int totalAmount) {
        this.totalAmount = totalAmount;
    }

    public int getActualAmount() {
        return actualAmount;
    }

    public void setActualAmount(int actualAmount) {
        this.actualAmount = actualAmount;
    }

    public int getCouponAmount() {
        return couponAmount;
    }

    public void setCouponAmount(int couponAmount) {
        this.couponAmount = couponAmount;
    }

    public int getUsedCoins() {
        return usedCoins;
    }

    public void setUsedCoins(int usedCoins) {
        this.usedCoins = usedCoins;
    }

    public long getCouponId() {
        return couponId;
    }

    public void setCouponId(long couponId) {
        this.couponId = couponId;
    }
}
