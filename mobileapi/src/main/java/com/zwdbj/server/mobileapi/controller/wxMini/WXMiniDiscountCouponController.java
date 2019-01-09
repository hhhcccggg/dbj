package com.zwdbj.server.mobileapi.controller.wxMini;

import com.zwdbj.server.utility.model.ResponseData;
import com.zwdbj.server.utility.model.ResponsePageInfoData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/wx/mini/discountCoupon")
@Api(description = "优惠券相关")
public class WXMiniDiscountCouponController {

    @PostMapping("getDiscountCoupon")
    @ApiOperation("领取优惠券")
    public ResponseData<Long> getDiscountCoupon(long id){
        return null;
    }

    @PostMapping("selectUserDiscountCoupon")
    @ApiOperation("查询用户优惠券")
    public ResponsePageInfoData<T> selectUserDiscountCoupon (@RequestParam(value = "pageNo" , required = true , defaultValue = "1")int pageNo,
                                                             @RequestParam(value = "rows" , required = true , defaultValue = "30")int rows){
        return null;
    }

    @GetMapping("find/{id}")
    @ApiOperation("查询指定优惠券")
    public ResponseData<T> selectById(@PathVariable long id){
        return null;
    }
}
