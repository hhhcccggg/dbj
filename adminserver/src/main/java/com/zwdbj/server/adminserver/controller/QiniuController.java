package com.zwdbj.server.adminserver.controller;

import com.zwdbj.server.adminserver.identity.RoleIdentity;
import com.zwdbj.server.basemodel.model.ResponseData;
import com.zwdbj.server.basemodel.model.ResponseDataCode;
import com.zwdbj.server.common.qiniu.QiniuService;
import com.zwdbj.server.config.settings.AppSettingConfigs;
import com.zwdbj.server.tokencenter.model.UserToken;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

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
    @RequiresAuthentication
    @ApiOperation(value = "七牛持久化")
    @RequestMapping(value = "/qiuniu/fops",method = RequestMethod.GET)
    public ResponseData<Boolean> fops(@RequestParam String key) {

        boolean a = this.qiniuService.watermark(key);
        return new ResponseData<>(ResponseDataCode.STATUS_NORMAL,"",a);
    }
    @ApiOperation(value = "七牛异步通知")
    @RequestMapping(value = "/qiuniu/notify",method = RequestMethod.POST)
    public void notify(HttpServletRequest request, HttpServletResponse response) throws Exception{

        Map<String,String> params = new HashMap<>();
        Map requestParams = request.getParameterMap();
        for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用。
            valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, valueStr);
        }

        System.out.println("request = [" + request + "], response = [" + response + "]");
        System.out.println("params.toString() = " + params.toString());
    }
}
