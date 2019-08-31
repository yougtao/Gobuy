package com.gobuy.auth.controller;


import com.gobuy.auth.config.AuthProperties;
import com.gobuy.auth.service.AuthService;
import com.gobuy.common.utils.CookieUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@EnableConfigurationProperties(AuthProperties.class)
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private AuthProperties authProperties;

    @PostMapping("accredit")
    public ResponseEntity<Boolean> accredit(@RequestParam("username") String username, @RequestParam("password") String password,
                                            HttpServletRequest request, HttpServletResponse response) {
        String token = authService.accredit(username, password);

        if (token == null)
            return ResponseEntity.badRequest().build();

        CookieUtils.setCookie(request, response, authProperties.getCookieName(), token, authProperties.getExpire() * 60);

        return ResponseEntity.ok(true);
    }

    @RequestMapping("verify")
    public ResponseEntity<Void> verify() {

        return ResponseEntity.ok().build();
    }
}
