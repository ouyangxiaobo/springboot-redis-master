package com.spring.redis.service.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.spring.redis.dao.UserMapper;
import com.spring.redis.model.User;
import com.spring.redis.response.UserEnum;
import com.spring.redis.service.RedisService;
import com.spring.redis.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/*
 * @ClassName
 * @Decription TOO
 * @Author HanniOvO
 * @Date 2019/7/25 15:57
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    UserMapper userMapper;

    @Autowired
    RedisService redisService;


    /**
     * 获取单个用户
     *
     * @param username
     * @return User
     */
    @Override
    public User findOneUserByName(String username) {
        //设置key
        String key = "user_" + username;
        //获取redis模板
        ValueOperations<String, User> operations = redisTemplate.opsForValue();
        //判断该key是否 存在
        boolean hasKey = redisTemplate.hasKey(key);
        if (hasKey) {
            //如果有的话，就从缓存中获取
            User user = operations.get(key);
            log.info("......从缓存中获取......");
            log.info("缓存中的用户名,{}", user.getUsername());
            log.info("-------------------------------------");
            return user;
        } else {
            //如果缓存中没有，则从数据库获取
            User user = userMapper.selectUserByUserName(username);
            log.info("......从数据库中获取......");
            log.info("数据库中的用户名,{}", user.getUsername());
            log.info("-------------------------------------");
            return user;
        }

    }

    /**
     * 获取单个用户
     *
     * @param userId
     * @return User
     */
    @Override
    public User findOneUserById(Integer userId) {

        //设置key
        String key = "user_" + userId;
        //获取redis模板
        ValueOperations<String, User> operations = redisTemplate.opsForValue();
        //判断该key是否 存在
        boolean hasKey = redisTemplate.hasKey(key);
        if (hasKey) {
            //如果有的话，就从缓存中获取
            User user = operations.get(key);
            log.info("......从缓存中获取......");
            log.info("缓存中的用户名,{}", user.getId());
            log.info("-------------------------------------");
            return user;
        } else {
            //如果缓存中没有，则从数据库获取
            User user = userMapper.selectByPrimaryKey(userId);
            log.info("......从数据库中获取......");
            log.info("数据库中的用户名,{}", user.getUsername());
            log.info("-------------------------------------");
            return user;
        }
    }

    /**
     * 增加用户
     *
     * @param user
     * @return User
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<Integer, Object> addUser(User user) throws Exception {
        Map<Integer, Object> map = Maps.newHashMap();
        //判断用户信息为空
        if (user == null) {
            map.put(UserEnum.USER_IS_NULL.getCode(), UserEnum.USER_IS_NULL.getMessage());
            return map;
        }
        //判断用户名或密码为空
        if (StringUtils.isBlank(user.getUsername().trim()) || StringUtils.isBlank(user.getPassword().trim())) {
            map.put(UserEnum.USER_NAME_AND_PASS_IS_NULL.getCode(), UserEnum.USER_NAME_AND_PASS_IS_NULL.getMessage());
            return map;
        }
        //判断用户是否存在
        User userData = userMapper.selectUserByUserName(user.getUsername());
        if (userData != null) {
            map.put(UserEnum.USER_IS_EXIST.getCode(), UserEnum.USER_IS_EXIST.getMessage());
            return map;
        }
        //添加到数据库
        user.setBirthday(new Date());
        int result = userMapper.insertSelective(user);
        if (result > 0) {
            map.put(result, UserEnum.SUCCESS.getMessage());
            //添加到缓存
            String key = "user_" + user.getId();
            //判断是否已经存在该key了
            boolean hasKey = redisService.exists(key);
            //如果有，则删除该key
            if (hasKey) {
                redisService.remove(key);
                log.info(".....删除key......");
            }
            //设置缓存时间为3小时

            redisService.set(key, user, 3L);
            log.info("....增加用户加入缓存成功....,{}", key, user.toString());

            return map;
        } else {
            map.put(result, UserEnum.ERROR.getMessage());
            return map;
        }
    }


    /**
     * 编辑用户
     *
     * @param user
     * @return User
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<Integer, Object> editUser(User user) throws Exception {
        Map<Integer, Object> map = Maps.newHashMap();
        //判断用户信息为空
        if (user == null) {
            map.put(UserEnum.USER_IS_NULL.getCode(), UserEnum.USER_IS_NULL.getMessage());
            return map;
        }
        //判断用户名或密码为空
        if (StringUtils.isBlank(user.getUsername().trim()) || StringUtils.isBlank(user.getPassword().trim())) {
            map.put(UserEnum.USER_NAME_AND_PASS_IS_NULL.getCode(), UserEnum.USER_NAME_AND_PASS_IS_NULL.getMessage());
            return map;
        }

        //更新数据库
        int result = userMapper.updateByPrimaryKeySelective(user);
        if (result > 0) {
            map.put(result, UserEnum.SUCCESS.getMessage());
            String key = "user_" + user.getId();

            boolean haskey = redisService.exists(key);
            if (haskey) {
                redisService.remove(key);
                log.info(".....删除key......");
            }
            User userData = userMapper.selectByPrimaryKey(user.getId());
            if (userData != null) {
                //更新key和value,设置缓存时间为3000S
                redisService.set(key, userData, 3L);
                log.info("....编辑用户加入缓存成功....,{}", key, user.toString());

            }
            return map;

        } else {
            map.put(result, UserEnum.ERROR.getMessage());
            return map;
        }
    }


    /**
     * 查询用户
     *
     * @param
     * @return User
     */
    @Override
    public List<User> queryAllUsers() {
        List<User> userList = Lists.newArrayList();
        userList = userMapper.ListAllUsers();
        return userList;
    }

    /**
     * 删除单个用户
     *
     * @param userId
     * @return User
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<Integer, Object> deleteOneUserById(Integer userId) throws Exception {
        Map<Integer, Object> map = Maps.newHashMap();
        if (userId == null || userId == 0) {
            map.put(UserEnum.USER_ID_IS_NULL.getCode(), UserEnum.USER_ID_IS_NULL.getMessage());
            return map;
        }
        User user = userMapper.selectByPrimaryKey(userId);
        if (user == null) {
            map.put(UserEnum.USER_IS_NOT_EXIST.getCode(), UserEnum.USER_IS_NOT_EXIST.getMessage());
            return map;
        }
        int result = userMapper.deleteByPrimaryKey(userId);
        if (result > 0) {
            map.put(result, UserEnum.SUCCESS.getMessage());
            //删除key
            String key = "user_" + userId;
            boolean hasKey = redisService.exists(key);
            if (hasKey) {
                redisService.remove(key);
                log.info(".....删除用户时删除key.....,{}", key, user.toString());
            }
            return map;
        } else {
            map.put(result, UserEnum.ERROR.getMessage());
            return map;
        }
    }

    /**
     * 删除多个用户
     *
     * @param userIds
     * @return User
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<Integer, Object> deleteCheckedUsers(Integer[] userIds) throws Exception {

        Map<Integer, Object> map = Maps.newHashMap();
        if (userIds == null) {
            map.put(UserEnum.USER_ID_IS_NULL.getCode(), UserEnum.USER_ID_IS_NULL.getMessage());
            return map;
        }

        int result = userMapper.deleteCheckedUsers(userIds);
        if (result > 0) {
            map.put(result, UserEnum.SUCCESS.getMessage());
            //删除key
            for (Integer userId : userIds) {
                String key = "user_" + userId;
                boolean hasKey = redisService.exists(key);
                if (hasKey) {
                    redisService.remove(key);
                    log.info(".....删除用户时删除key.....,{}", key);

                }

            }
            log.info("用户批量删除成功....");
            return map;
        } else {
            map.put(result, UserEnum.ERROR.getMessage());
            return map;
        }
    }

}
