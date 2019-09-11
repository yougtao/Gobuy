package com.gobuy.cart.controller;


import com.gobuy.cart.pojo.Cart;
import com.gobuy.cart.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


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


}// end
