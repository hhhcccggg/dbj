package com.zwdbj.server.adminserver.service.youzan.model;

import com.youzan.open.sdk.gen.v3_0_0.model.YouzanMultistoreGoodsSkuGetResult;
import com.youzan.open.sdk.gen.v3_0_0.model.YouzanTradeCartAddParams;

public class AyouzanTradeCartAddParams extends YouzanTradeCartAddParams {
    YouzanMultistoreGoodsSkuGetResult.GoodsDetail goodsDetail;

    public YouzanMultistoreGoodsSkuGetResult.GoodsDetail getGoodsDetail() {
        return goodsDetail;
    }

    public void setGoodsDetail(YouzanMultistoreGoodsSkuGetResult.GoodsDetail goodsDetail) {
        this.goodsDetail = goodsDetail;
    }
}
