package com.near.serviceuserhw.service.impl;

import com.near.serviceuserhw.bean.entity.User;
import com.near.serviceuserhw.mapper.UserMapper;
import com.near.serviceuserhw.service.FeignClientService;
import com.near.serviceuserhw.service.UserService;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author near
 * @description 大概描述所属模块和介绍
 * @date 2020-10-12 14:12
 **/
@Service
public class UserServiceImpl implements UserService {
    @Resource
    private FeignClientService feignClientService;


    @Resource
    private UserMapper userMapper;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    private static final String TOKEN_PRIFIX="TOKEN_PRIFIX_";

    @Override
    public boolean register(String email, String password, String code) {
        int validate = feignClientService.validate(email, code);
        //验证码校验通过，且没有注册的，才会进行注册。
        if(validate==0 && !isRegistered(email)){
            User user = new User(email, password);
            return userMapper.add(user)>0;
        }
        return false;
    }

    @Override
    public String info(String token) {
        String key =TOKEN_PRIFIX+ token;
        // token
        return stringRedisTemplate.opsForValue().get(key);
    }

    @Override
    public boolean isRegistered(String email) {
        User user = userMapper.findByEmail(email);
        return user!=null;
    }

    @Override
    public String login(String email, String password, HttpServletResponse response) {
        User user = userMapper.findByEmail(email);
        if(user!=null && password.equals(user.getPassword())){
            String key =TOKEN_PRIFIX+ UUID.randomUUID().toString();
            // token
            stringRedisTemplate.opsForValue().set(key,user.toString(),3000L, TimeUnit.SECONDS);
            Cookie cookie = new Cookie("token", key);
            cookie.setPath("/");
            response.addCookie(cookie);
            return user.toString();
        }
        return "";
    }
}
