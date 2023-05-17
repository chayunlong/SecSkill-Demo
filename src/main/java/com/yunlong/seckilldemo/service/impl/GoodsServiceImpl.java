package com.yunlong.seckilldemo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yunlong.seckilldemo.mapper.GoodsMapper;
import com.yunlong.seckilldemo.pojo.Goods;
import com.yunlong.seckilldemo.service.IGoodsService;
import com.yunlong.seckilldemo.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yunlong
 * @since 2023-03-25
 */
@Service
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, Goods> implements IGoodsService {


    @Autowired
    private GoodsMapper goodsMapper;
    @Override
    public List<GoodsVo> findGoodsVo(){
        return goodsMapper.findGoodsVo();
    }

    @Override
    public GoodsVo findGoodsVoByGoodsId(int goodsId) {
        return goodsMapper.findGoodsVoByGoodsId(goodsId);
    }


}
