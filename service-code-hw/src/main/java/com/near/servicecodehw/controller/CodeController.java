package com.near.servicecodehw.controller;

import com.near.servicecodehw.service.CodeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author near
 * @description 验证码服务
 * @date 2020-10-13 16:48
 **/
@RestController
@Slf4j
@RequestMapping("code")
public class CodeController {

    @Resource
    private CodeService codeService;

    @RequestMapping(value = "/create/{email}",method = {RequestMethod.GET,RequestMethod.POST})
    public boolean sendCode(@PathVariable String email){
        log.info("注册邮箱：{}",email);
        return codeService.sendCode(email);
    }

    @RequestMapping(value = "/validate/{email}/{code}",method = {RequestMethod.GET,RequestMethod.POST})
    public Integer sendCode(@PathVariable String email,@PathVariable String code){
        log.info("验证邮箱：{}",email);
        return codeService.validate(email,code);
    }
}
