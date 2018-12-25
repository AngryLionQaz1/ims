package com.snow.ims.module.user;

import com.snow.ims.common.bean.Result;
import com.snow.ims.config.annotation.SecurityPermission;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(tags = "用户")
@RestController
@RequestMapping("user")
@SecurityPermission("register,login")
public class UserController {

    @Autowired
    private UserService userService;


    @PostMapping("createGroup")
    @ApiOperation(value = "创建群")
    public Result createGroup(@ApiParam(value = "群名",required = true)@RequestParam String groupName,
                              @ApiParam(value = "群头像",required = true)@RequestParam String groupImg){
        return userService.createGroup(groupName,groupImg);
    }

    @GetMapping("groups")
    @ApiOperation(value = "获取群列表")
    public Result groups(){
        return userService.groups();
    }

    @GetMapping("friends")
    @ApiOperation(value = "获取好友列表")
    public Result friends(){
        return userService.friends();
    }

    @PostMapping("addFriend")
    @ApiOperation(value = "添加好友")
    public Result addFriend(@ApiParam(value = "好友用户ID",required = true)@RequestParam Long friendId){
        return userService.addFriend(friendId);
    }
    @PostMapping("deleteFriend")
    @ApiOperation(value = "删除好友")
    public Result deleteFriend(@ApiParam(value = "好友ID",required = true)@RequestParam Long id){
        return userService.deleteFriend(id);
    }

    @PostMapping("searchFriend")
    @ApiOperation(value = "搜索添加好友")
    public Result searchFriend(@ApiParam(value = "名称",required = true)@RequestParam String name,
                               @ApiParam(value = "多少页",required = true)@RequestParam Integer page,
                               @ApiParam(value = "多少条",required = true)@RequestParam Integer pagesize){
        return userService.searchFriend(name,page,pagesize);
    }
    @PostMapping("register")
    @ApiOperation(value = "用户注册")
    public Result register(@ApiParam(value = "用户名",required = true)@RequestParam String username,
                           @ApiParam(value = "密码",required = true)@RequestParam String password){
        return userService.register(username,password);
    }

    @PostMapping("login")
    @ApiOperation(value = "用户登录")
    public Result login(@ApiParam(value = "用户名",required = true)@RequestParam String username,
                        @ApiParam(value = "密码",required = true)@RequestParam String password){
        return userService.login(username,password);
    }













}
