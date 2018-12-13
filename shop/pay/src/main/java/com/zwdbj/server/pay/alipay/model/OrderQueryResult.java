package com.zwdbj.server.pay.alipay.model;

import com.alipay.api.response.AlipayTradeQueryResponse;
import io.swagger.annotations.ApiModel;

import java.io.Serializable;

/**
 * 查询结果
 */
@ApiModel("订单查询结果")
public class OrderQueryResult extends AlipayTradeQueryResponse {

}
