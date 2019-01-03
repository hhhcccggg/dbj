package com.zwdbj.server.shopadmin.controller;

import com.zwdbj.server.shop_admin_service.service.legalSubject.model.LegalSubjectModel;
import com.zwdbj.server.utility.model.ResponsePageInfoData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/shop/legalSubject")
@Api(description = "商户相关")
public class LegalSubjectController {

    /*@RequestMapping(value = "/search", method = RequestMethod.GET)
    @ApiOperation(value = "查询商户")
    public ResponsePageInfoData<List<LegalSubjectModel>>*/
}
