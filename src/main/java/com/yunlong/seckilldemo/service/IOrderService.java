package com.yunlong.seckilldemo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yunlong.seckilldemo.pojo.Order;
import com.yunlong.seckilldemo.pojo.User;
import com.yunlong.seckilldemo.vo.GoodsVo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yunlong
 * @since 2023-03-25
 */
public interface IOrderService extends IService<Order> {

    Order seckill(User user, GoodsVo goodsVo);

}
