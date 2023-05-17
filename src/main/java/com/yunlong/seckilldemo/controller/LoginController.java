package com.yunlong.seckilldemo.controller;

import com.yunlong.seckilldemo.service.IUserService;
import com.yunlong.seckilldemo.vo.LoginVo;
import com.yunlong.seckilldemo.vo.RespBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

//登录
@Controller   //RequestController 默认返回为一个对象   @Controller则可以实现页面跳转
@RequestMapping("/user")
@Slf4j  //日志
public class LoginController {


    @Autowired
    private IUserService iUserServiceser;


    //登录功能
    @RequestMapping("/login")
    public String Login(){
        return "login";
    }

    @RequestMapping("/doLogin") //通过url获取js对象
    @ResponseBody
    public RespBean doLogin(@Valid LoginVo loginVo, HttpServletRequest request, HttpServletResponse response){   //对LoginVo对象进行参数校验 注解@Valid
        //System.out.println("成功到这");
        return iUserServiceser.doLogin(loginVo,request,response);
    }


}
