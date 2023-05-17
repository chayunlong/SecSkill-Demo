package com.yunlong.seckilldemo.exception;


import com.yunlong.seckilldemo.vo.RespBeanEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
//全局异常
public class GlobalException extends RuntimeException{   //继承RuntimeException异常

    //返回一个异常枚举
    private RespBeanEnum respBeanEnum;
}
