package com.zwdbj.server.mobileapi.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zwdbj.server.mobileapi.service.favorite.model.FavoriteDto;
import com.zwdbj.server.mobileapi.service.favorite.model.FavoriteInput;
import com.zwdbj.server.mobileapi.service.favorite.model.FavoriteModel;
import com.zwdbj.server.mobileapi.service.favorite.model.SearchFavorite;
import com.zwdbj.server.mobileapi.service.favorite.service.FavoriteService;
import com.zwdbj.server.basemodel.model.ResponseData;
import com.zwdbj.server.basemodel.model.ResponseDataCode;
import com.zwdbj.server.basemodel.model.ResponsePageInfoData;
import com.zwdbj.server.basemodel.model.ServiceStatusInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(description = "收藏相关")
@RestController
@RequestMapping(value = "/api/favorite")
public class FavoriteController {

    @Autowired
    private FavoriteService favoriteServiceImpl;

    @ApiOperation(value = "查询我的收藏")
    @GetMapping("/searchFavorite")
    public ResponsePageInfoData<List<FavoriteModel>> searchFavorite(@RequestParam(value = "pageNo", required = true, defaultValue = "1") int pageNo,
                                                                    @RequestParam(value = "rows", required = true, defaultValue = "10") int rows,
                                                                    @Valid SearchFavorite searchFavorite){
        PageHelper.startPage(pageNo,rows);
        ServiceStatusInfo<List<FavoriteModel>> serviceStatusInfo = favoriteServiceImpl.searchFavorite(searchFavorite);
        if( !serviceStatusInfo.isSuccess()){
            return new ResponsePageInfoData<>(ResponseDataCode.STATUS_ERROR , serviceStatusInfo.getMsg(),null,0L);
        }
        PageInfo pageInfo = new PageInfo(serviceStatusInfo.getData());
        return new ResponsePageInfoData<>(ResponseDataCode.STATUS_NORMAL , "",pageInfo.getList(),pageInfo.getTotal());
    }

    @ApiOperation(value = "添加收藏")
    @PostMapping("/addFavorite")
    public ResponseData<Long> addFavorite(@RequestBody @Valid FavoriteInput favoriteInput){
        ServiceStatusInfo<Long> serviceStatusInfo = favoriteServiceImpl.addFavorite(favoriteInput);
        if(!serviceStatusInfo.isSuccess())
            return new ResponseData<>(ResponseDataCode.STATUS_ERROR,serviceStatusInfo.getMsg(),null);
        return new ResponseData<>(ResponseDataCode.STATUS_NORMAL,"",serviceStatusInfo.getData());
    }

    @ApiOperation(value = "删除收藏")
    @PostMapping("/deleteFavorite")
    public ResponseData<Long> deleteFavorite(long id){
        ServiceStatusInfo<Long> serviceStatusInfo = favoriteServiceImpl.deleteFavorite(id);
        if(!serviceStatusInfo.isSuccess())
            return new ResponseData<>(ResponseDataCode.STATUS_ERROR,serviceStatusInfo.getMsg(),null);
        return new ResponseData<>(ResponseDataCode.STATUS_NORMAL,"",serviceStatusInfo.getData());
    }

    @ApiOperation(value = "取消收藏")
    @PostMapping("/cancelFavorite")
    public ResponseData<Long> cancelFavorite(@RequestBody @Valid FavoriteDto favoriteDto){
        ServiceStatusInfo<Long> serviceStatusInfo = favoriteServiceImpl.cancelFavorite(favoriteDto);
        return new ResponseData<>(serviceStatusInfo.isSuccess()?ResponseDataCode.STATUS_NORMAL:ResponseDataCode.STATUS_ERROR,
                serviceStatusInfo.getMsg(),serviceStatusInfo.getData());
    }
}
