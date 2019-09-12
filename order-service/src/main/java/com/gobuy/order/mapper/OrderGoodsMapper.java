package com.gobuy.order.mapper;

import com.gobuy.order.pojo.OrderGoods;
import tk.mybatis.mapper.additional.insert.InsertListMapper;
import tk.mybatis.mapper.common.Mapper;

public interface OrderGoodsMapper extends Mapper<OrderGoods>, InsertListMapper<OrderGoods> {
}
