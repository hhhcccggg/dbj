package com.zwdbj.server.adminserver.controller.shop;

import com.github.pagehelper.PageInfo;
import com.zwdbj.server.adminserver.service.shop.service.discountCoupon.model.DiscountCouponInput;
import com.zwdbj.server.adminserver.service.shop.service.discountCoupon.model.DiscountCouponModel;
import com.zwdbj.server.adminserver.service.shop.service.discountCoupon.model.SearchDiscountCoupon;
import com.zwdbj.server.adminserver.service.shop.service.discountCoupon.service.DiscountCouponService;
import com.zwdbj.server.utility.model.ResponseData;
import com.zwdbj.server.utility.model.ResponseDataCode;
import com.zwdbj.server.utility.model.ResponsePageInfoData;
import com.zwdbj.server.utility.model.ServiceStatusInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/shop/discountCoupon")
@Api(description = "优惠券模块")
public class DiscountCouponController {

    @Autowired
    private DiscountCouponService discountCouponServiceImpl;

    @GetMapping("findPage")
    @ApiOperation(value = "优惠券分页查询")
    public ResponsePageInfoData<DiscountCouponModel> findPage(@RequestParam(value = "pageNo" , required = true , defaultValue = "1") int pageNo,
                                                              @RequestParam(value = "rows" ,required = true , defaultValue = "30") int rows,
                                                              SearchDiscountCoupon searchDiscountCoupon){
        ServiceStatusInfo<List<DiscountCouponModel>> serviceStatusInfo = discountCouponServiceImpl.findByPage(searchDiscountCoupon,pageNo,rows);
        if( !serviceStatusInfo.isSuccess() ){
            return new ResponsePageInfoData<>(ResponseDataCode.STATUS_ERROR,serviceStatusInfo.getMsg(),null,0);
        }
        PageInfo<DiscountCouponModel> pageInfo = new PageInfo<>(serviceStatusInfo.getData());
        return new ResponsePageInfoData(ResponseDataCode.STATUS_NORMAL,"",pageInfo.getList(),pageInfo.getTotal());
    }

    @PostMapping("addDiscountCoupon")
    @ApiOperation(value = "新增优惠券")
    public ResponseData<Long> addDiscountCoupon(@RequestBody @Valid DiscountCouponInput discountCouponInput){
        ServiceStatusInfo<Long> serviceStatusInfo = discountCouponServiceImpl.createDiscountCoupon(discountCouponInput);
        if( !serviceStatusInfo.isSuccess() ){
            return new ResponseData<>(ResponseDataCode.STATUS_ERROR,serviceStatusInfo.getMsg(),null);
        }
        return new ResponseData<>(ResponseDataCode.STATUS_NORMAL,"",serviceStatusInfo.getData());
    }

    @PostMapping("updateDiscountCoupon")
    @ApiOperation(value = "修改优惠券")
    public ResponseData<Long> updateDiscountCoupon(@RequestBody @Valid DiscountCouponInput discountCouponInput){
        ServiceStatusInfo<Long> serviceStatusInfo = discountCouponServiceImpl.updateDiscountCoupon(discountCouponInput);
        if( !serviceStatusInfo.isSuccess() ){
            return new ResponseData<>(ResponseDataCode.STATUS_ERROR,serviceStatusInfo.getMsg(),null);
        }
        return new ResponseData<>(ResponseDataCode.STATUS_NORMAL,"",serviceStatusInfo.getData());
    }

    @PostMapping("deleteDiscountCoupon")
    @ApiOperation(value = "删除优惠券")
    public ResponseData<Long> deleteDiscountCoupon(@RequestBody long[] id){
        ServiceStatusInfo<Long> serviceStatusInfo = discountCouponServiceImpl.removeDiscountCoupon(id);
        if( !serviceStatusInfo.isSuccess() ){
            return new ResponseData<>(ResponseDataCode.STATUS_ERROR,serviceStatusInfo.getMsg(),null);
        }
        return new ResponseData<>(ResponseDataCode.STATUS_NORMAL,"",serviceStatusInfo.getData());
    }

    @PostMapping("find/{id}")
    @ApiOperation(value = "查询优惠券")
    public ResponseData<DiscountCouponModel> findDiscountCoupon(@RequestParam(value = "id" ,required = true) long id){
        ServiceStatusInfo<DiscountCouponModel> serviceStatusInfo = discountCouponServiceImpl.findById(id);
        if( !serviceStatusInfo.isSuccess() ){
            return new ResponseData<>(ResponseDataCode.STATUS_ERROR,serviceStatusInfo.getMsg(),null);
        }
        return new ResponseData<>(ResponseDataCode.STATUS_NORMAL,"",serviceStatusInfo.getData());
    }


}
