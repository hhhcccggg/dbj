package com.zwdbj.server.pay.settlement.protocol;

import com.zwdbj.server.pay.settlement.SettlementResult;

/**
 * 结算订单金额
 */
public interface ISettlement {
    SettlementResult settlement(int amount, long orderId);
    SettlementResult settlement(int amount,long orderId,
                    IUseCoins useCoins,
                    IUseCoupon useCoupon);
}
