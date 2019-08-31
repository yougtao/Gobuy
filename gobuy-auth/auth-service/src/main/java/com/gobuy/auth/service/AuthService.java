package com.gobuy.auth.service;


import com.gobuy.auth.client.UserClient;
import com.gobuy.auth.config.AuthProperties;
import com.gobuy.auth.entity.UserInfo;
import com.gobuy.auth.utils.JwtUtils;
import com.gobuy.user.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserClient userClient;

    @Autowired
    private AuthProperties authProperties;

    public String accredit(String username, String password) {
        // 查询并判断
        User user = userClient.queryUser(username, password);
        if (user == null)
            return null;

        //生成token
        try {
            return JwtUtils.generateToken(new UserInfo(user.getId(), user.getUsername()), authProperties.getPrivateKey(), authProperties.getExpire());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
