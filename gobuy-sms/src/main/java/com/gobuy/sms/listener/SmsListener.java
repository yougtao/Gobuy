package com.gobuy.sms.listener;


import com.aliyuncs.exceptions.ClientException;
import com.gobuy.sms.config.SmsProperties;
import com.gobuy.sms.utils.SmsUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;


@Component
public class SmsListener {

    @Autowired
    private SmsUtil smsUtil;

    @Autowired
    private SmsProperties smsProperties;


    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "gobuy.sms.queue", durable = "true"),
            exchange = @Exchange(value = "gobuy.sms.exchange", ignoreDeclarationExceptions = "true", type = ExchangeTypes.TOPIC),
            key = {"VerifyCode"}
    ))
    public void sendSms(Map<String, String> msg) {

        if (msg == null || msg.size() == 0)
            return;

        String phone = msg.get("phone");
        String code = msg.get("code");

        if (StringUtils.isBlank(phone) || StringUtils.isBlank(code))
            return;

        try {
            smsUtil.sendSms(phone, code, smsProperties.getVerifyCodeTemplate());
        } catch (ClientException e) {
            e.printStackTrace();
        }
    }

}// end
