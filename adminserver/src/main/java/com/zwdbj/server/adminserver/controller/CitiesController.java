package com.zwdbj.server.adminserver.controller;

import com.zwdbj.server.adminserver.service.cities.model.Cities;
import com.zwdbj.server.adminserver.service.cities.model.LevelType;
import com.zwdbj.server.adminserver.service.cities.service.CitiesService;
import com.zwdbj.server.utility.model.ResponseData;
import com.zwdbj.server.utility.model.ResponseDataCode;
import com.zwdbj.server.utility.model.ServiceStatusInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/api/cities")
@Api(description = "省市区相关")
public class CitiesController {

    @Autowired
    private CitiesService citiesServiceImpl;

    @GetMapping("selectAllProvince")
    @ApiOperation(value = "查询所有省")
    public ResponseData<List<Cities>> selectAllProvince(){
        ServiceStatusInfo<List<Cities>> serviceStatusInfo = citiesServiceImpl.selectAllProvince();
        if(serviceStatusInfo.isSuccess())
            return new ResponseData<>(ResponseDataCode.STATUS_NORMAL,"",serviceStatusInfo.getData());
        return new ResponseData<>(ResponseDataCode.STATUS_NORMAL,serviceStatusInfo.getMsg(),null);
    }

    @GetMapping("selectCity")
    @ApiOperation(value = "查询省下面的市")
    public ResponseData<List<Cities>> selectCity(long parenId){
        ServiceStatusInfo<List<Cities>> serviceStatusInfo = citiesServiceImpl.selectCondition(LevelType.city,parenId);
        if(serviceStatusInfo.isSuccess())
            return new ResponseData<>(ResponseDataCode.STATUS_NORMAL,"",serviceStatusInfo.getData());
        return new ResponseData<>(ResponseDataCode.STATUS_NORMAL,serviceStatusInfo.getMsg(),null);
    }

    @GetMapping("selectStreet")
    @ApiOperation(value = "查询市下面的街道 镇 乡")
    public ResponseData<List<Cities>> selectStreet(long parenId){
        ServiceStatusInfo<List<Cities>> serviceStatusInfo = citiesServiceImpl.selectCondition(LevelType.street,parenId);
        if(serviceStatusInfo.isSuccess())
            return new ResponseData<>(ResponseDataCode.STATUS_NORMAL,"",serviceStatusInfo.getData());
        return new ResponseData<>(ResponseDataCode.STATUS_NORMAL,serviceStatusInfo.getMsg(),null);
    }

    @GetMapping("selectDistrict")
    @ApiOperation(value = "查询市下面的行政区")
    public ResponseData<List<Cities>> selectDistrict(long parenId){
        ServiceStatusInfo<List<Cities>> serviceStatusInfo = citiesServiceImpl.selectCondition(LevelType.district,parenId);
        if(serviceStatusInfo.isSuccess())
            return new ResponseData<>(ResponseDataCode.STATUS_NORMAL,"",serviceStatusInfo.getData());
        return new ResponseData<>(ResponseDataCode.STATUS_NORMAL,serviceStatusInfo.getMsg(),null);
    }
}
