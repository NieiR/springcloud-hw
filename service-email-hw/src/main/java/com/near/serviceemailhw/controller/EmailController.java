package com.near.serviceemailhw.controller;

import com.near.serviceemailhw.service.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author near
 * @description 大概描述所属模块和介绍
 * @date 2020-10-12 13:31
 **/
@RestController
@RequestMapping("/email")
@Slf4j
public class EmailController {
    @Resource
    private EmailService emailService;

    @RequestMapping(value = "/{email}/{code}",method = {RequestMethod.GET,RequestMethod.POST})
    public boolean sendEmail(@PathVariable String email, @PathVariable String code) {
        boolean b = emailService.sendEmail(email, code);
        if(b){
            log.info("发送成功，请查收");
        }else {
            log.info("发送失败，请重新发送");
        }
        return b;
    }
}
