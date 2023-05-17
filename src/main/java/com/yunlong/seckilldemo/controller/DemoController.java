package com.yunlong.seckilldemo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/demo")
public class DemoController {


    /*
      功能描述：测试页面跳转
     */
    @RequestMapping("/hello")
    public String hello(Model model){
        model.addAttribute("Name","时间常数");
        System.out.println("世界安静了");
        return "hello";
    }
}
