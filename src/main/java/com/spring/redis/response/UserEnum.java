package com.spring.redis.response;

import lombok.Data;
import lombok.Getter;

/*
 * @ClassName
 * @Decription TOO
 * @Author HanniOvO
 * @Date 2019/7/25 16:10
 */
@Getter
public enum  UserEnum {

    SUCCESS(1,"操作成功"),
    ERROR(0,"操作失败"),
    USER_IS_NULL(-1,"用户信息为空"),
    USER_NAME_IS_NULL(-2,"用户名称为空"),
    USER_PASSWORD_IS_NULL(-3,"用户密码为空"),
    USER_ID_IS_NULL(-4,"用户ID不存在"),
    USER_NAME_AND_PASS_IS_NULL(-5,"用户名称为空或者密码为空"),
    USER_PASSWORD_IS_WRONG(-6,"用户密码错误"),
    LOGIN_SUCCESS(2,"登录成功"),
    LOGIN_FAIL(-10,"登录失败"),
    USER_REDIS_TOKEN_IS_NULL(-7,"用户token已超时"),
    USER_IS_EXIST(-7,"用户已经存在,请更换用户名"),
    USER_IS_NOT_EXIST(-7,"用户不已经存在"),

    ;

    private int code;
    private String message;

    UserEnum(int code,String message){
        this.code = code;
        this.message = message;
    }
}
