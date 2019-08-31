package com.gobuy.user.controller;

import com.gobuy.user.pojo.User;
import com.gobuy.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping("/check/{data}/{type}")
    public ResponseEntity<Boolean> checkUser(@PathVariable("data") String data, @PathVariable("type") Integer type) {
        Boolean bool = userService.checkUser(data, type);
        if (bool == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

        return ResponseEntity.ok(bool);
    }


    @PostMapping("code")
    public ResponseEntity<Boolean> sendVerifyCode(String phone) {
        Boolean bool = userService.sendVerifyCode(phone);
        if (bool == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        else
            return ResponseEntity.ok(bool);
    }


    @PostMapping("register")
    public ResponseEntity<Boolean> register(@Valid User user, @RequestParam("code") String code) {
        Boolean bool = userService.register(user, code);
        if (bool == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        return ResponseEntity.ok(bool);
    }


    @PostMapping("query")
    public ResponseEntity<User> queryUser(@RequestParam("username") String username, @RequestParam("password") String password) {
        User user = userService.queryUser(username, password);
        if (user == null) {
            return ResponseEntity.ok().build();
        } else
            return ResponseEntity.ok(user);
    }

}// end
