package com.zwdbj.server.pay.settlement;

import com.zwdbj.server.pay.settlement.protocol.*;
import org.springframework.beans.factory.annotation.Autowired;

public class Settlement implements ISettlement {
    @Override
    public SettlementResult settlement(int amount, long orderId) {
        return this.settlement(amount,orderId,0,null);
    }

    @Override
    public SettlementResult settlement(int amount, long orderId, int coins, Coupon coupon) {
        SettlementResult result = new SettlementResult();
        result.setOrderId(orderId);
        result.setTotalAmount(amount);
        result.setCouponId(0);
        int useCoinsAmount = 0;
        if (coins <= 0) {
            useCoinsAmount = 0;
        } else if (coins % 10 != 0) {
            useCoinsAmount = 0;
        } else {
            useCoinsAmount = coins / 10 * 100;
        }
        //先抵扣，然后打折
        result.setUsedCoins(useCoinsAmount);
        int remainAmount = amount - useCoinsAmount;
        result.setActualAmount(remainAmount);
        //处理优惠券
        if (coupon!=null&&coupon.getLimitMoney()>=amount) {
            if (coupon.getDiscountType().equals("CASH")) {
                result.setCouponId(coupon.getId());
                remainAmount = remainAmount - coupon.getDiscountValue();
                result.setActualAmount(remainAmount);
            } else if (coupon.getDiscountType().equals("DISCOUNT")) {
                result.setCouponId(coupon.getId());
                remainAmount = (int)(remainAmount * coupon.getDiscountValue()/10.0f);
                result.setActualAmount(remainAmount);
            }
        }
        result.setCouponAmount(amount-remainAmount);
        return result;
    }
}
