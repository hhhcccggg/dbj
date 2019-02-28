package com.zwdbj.server.adminserver.service.shop.service.products.common;

/**
 * 产品详细类型
 */
public enum ProductDetailType {
    /**
     * 实物产品
     */
    DELIVERY,
    /**
     * 虚拟商品(不需要物流)
     */
    NODELIVERY,
    /**
     * 卡券(服务中套餐)，关联[ProductCard]表
     */
    CARD,
    /**
     * 代金券，类似70抵100，关联[ProductCashCoupon]表
     */
    CASHCOUPON
}
