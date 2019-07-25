package com.spring.redis.service;

import com.spring.redis.model.User;

import java.util.List;
import java.util.Map;

public interface UserService {

    /*单个用户*/
    User findOneUserByName(String username);

    User findOneUserById(Integer userId);


    /*增加用户*/
    Map<Integer,Object> addUser(User user) throws  Exception;

    /*编辑用户*/
    Map<Integer,Object> editUser(User user) throws  Exception;

    /*查询所有用户*/
    List<User> queryAllUsers();

    /*删除单个用户*/
    Map<Integer,Object> deleteOneUserById(Integer userId) throws  Exception;

    /*删除多个用户*/
    Map<Integer,Object> deleteCheckedUsers(Integer[] userIds) throws  Exception;


}
