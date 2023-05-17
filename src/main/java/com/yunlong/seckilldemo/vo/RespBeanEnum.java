package com.yunlong.seckilldemo.vo;


import lombok.*;

//公共返回对象枚举
//@Data
@Getter
@ToString
@AllArgsConstructor
//枚举类型
public enum RespBeanEnum {


    SUCCESS(200,"SUCCESS"),
    ERROR(500,"服务端异常"),

    LOGIN_ERROR(500210,"密码或账号错误"),
    MOBILE_ERROR(500211,"手机号出现错误"),

    PASSWORD_ERROR(500212,"密码错误"),
    BIND_ERROR(500213,"参数校验异常"),
    MOBILE_NOT_EXIST(500214,"该手机号不存在"),
    PASSWORD_UPDATE_FAIL(500215,"密码更新失败"),
    EMPTY_STOCK(500300,"已售罄"),
    REPEATE_ERROR(500301,"每人限购一件")
    ;



    private final Integer code;
    private final String message;
}
