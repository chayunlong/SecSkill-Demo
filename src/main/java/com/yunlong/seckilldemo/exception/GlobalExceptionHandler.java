package com.yunlong.seckilldemo.exception;


import com.yunlong.seckilldemo.vo.RespBean;
import com.yunlong.seckilldemo.vo.RespBeanEnum;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


//全局异常处理类
/*
    作用范围：单个项目中使用@RequestMapping(像@PostMapping底层是使用了@RequestMapping注解的也支持)的类
    都归他管
    作用：这是一个应用于Controller层的切面注解
 */
@RestControllerAdvice  //该注解是一个切面注解 切面注解只能对其作用范围内的操作实现切面操作
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)  //自定义抛出异常
    public RespBean ExceptionHandler(Exception e){
        if(e instanceof GlobalException){
            GlobalException ex = (GlobalException) e;
            return RespBean.error(ex.getRespBeanEnum());
        }else if ( e instanceof BindException){
            BindException ex = (BindException) e;
            RespBean respBean=RespBean.error(RespBeanEnum.BIND_ERROR);
            respBean.setMessage("参数校验异常："+ex.getBindingResult().getAllErrors().get(0).getDefaultMessage());
            return respBean;
        }
        return RespBean.error(RespBeanEnum.ERROR);
    }
}
