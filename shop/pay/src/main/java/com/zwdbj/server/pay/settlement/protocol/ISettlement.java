package com.zwdbj.server.pay.settlement.protocol;

/**
 * 结算订单金额
 */
public interface ISettlement {
    /**
     * 结算
     * @param amount 原总金额，单位分
     * @param orderId 订单编号
     * @return 结算结果
     */
    SettlementResult settlement(int amount, long orderId);

    /**
     * 结算
     * @param amount 原总金额，单位分
     * @param orderId 订单编号
     * @param useCoins 金币数据协议
     * @param useCoupon 优惠券数据协议
     * @return 结算结果
     */
    SettlementResult settlement(int amount,long orderId,
                    IUseCoins useCoins,
                    IUseCoupon useCoupon);
}
