package com.zwdbj.server.mobileapi.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zwdbj.server.mobileapi.service.wxMiniProgram.discountCoupon.model.DiscountCouponModel;
import com.zwdbj.server.mobileapi.service.wxMiniProgram.discountCoupon.service.DiscountCouponService;
import com.zwdbj.server.mobileapi.service.wxMiniProgram.userDiscountCoupon.model.SearchUserDiscountCoupon;
import com.zwdbj.server.mobileapi.service.wxMiniProgram.userDiscountCoupon.model.UserDiscountCouponOut;
import com.zwdbj.server.mobileapi.service.wxMiniProgram.userDiscountCoupon.service.UserDiscountCouponService;
import com.zwdbj.server.basemodel.model.ResponseData;
import com.zwdbj.server.basemodel.model.ResponseDataCode;
import com.zwdbj.server.basemodel.model.ResponsePageInfoData;
import com.zwdbj.server.basemodel.model.ServiceStatusInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/discountCoupon")
@Api(description = "优惠券相关")
public class DiscountCouponController {

    @Autowired
    private DiscountCouponService discountCouponServiceImpl;

    @Autowired
    private UserDiscountCouponService userDiscountCouponServiceImpl;

    @PostMapping("getDiscountCoupon")
    @ApiOperation("领取优惠券")
    public ResponseData<Long> getDiscountCoupon(long id){
        ServiceStatusInfo<Long> serviceStatusInfo = discountCouponServiceImpl.userGetDiscountCoupon(id);
        if(serviceStatusInfo.isSuccess())
            return new ResponseData<>(ResponseDataCode.STATUS_NORMAL,"",serviceStatusInfo.getData());
        return new ResponseData<>(ResponseDataCode.STATUS_ERROR,serviceStatusInfo.getMsg(),null);
    }

    @PostMapping("selectUserDiscountCoupon")
    @ApiOperation("查询用户优惠券")
    public ResponsePageInfoData<List<UserDiscountCouponOut>> selectUserDiscountCoupon (@RequestParam(value = "pageNo" , required = true , defaultValue = "1")int pageNo,
                                                                                       @RequestParam(value = "rows" , required = true , defaultValue = "30")int rows,
                                                                                       @Valid SearchUserDiscountCoupon searchUserDiscountCoupon){
        PageHelper.startPage(pageNo,rows);
        ServiceStatusInfo<List<UserDiscountCouponOut>> serviceStatusInfo = userDiscountCouponServiceImpl.selectUserDiscountCoupon(searchUserDiscountCoupon);
        if( !serviceStatusInfo.isSuccess() )
            return new ResponsePageInfoData<>(ResponseDataCode.STATUS_ERROR,serviceStatusInfo.getMsg(),null,0L);
        PageInfo<UserDiscountCouponOut> pageInfo = new PageInfo<>(serviceStatusInfo.getData());
        return new ResponsePageInfoData<>(ResponseDataCode.STATUS_NORMAL,serviceStatusInfo.getMsg(),pageInfo.getList(),pageInfo.getTotal());
    }

    @GetMapping("find/{id}")
    @ApiOperation("查询指定优惠券")
    public ResponseData<DiscountCouponModel> selectById(@PathVariable long id){
        ServiceStatusInfo<DiscountCouponModel> serviceStatusInfo = discountCouponServiceImpl.selectDiscountCoupon(id);
        if(serviceStatusInfo.isSuccess())
            return new ResponseData<>(ResponseDataCode.STATUS_NORMAL,"",serviceStatusInfo.getData());
        return new ResponseData<>(ResponseDataCode.STATUS_ERROR,serviceStatusInfo.getMsg(),null);
    }

    @GetMapping("getVaildCoupon")
    @ApiOperation("查询订单可用优惠券")
    public ResponseData<List<UserDiscountCouponOut>> getVaildCoupon(@ApiParam(value = "店铺ID") long storeId,@ApiParam(value = "订单价格") long price){
        ServiceStatusInfo<List<UserDiscountCouponOut>> serviceStatusInfo = userDiscountCouponServiceImpl.getVaildCoupon(storeId,price);
        if(serviceStatusInfo.isSuccess())
            return new ResponseData<>(ResponseDataCode.STATUS_NORMAL,"",serviceStatusInfo.getData());
        return new ResponseData<>(ResponseDataCode.STATUS_ERROR,serviceStatusInfo.getMsg(),null);
    }

    @GetMapping("getVaildCouponById/{id}")
    @ApiOperation("查询单个可用优惠券")
    public ResponseData<UserDiscountCouponOut> getVaildCouponById(@ApiParam(value = "店铺ID") long storeId,
                                                     @ApiParam(value = "订单价格") long price,@PathVariable long id){
        ServiceStatusInfo<UserDiscountCouponOut> serviceStatusInfo = userDiscountCouponServiceImpl.getVaildCouponById(storeId,price,id);
        if(serviceStatusInfo.isSuccess())
            return new ResponseData<>(ResponseDataCode.STATUS_NORMAL,"",serviceStatusInfo.getData());
        return new ResponseData<>(ResponseDataCode.STATUS_ERROR,serviceStatusInfo.getMsg(),null);
    }
}
