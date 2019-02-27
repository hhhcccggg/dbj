package com.zwdbj.server.adminserver.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zwdbj.server.adminserver.identity.RoleIdentity;
import com.zwdbj.server.adminserver.service.task.model.TaskAddInput;
import com.zwdbj.server.adminserver.service.task.model.TaskModel;
import com.zwdbj.server.adminserver.service.task.model.TaskModifyInput;
import com.zwdbj.server.adminserver.service.task.model.TaskSearchInput;
import com.zwdbj.server.adminserver.service.task.service.TaskService;
import com.zwdbj.server.basemodel.model.ResponseData;
import com.zwdbj.server.basemodel.model.ResponseDataCode;
import com.zwdbj.server.basemodel.model.ResponsePageInfoData;
import com.zwdbj.server.basemodel.model.ServiceStatusInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/task")
@Api(description = "金币任务相关")
public class TaskController {

    @Autowired
    TaskService taskService;


    @RequiresAuthentication
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    @ApiOperation(value = "查询所有任务")
    @RequiresRoles(value = {RoleIdentity.ADMIN_ROLE, RoleIdentity.MARKET_ROLE}, logical = Logical.OR)
    public ResponsePageInfoData<List<TaskModel>> getAllTasks(@RequestBody TaskSearchInput input,
                                                             @RequestParam(value = "pageNo",required = true,defaultValue = "1") int pageNo,
                                                             @RequestParam(value = "rows",required = true,defaultValue = "30") int rows){
        Page<TaskModel> pageInfo = PageHelper.startPage(pageNo,rows);
        List<TaskModel> taskModels = this.taskService.getAllTasks(input);
        return new ResponsePageInfoData<>(ResponseDataCode.STATUS_NORMAL,"",taskModels,pageInfo.getTotal());
    }

    @RequiresAuthentication
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ApiOperation(value = "增加任务")
    @RequiresRoles(value = {RoleIdentity.ADMIN_ROLE, RoleIdentity.MARKET_ROLE}, logical = Logical.OR)
    public ResponseData<Integer> addNewTask(@RequestBody TaskAddInput input){

        ServiceStatusInfo<Integer>  statusInfo = this.taskService.addNewTask(input);
        if (statusInfo.isSuccess()) {
            return new ResponseData<>(ResponseDataCode.STATUS_NORMAL, "", statusInfo.getData());
        }
        return new ResponseData<>(ResponseDataCode.STATUS_ERROR, statusInfo.getMsg(), null);
    }
    @RequiresAuthentication
    @RequestMapping(value = "/search/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "根据id查询任务")
    @RequiresRoles(value = {RoleIdentity.ADMIN_ROLE, RoleIdentity.MARKET_ROLE}, logical = Logical.OR)
    public ResponseData<TaskModel> getTaskById(@PathVariable String id){
        ServiceStatusInfo<TaskModel>  statusInfo = this.taskService.getTaskById(id);
        if (statusInfo.isSuccess()) {
            return new ResponseData<>(ResponseDataCode.STATUS_NORMAL, "", statusInfo.getData());
        }
        return new ResponseData<>(ResponseDataCode.STATUS_ERROR, statusInfo.getMsg(), null);
    }

    @RequiresAuthentication
    @RequestMapping(value = "/modify/{id}", method = RequestMethod.POST)
    @ApiOperation(value = "增加任务")
    @RequiresRoles(value = {RoleIdentity.ADMIN_ROLE, RoleIdentity.MARKET_ROLE}, logical = Logical.OR)
    public ResponseData<Integer> modifyTaskById(@RequestBody TaskModifyInput input,@PathVariable String id){

        ServiceStatusInfo<Integer>  statusInfo = this.taskService.modifyTaskById(id,input);
        if (statusInfo.isSuccess()) {
            return new ResponseData<>(ResponseDataCode.STATUS_NORMAL, "", statusInfo.getData());
        }
        return new ResponseData<>(ResponseDataCode.STATUS_ERROR, statusInfo.getMsg(), null);
    }
    @RequiresAuthentication
    @RequestMapping(value = "/del/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "删除任务")
    @RequiresRoles(value = {RoleIdentity.ADMIN_ROLE, RoleIdentity.MARKET_ROLE}, logical = Logical.OR)
    public ResponseData<Integer> delTaskById(@PathVariable String id){

        ServiceStatusInfo<Integer>  statusInfo = this.taskService.delTaskById(id);
        if (statusInfo.isSuccess()) {
            return new ResponseData<>(ResponseDataCode.STATUS_NORMAL, "", statusInfo.getData());
        }
        return new ResponseData<>(ResponseDataCode.STATUS_ERROR, statusInfo.getMsg(), null);
    }
}
