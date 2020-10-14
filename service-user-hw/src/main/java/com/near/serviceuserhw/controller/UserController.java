package com.near.serviceuserhw.controller;

import com.near.serviceuserhw.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.net.HttpCookie;

/**
 * @author near
 * @description 大概描述所属模块和介绍
 * @date 2020-10-12 14:14
 **/
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/user")
@Slf4j
public class UserController {
    @Resource
    private UserService userService;

    @RequestMapping(value = "/hello/{name}",method = {RequestMethod.GET,RequestMethod.POST})
    public String hello(@PathVariable String name){
        return name;
    }


    @RequestMapping(value = "/register/{email}/{password}/{code}",method = {RequestMethod.GET,RequestMethod.POST})
    public boolean register(@PathVariable String email, @PathVariable String password, @PathVariable String code, HttpServletResponse response) {
        log.info("开始注册。。。");
        return userService.register(email,password,code);
    }


    @RequestMapping(value = "/isregistered/{email}",method = {RequestMethod.GET,RequestMethod.POST})
    public boolean isRegistered(@PathVariable String email) {
        log.info("判断是否注册过");
        return userService.isRegistered(email);
    }

    @RequestMapping(value = "/login/{email}/{password}",method = {RequestMethod.GET,RequestMethod.POST})
    public String login(@PathVariable String email,@PathVariable String password,HttpServletResponse response) {
        return userService.login(email,password,response);
    }


    @RequestMapping(value = "/info/{token}",method = {RequestMethod.GET,RequestMethod.POST})
    public String info(@PathVariable String token) {
        return userService.info(token);
    }



    public static void addCookieToResponse(HttpServletResponse response, String token){
        // 登录时所使用的 tokenId
        HttpCookie httpCookie = new HttpCookie("token",token);
        response.addCookie(new Cookie("token",token));
    }
}
