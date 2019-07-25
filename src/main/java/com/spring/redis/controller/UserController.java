package com.spring.redis.controller;

import com.spring.redis.model.User;
import com.spring.redis.service.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @ApiOperation(value = "单个用户",notes = "单个用户")
    @PostMapping("/getOneUserByName")
    public User getOneUserByName(@RequestBody String username){
        try {
            log.info("【单个用户】.....username=",username);
            User user=userService.findOneUserByName(username);
            return user;

        }catch (Exception e){
            e.printStackTrace();
            log.error("【单个用户错误】....",e.getMessage());
        }
        return null;
    }

    @ApiOperation(value = "单个用户",notes = "单个用户")
    @PostMapping("/getOneUserById")
    public User getOneUserById(@RequestBody Integer userId){
        try {
            log.info("【单个用户】.....userId=",userId);
            User user=userService.findOneUserById(userId);
            return user;

        }catch (Exception e){
            e.printStackTrace();
            log.error("【单个用户错误】....",e.getMessage());
        }
        return null;
    }

    @ApiOperation(value = "添加用户",notes = "添加用户")
    @PostMapping("/addUser")
    public Map<Integer,Object> addUser(@RequestBody User user){
        try {
            log.info("【添加用户】.....添加用户=",user.toString());
            Map<Integer,Object> map=userService.addUser(user);
            return map;

        }catch (Exception e){
            e.printStackTrace();
            log.error("【添加用户】....",e.getMessage());
        }
        return null;
    }

    @ApiOperation(value = "编辑用户",notes = "编辑用户")
    @PostMapping("/editUser")
    public Map<Integer,Object> editUser(@RequestBody User user){
        try {
            log.info("【编辑用户】.....user=",user.toString());
            Map<Integer,Object> map=userService.editUser(user);
            return map;

        }catch (Exception e){
            e.printStackTrace();
            log.error("【编辑用户】....",e.getMessage());
        }
        return null;
    }

    @ApiOperation(value = "删除单个用户",notes = "删除单个用户")
    @PostMapping("/deleteOneUser")
    public Map<Integer,Object> deleteOneUser(@RequestBody Integer userId){
        try {
            log.info("【删除单个用户】.....userId=",userId);
            Map<Integer,Object> map=userService.deleteOneUserById(userId);
            return map;

        }catch (Exception e){
            e.printStackTrace();
            log.error("【删除单个用户】....",e.getMessage());
        }
        return null;
    }

    @ApiOperation(value = "删除多个用户",notes = "删除多个用户")
    @PostMapping("/deleteCheckedUsers")
    public Map<Integer,Object> deleteCheckedUsers(@RequestParam Integer[] userIds){
        try {
            for (Integer userId : userIds){
                log.info("【删除多个用户】.....userIds=",userId);

            }
            Map<Integer,Object> map=userService.deleteCheckedUsers(userIds);
            return map;

        }catch (Exception e){
            e.printStackTrace();
            log.error("【删除多个用户】....",e.getMessage());
        }
        return null;
    }
}
