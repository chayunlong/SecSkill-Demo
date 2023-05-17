package com.yunlong.seckilldemo.vo;


import com.yunlong.seckilldemo.Valiator.IsMobile;
import lombok.Data;
import org.hibernate.validator.constraints.Length;



import javax.validation.constraints.NotNull;

//获取前端登录手机号和账号
@Data
public class LoginVo {

    @NotNull   //参数非空校验
    @IsMobile   //自定义注解 自己写内部的逻辑
    private String mobile;


    @NotNull   //参数非空校验
    @Length(min = 32)  //密码最少是32位的
    private String password;

}
