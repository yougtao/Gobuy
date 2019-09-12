package com.gobuy.order.service;


import com.gobuy.auth.entity.UserInfo;
import com.gobuy.order.Utils.OrderIdGeneration;
import com.gobuy.order.interceptor.LoginInterceptor;
import com.gobuy.order.mapper.OrderGoodsMapper;
import com.gobuy.order.mapper.OrderMapper;
import com.gobuy.order.pojo.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;

@Service
public class OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderGoodsMapper orderGoodsMapper;


    @Autowired
    OrderIdGeneration idGeneration;

    // 日志
    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);


    @Transactional
    public Long createOrder(Order order) {

        // 生成orderId
        long currentTimestamp = System.currentTimeMillis();
        long orderId = idGeneration.nextId(currentTimestamp);

        // 获取登录用户
        UserInfo user = LoginInterceptor.getLoginUser();

        // 初始化数据
        order.setId(orderId);
        order.setMemberId(user.getId());
        order.setOrderStatus(1);
        order.setCreateTime(new Timestamp(currentTimestamp));
        // 保存订单数据
        orderMapper.insertSelective(order);

        // 订单goods数据
        order.getGoodsList().forEach(goods -> {
            goods.setOrderId(orderId);
        });
        // 保存
        orderGoodsMapper.insertList(order.getGoodsList());


        logger.debug("生成订单，订单编号：{}，用户id：{}", orderId, user.getId());
        return orderId;
    }


}// end
