package com.zwdbj.server.adminserver.controller.shop;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zwdbj.server.adminserver.service.user.model.CreateUserInput;
import com.zwdbj.server.adminserver.service.user.model.UserModel;
import com.zwdbj.server.adminserver.service.user.model.UserShopSearchInput;
import com.zwdbj.server.adminserver.service.user.model.UserShopSelectInput;
import com.zwdbj.server.adminserver.service.user.service.UserService;
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
@RequestMapping("/api/shop/user/dbj")
@Api(description = "员工相关")
public class UserShopController {

    @Autowired
    UserService userService;

    @PostMapping("/findPage")
    @ApiOperation(value = "分页条件查询")
    public ResponsePageInfoData<List<UserShopSelectInput>> findPage(@RequestParam(value = "pageNo", required = true, defaultValue = "1") int pageNo,
                                                          @RequestParam(value = "rows", required = true, defaultValue = "30") int rows,
                                                          @RequestBody UserShopSearchInput userShopSearchInput){
        PageHelper.startPage(pageNo,rows);
        //商户ID固定
        long id = 110L;
        ServiceStatusInfo<List<UserShopSelectInput>> serviceStatusInfo = userService.selectStaff(userShopSearchInput,id);
        PageInfo<UserShopSelectInput> pageInfo =new PageInfo(serviceStatusInfo.getData());
        return new ResponsePageInfoData(ResponseDataCode.STATUS_NORMAL, serviceStatusInfo.getMsg(), pageInfo.getList(), pageInfo.getTotal());
    }

    @PostMapping("/createUserShop")
    @ApiOperation(value = "创建员工")
     public ResponseData<Long> createUserShop(@RequestBody @Valid CreateUserInput input){
        ServiceStatusInfo<Long> serviceStatusInfo = this.userService.createUserShop(input);
        if (serviceStatusInfo.isSuccess()) {
            return new ResponseData(ResponseDataCode.STATUS_NORMAL, "", serviceStatusInfo.getData());
        }
        return new ResponseData(ResponseDataCode.STATUS_ERROR, serviceStatusInfo.getMsg(), null);
     }

     @GetMapping("/findById/{id}")
     @ApiOperation(value = "查询员工")
     public ResponseData<UserModel> findById(@PathVariable long id){
         return new ResponseData(ResponseDataCode.STATUS_NORMAL, "", this.userService.findUserById(id));
     }

     @PostMapping("/deleteByUser")
     @ApiOperation(value = "批量删除员工")
     public ResponseData<Long> deleteByUser(@RequestParam(value = "id",required = true) long[] id){
         ServiceStatusInfo<Long> serviceStatusInfo = this.userService.deleteByIds(id);
         if (serviceStatusInfo.isSuccess()) {
             return new ResponseData(ResponseDataCode.STATUS_NORMAL, "", serviceStatusInfo.getData());
         }
         return new ResponseData(ResponseDataCode.STATUS_ERROR, "", 0L);
     }

    @PostMapping("/setRepresent")
    @ApiOperation(value = "批量设置代言人")
    public ResponseData<Long> setRepresent(@RequestParam(value = "id",required = true) long[] id){

        ServiceStatusInfo<Long> serviceStatusInfo = this.userService.setRepresent(id);
        if (serviceStatusInfo.isSuccess()) {
            return new ResponseData(ResponseDataCode.STATUS_NORMAL, "", serviceStatusInfo.getData());
        }
        return new ResponseData(ResponseDataCode.STATUS_ERROR, "", 0L);
    }

    @PostMapping("/cancelRepresent")
    @ApiOperation(value = "批量取消代言人")
    public ResponseData<Long> cancelRepresent(@RequestParam(value = "id",required = true) long[] id){
        ServiceStatusInfo<Long> serviceStatusInfo = this.userService.cancelRepresent(id);
        if (serviceStatusInfo.isSuccess()) {
            return new ResponseData(ResponseDataCode.STATUS_NORMAL, "", serviceStatusInfo.getData());
        }
        return new ResponseData(ResponseDataCode.STATUS_ERROR, "", 0L);
    }
}
