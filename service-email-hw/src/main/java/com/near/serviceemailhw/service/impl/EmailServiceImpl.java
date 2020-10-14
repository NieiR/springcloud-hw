package com.near.serviceemailhw.service.impl;

import com.near.serviceemailhw.service.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author near
 * @description 大概描述所属模块和介绍
 * @date 2020-10-12 13:26
 **/
@Slf4j
@Service
public class EmailServiceImpl implements EmailService {

    @Resource
    private JavaMailSender mailSender;
    @Value("${mail.from}")
    private String mailFrom;

    @Override
    public boolean sendEmail(String email, String code) {
        try {
            SimpleMailMessage message=new SimpleMailMessage();
            message.setFrom(mailFrom);
            message.setTo(email);
            message.setSubject("【程序员爱酸奶】验证码");
            message.setText("验证码【"+code+"】,5 分钟内有效，请及时使用");
            mailSender.send(message);
            log.info("邮件已经发送");
        }catch (Exception e){
            log.error("{}",e);
            return false;
        }
        return true;
    }
}
