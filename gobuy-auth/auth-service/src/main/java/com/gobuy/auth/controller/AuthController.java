package com.gobuy.auth.controller;


import com.gobuy.auth.config.AuthProperties;
import com.gobuy.auth.entity.UserInfo;
import com.gobuy.auth.service.AuthService;
import com.gobuy.auth.utils.JwtUtils;
import com.gobuy.common.utils.CookieUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
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

    /*
     * 登录
     * */
    @PostMapping("accredit")
    public ResponseEntity<Boolean> accredit(@RequestParam("username") String username, @RequestParam("password") String password,
                                            HttpServletRequest request, HttpServletResponse response) {
        String token = authService.accredit(username, password);

        if (StringUtils.isBlank(token))
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        CookieUtils.setCookie(request, response, authProperties.getCookieName(), token, authProperties.getExpire() * 60);

        return ResponseEntity.ok(true);
    }


    /*
     * 验证
     * */
    @RequestMapping("verify")
    public ResponseEntity<UserInfo> verify(@CookieValue("user-identity") String token) {

        try {
            UserInfo userInfo = JwtUtils.getInfoFromToken(token, authProperties.getPublicKey());
            return ResponseEntity.ok(userInfo);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}// end
