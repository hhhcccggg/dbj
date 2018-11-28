package com.zwdbj.server.mobileapi.controller;


import com.zwdbj.server.utility.model.ResponseData;
import com.zwdbj.server.utility.model.ResponseDataCode;
import com.zwdbj.server.mobileapi.service.share.model.ShareDto;
import com.zwdbj.server.mobileapi.service.share.service.ShareService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/share")
@Api(description = "分享相关",value = "share")
public class ShareController {
    @Autowired
    private ShareService shareService;
    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    @ApiOperation(value = "视频分享")
    public ResponseData<ShareDto> doShare(@PathVariable Long id){
        ShareDto shareDto = this.shareService.doShare(id);
        if (shareDto==null) return new ResponseData<>(ResponseDataCode.STATUS_NOT_FOUND,"分享失败",null);
        return  new ResponseData<>(ResponseDataCode.STATUS_NORMAL,"",shareDto);
    }
}
