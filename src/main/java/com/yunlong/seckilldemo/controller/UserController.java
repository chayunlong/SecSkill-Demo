package com.yunlong.seckilldemo.controller;


import com.yunlong.seckilldemo.rabbitmq.MQSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author yunlong
 * @since 2023-03-19
 */
@Controller
@RequestMapping("/user")
public class UserController {


    @Autowired
    private MQSender mqSender;



    //测试发送rabbitMq消息代码
    @RequestMapping("/mq")
    @ResponseBody
    public void mq(){
        mqSender.send("hello");
    }


}
