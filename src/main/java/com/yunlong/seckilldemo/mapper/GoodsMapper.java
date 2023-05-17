package com.yunlong.seckilldemo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yunlong.seckilldemo.pojo.Goods;
import com.yunlong.seckilldemo.vo.GoodsVo;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author yunlong
 * @since 2023-03-25
 */
public interface GoodsMapper extends BaseMapper<Goods> {

    //获取列表篇
    List<GoodsVo> findGoodsVo();

    GoodsVo findGoodsVoByGoodsId(int goodsId);
}

