package com.gobuy.cart.controller;


import com.gobuy.cart.pojo.Cart;
import com.gobuy.cart.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
public class CartController {

    @Autowired
    private CartService cartService;


    @PostMapping
    public ResponseEntity<Boolean> addCart(@RequestBody Cart cart) {
        Boolean bool = cartService.addCart(cart);
        if (bool == null)
            return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(bool);
    }


    @GetMapping
    public ResponseEntity<List<Cart>> queryCarts() {
        List<Cart> carts = cartService.queryCarts();
        if (carts == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(carts);
    }


    @PutMapping
    public ResponseEntity<Boolean> updateCartNum(@RequestParam("skuId") Long skuId, @RequestParam("num") Integer num) {
        Boolean bool = cartService.updateCartNum(skuId, num);
        if (bool == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(bool);
    }


    @DeleteMapping("{id}")
    public ResponseEntity<Boolean> deleteCart(@PathVariable("id") Long id) {
        Boolean bool = cartService.deleteCart(id);
        if (bool == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(bool);

    }


}// end
