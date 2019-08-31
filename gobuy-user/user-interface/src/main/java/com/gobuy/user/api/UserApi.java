package com.gobuy.user.api;

import com.gobuy.user.pojo.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


public interface UserApi {

    @PostMapping("query")
    public User queryUser(@RequestParam("username") String username, @RequestParam("password") String password);

}

