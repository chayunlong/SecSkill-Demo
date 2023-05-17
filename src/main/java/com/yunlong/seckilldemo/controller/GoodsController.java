package com.yunlong.seckilldemo.controller;



import com.yunlong.seckilldemo.pojo.User;
import com.yunlong.seckilldemo.service.IGoodsService;
import com.yunlong.seckilldemo.service.IUserService;
import com.yunlong.seckilldemo.vo.GoodsVo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.concurrent.TimeUnit;


@Controller
@RequestMapping("/goods")
public class GoodsController {


    @Autowired
    private IUserService iUserService;


    //引入redis模板
    @Autowired
    private RedisTemplate redisTemplate;


    //实现手动渲染的对象
    @Autowired
    private ThymeleafViewResolver thymeleafViewResolver;

    @Autowired
    private IGoodsService goodsService;
    //将商品页面缓存在Redis中   将html页面缓存到 redis中 添加@ResponseBody
    @RequestMapping(value="/list",produces = "text/html;charset=utf-8")
    @ResponseBody     //传入一个对象
    public String toList(Model model, User user, HttpServletRequest request, HttpServletResponse response){
        //从redis中获取页面 如果不为空 直接返回页面
        ValueOperations valueOperations = redisTemplate.opsForValue();
        String html=((String)valueOperations.get("goods_list"));  //该名字为HTML文件的名字 不能随便取
        if(!StringUtils.isEmpty(html)){
            return html;
        }

//        if(StringUtils.isEmpty(ticket)){
//            return "login";
//        }
//       // User user = (User) session.getAttribute(ticket);  //获取session中的ticket的值
//
//        //直接从redis中取值
//        User user = iUserService.getUserByTicket(ticket, request, response);
//
//        if(user==null){
//            return "login";
//        }
        model.addAttribute("user",user); //添加model中用户的user 可以将用户传递到前端
        model.addAttribute("goodsList",goodsService.findGoodsVo());

        //如果为空 则手动渲染 存入Redis并返回
        WebContext context = new WebContext(request, response, request.getServletContext(), request.getLocale(),model.asMap());
        html = thymeleafViewResolver.getTemplateEngine().process("goods_list", context);
        if(!StringUtils.isEmpty(html)){
            //存入到redis中  60和TimeUnit.SECONDS表示失效时间为60秒
            valueOperations.set("goods_list",html,60, TimeUnit.SECONDS);

        }
        //不做页面跳转
        //return "goods_list";

        return html;
    }


    @RequestMapping(value="/goods_detail/{goodsId}",produces = "text/html;charset=utf-8")
    @ResponseBody
    public String toGoodsDetail(Model model,User user,@PathVariable int goodsId,HttpServletRequest request,HttpServletResponse response){
        ValueOperations valueOperations = redisTemplate.opsForValue();
        String html = ((String)valueOperations.get("goods_detail:" + goodsId));
        if(!StringUtils.isEmpty(html)){
            return html;
        }
        model.addAttribute("user",user);
        GoodsVo goodsVo=goodsService.findGoodsVoByGoodsId(goodsId);
        int seckillStatus =0;
        int remainSeconds=0;
        Date nowDate=new Date();
        if(nowDate.before(goodsVo.getStartDate())){
            remainSeconds = ((int) ((goodsVo.getStartDate().getTime() - nowDate.getTime())/1000));
        }else if(nowDate.after(goodsVo.getEndDate())){
            //秒杀已结束
            seckillStatus=2;
            remainSeconds=-1;
        }else{
            //秒杀中
            seckillStatus=1;
            remainSeconds=0;

        }
        model.addAttribute("remainSeconds",remainSeconds);
        model.addAttribute("seckillStatus",seckillStatus);
        model.addAttribute("goods",goodsService.findGoodsVoByGoodsId(goodsId));
        //如果html为空 则手动渲染
        WebContext context = new WebContext(request,response,request.getServletContext(),request.getLocale(),model.asMap());
        html = thymeleafViewResolver.getTemplateEngine().process("goods_detail", context);
        if(!StringUtils.isEmpty(html)){
            //存入Redis中 设置失效时间 60秒
            valueOperations.set("goods_detail:"+goodsId,html,60,TimeUnit.SECONDS);
        }
        return html;
    }
}
