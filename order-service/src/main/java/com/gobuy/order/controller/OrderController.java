package com.gobuy.order.controller;

import com.gobuy.order.pojo.Order;
import com.gobuy.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class OrderController {

    @Autowired
    OrderService orderService;


    /**
     * 创建订单
     *
     * @param order 订单对象
     * @return 订单编号
     */
    @PostMapping
    public ResponseEntity<Long> createOrder(@RequestBody Order order) {
        Long id = orderService.createOrder(order);
        if (id == null)
            return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(id);
    }


}// end
