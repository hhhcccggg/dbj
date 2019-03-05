package com.zwdbj.server.adminserver.controller;

import com.zwdbj.server.basemodel.model.ResponseData;
import com.zwdbj.server.basemodel.model.ResponseDataCode;
import com.zwdbj.server.config.settings.AppSettingConfigs;
import com.zwdbj.server.tokencenter.model.UserToken;
import com.zwdbj.server.adminserver.service.qiniu.service.QiniuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("/api/qiniu")
@Api(description = "七牛相关",value = "Qiniu")
public class QiniuController {

    @Autowired
    QiniuService qiniuService;
    @Autowired
    private AppSettingConfigs appSettingConfigs;

    @RequestMapping(value = "/uptoken",method = RequestMethod.GET)
    @ApiOperation(value = "获取七牛上传文件token",notes = "")
    @RequiresAuthentication
    public ResponseData<UserToken> UploadToken() {
        UserToken userToken = new UserToken(qiniuService.uploadToken(),this.appSettingConfigs.getQiniuConfigs().getUptokenExpiretime());
        return new ResponseData<UserToken>(ResponseDataCode.STATUS_NORMAL,"",userToken);
    }

    @RequestMapping(value = "/url/{key}",method = RequestMethod.GET)
    @ApiOperation(value = "七牛资源地址")
    @ApiIgnore
    public RedirectView Url(@PathVariable String key) {
        String publicUrl = qiniuService.url(key);
        RedirectView redirectView = new RedirectView(publicUrl);
        redirectView.setStatusCode(HttpStatus.MOVED_PERMANENTLY);
        return redirectView;
    }
}
