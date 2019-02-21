package com.zwdbj.server.adminserver.controller.shop;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zwdbj.server.adminserver.service.shop.service.store.model.ReviewStoreInput;
import com.zwdbj.server.adminserver.service.shop.service.store.model.StoreInfo;
import com.zwdbj.server.adminserver.service.shop.service.store.model.StoreSearchInput;
import com.zwdbj.server.adminserver.service.shop.service.store.model.StoreSimpleInfo;
import com.zwdbj.server.adminserver.service.shop.service.store.service.StoreService;
import com.zwdbj.server.utility.model.ResponseData;
import com.zwdbj.server.utility.model.ResponsePageInfoData;
import com.zwdbj.server.utility.model.ServiceStatusInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api("店铺信息相关")
@RequestMapping("/api/shop/store")
@RestController
public class StoreController {
    @Autowired
    private StoreService storeServiceImpl;

    @RequiresAuthentication
    @RequestMapping(value = "/basicInfo", method = RequestMethod.POST)
    @ApiOperation(value = "展示店铺基本信息")
    public ResponsePageInfoData<List<StoreSimpleInfo>> searchStore(@RequestBody StoreSearchInput input,
                                                                   @RequestParam(value = "pageNo", required = true, defaultValue = "1") int pageNo,
                                                                   @RequestParam(value = "rows", required = true, defaultValue = "30")int rows){
        Page<StoreSimpleInfo> pageInfo = PageHelper.startPage(pageNo,rows);
        ServiceStatusInfo<List<StoreSimpleInfo>> statusInfo = this.storeServiceImpl.searchStore(input);
        if (statusInfo.isSuccess())return new ResponsePageInfoData<>(0,"",statusInfo.getData(),pageInfo.getTotal());
        return new ResponsePageInfoData<>(1,"查询失败",null,0);
    }

    @RequiresAuthentication
    @RequestMapping(value = "/Info/{storeId}", method = RequestMethod.GET)
    @ApiOperation(value = "展示店铺基本信息")
    public ResponseData<StoreInfo> selectByStoreId(@PathVariable long storeId){
        ServiceStatusInfo<StoreInfo> statusInfo = this.storeServiceImpl.selectByStoreId(storeId);
        if (statusInfo.isSuccess())return new ResponseData<>(0,"",statusInfo.getData());
        return new ResponseData<>(1,"查询失败",null);
    }
    @RequiresAuthentication
    @RequestMapping(value = "/update/storeStatus/{storeId}/{legalSubjectId}", method = RequestMethod.GET)
    @ApiOperation(value = "店铺 的上线和下线 state为0时上线,为1时下线")
    public ResponseData<Integer> updateStoreStatus(@PathVariable long storeId,
                                                   @PathVariable long legalSubjectId,
                                                   @RequestParam int state){
        ServiceStatusInfo<Integer> statusInfo = this.storeServiceImpl.updateStoreStatus(storeId,legalSubjectId,state);
        if (statusInfo.isSuccess())return new ResponseData<>(0,"",statusInfo.getData());
        return new ResponseData<>(1,"更新失败",null);
    }
    @RequiresAuthentication
    @RequestMapping(value = "/review/store/{storeId}/{legalSubjectId}", method = RequestMethod.GET)
    @ApiOperation(value = "店铺的审核")
    public ResponseData<Integer> reviewStore(@PathVariable long storeId,
                                             @PathVariable long legalSubjectId,
                                             @RequestBody ReviewStoreInput input){
        ServiceStatusInfo<Integer> statusInfo = this.storeServiceImpl.reviewStore(storeId,legalSubjectId,input);
        if (statusInfo.isSuccess())return new ResponseData<>(0,"",statusInfo.getData());
        return new ResponseData<>(1,"审核失败",null);
    }

}
