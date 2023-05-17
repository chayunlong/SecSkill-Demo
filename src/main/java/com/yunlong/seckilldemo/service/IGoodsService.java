package com.yunlong.seckilldemo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yunlong.seckilldemo.pojo.Goods;
import com.yunlong.seckilldemo.vo.GoodsVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yunlong
 * @since 2023-03-25
 */
public interface IGoodsService extends IService<Goods> {

    //获取商品列表
    List<GoodsVo> findGoodsVo();

    GoodsVo findGoodsVoByGoodsId(int goodsId);
}
