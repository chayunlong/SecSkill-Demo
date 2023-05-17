package com.yunlong.seckilldemo.controller;



import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yunlong.seckilldemo.pojo.Order;
import com.yunlong.seckilldemo.pojo.SeckillOrder;
import com.yunlong.seckilldemo.pojo.User;
import com.yunlong.seckilldemo.service.IGoodsService;
import com.yunlong.seckilldemo.service.IOrderService;
import com.yunlong.seckilldemo.service.ISeckillOrderService;
import com.yunlong.seckilldemo.vo.GoodsVo;
import com.yunlong.seckilldemo.vo.RespBeanEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/secKill")
public class SecKillController {



    @Autowired
    private ISeckillOrderService seckillOrderService;
    @Autowired
    private IGoodsService goodsService;

    //下订单
    @Autowired
    private IOrderService orderService;

    @Autowired
    private RedisTemplate redisTemplate;



    @RequestMapping("/doSecKill")
    public String doSecKill(Model model, User user,int goodsId){
        if(user==null){
            return "login";
        }
        model.addAttribute("user",user);
        GoodsVo goodsVo = goodsService.findGoodsVoByGoodsId(goodsId);
        //判断库存
        if(goodsVo.getStockCount()<1){
            model.addAttribute("errmsg", RespBeanEnum.EMPTY_STOCK.getMessage());
            return "miaosha_fail";
        }
        //判断是否重复抢购
        //SeckillOrder seckillOrder = seckillOrderService.getOne(new QueryWrapper<SeckillOrder>().eq("user_id", user.getId()).eq("goods_id", goodsId));

        //将订单放到redis缓存中 加一个索引
        SeckillOrder seckillOrder = (SeckillOrder) redisTemplate.opsForValue().get("order:" + user.getId() + ":" + goodsId);
        if(seckillOrder!=null){
            model.addAttribute("errmsg",RespBeanEnum.REPEATE_ERROR.getMessage());
            return "miaosha_fail";
        }
        //实现秒杀  goodsVo订单对象
        Order order=orderService.seckill(user,goodsVo);
        model.addAttribute("order",order);
        model.addAttribute("goods",goodsVo);
        return "order_detail";

    }
}
