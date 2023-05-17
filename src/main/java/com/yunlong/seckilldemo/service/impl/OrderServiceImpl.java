package com.yunlong.seckilldemo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yunlong.seckilldemo.mapper.OrderMapper;
import com.yunlong.seckilldemo.pojo.Order;
import com.yunlong.seckilldemo.pojo.SeckillGoods;

import com.yunlong.seckilldemo.pojo.SeckillOrder;
import com.yunlong.seckilldemo.pojo.User;
import com.yunlong.seckilldemo.service.IOrderService;
import com.yunlong.seckilldemo.service.ISeckillGoodsService;

import com.yunlong.seckilldemo.service.ISeckillOrderService;
import com.yunlong.seckilldemo.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yunlong
 * @since 2023-03-25
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements IOrderService {

    @Autowired
    private ISeckillGoodsService seckillGoodsService;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private ISeckillOrderService seckillOrderService;
    //秒杀
    @Override
    public Order seckill(User user, GoodsVo goodsVo) {
        //秒杀商品减库存
        SeckillGoods seckillGood = seckillGoodsService.getOne(new QueryWrapper<SeckillGoods>().eq("goods_id",goodsVo.getId()));
        //商品库存减 1  当前库存减1
        seckillGood.setStockCount(seckillGood.getStockCount()-1);
        //更新库存 没有判断是否有库存
        //seckillGoodsService.updateById(seckillGood);
        //解决超卖问题的方式  在数据库中添加了一个索引
        boolean result = seckillGoodsService.update(new UpdateWrapper<SeckillGoods>().setSql("stock_count=" + "stock_count-1")
                .eq("goods_id", goodsVo.getId()).gt("stock_count", 0));//gt("stock_count",0)确保库存大于0
        if(!result){
            return null;
        }
        Order order=new Order();

        order.setUserId(user.getId());
        order.setGoodsId(goodsVo.getId());
        order.setAddrId(0L);
        order.setGoodsName(goodsVo.getGoodsName());
        order.setGoodsCount(1);
        order.setGoodsPrice(seckillGood.getSeckillPrice());
        order.setOrderChannel(1);
        order.setStatus(0);
        order.setCreateDate(new Date());
        //插入订单
        orderMapper.insert(order);

        //生成秒杀订单
        SeckillOrder seckillOrder = new SeckillOrder();
        seckillOrder.setUserId(user.getId());
        seckillOrder.setOrderId(order.getId());
        seckillOrder.setGoodsId(goodsVo.getId());
        seckillOrderService.save(seckillOrder);

        return order;
    }



}
