package com.gobuy.user.service;


import com.gobuy.user.mapper.UserMapper;
import com.gobuy.user.pojo.User;
import com.gobuy.user.utils.CodecUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class UserService {
    @Autowired
    UserMapper userMapper;

    @Autowired
    AmqpTemplate amqpTemplate;

    @Autowired
    private StringRedisTemplate redisTemplate;

    // redis中 key前缀
    private final static String verify_prefix = "user:login:";

    /*
     * 校验数据是否可用
     * */
    public Boolean checkUser(String data, Integer type) {
        User user = new User();
        if (type == 1)
            user.setUsername(data);
        else if (type == 2)
            user.setEmail(data);
        else if (type == 3)
            user.setPhone(data);
        else
            return null;

        return userMapper.selectCount(user) == 0;
    }


    /*
     * 验证码
     * */
    public Boolean sendVerifyCode(String phone) {
        // 生成验证码
        if (StringUtils.isBlank(phone))
            return null;

        String code = "263740";

        // 发送消息到rabbitMQ
        Map<String, String> map = new HashMap<>();
        map.put("phone", phone);
        map.put("code", code);
        amqpTemplate.convertAndSend("gobuy.sms.exchange", "VerifyCode", map);

        // 保存到Redis
        redisTemplate.opsForValue().set(verify_prefix + phone, code, 2, TimeUnit.MINUTES);

        return true;
    }

    public Boolean register(User user, String code) {
        // 检验验证码
        String trueCode = redisTemplate.opsForValue().get(verify_prefix + user.getPhone());
        if (!StringUtils.equals(trueCode, code))
            return false;

        // 生成盐
        String salt = CodecUtils.generateSalt();
        user.setSalt(salt);

        // 加盐加密
        user.setPassword(CodecUtils.shaHex(user.getPassword(), salt));

        // 新增用户
        Date date = new Date();
        user.setCreateTime(new Timestamp(date.getTime()));
        userMapper.insert(user);

        // 删除redis中的code
        redisTemplate.delete(verify_prefix + user.getPhone());
        return true;
    }

    public User queryUser(String username, String password) {
        User user = new User();
        user.setUsername(username);

        User record = userMapper.selectOne(user);
        if (record == null)
            return null;

        // 获取salt并加密，然后比较
        String shaHex = CodecUtils.shaHex(password, record.getSalt());
        if (shaHex.equals(record.getPassword()))
            return record;
        else
            return null;
    }
}
