package com.near.servicecodehw.service.impl;

import com.near.servicecodehw.service.CodeService;
import com.near.servicecodehw.service.ServiceFeignClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @author near
 * @description 大概描述所属模块和介绍
 * @date 2020-10-12 13:51
 **/
@Service
@Slf4j
public class CodeServiceImpl implements CodeService {
    @Resource
    private ServiceFeignClient serviceFeignClient;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    private static Random random = new Random();

    private static final String IDENTIFYCODE_EMAIL="IDENTIFYCODE_EMAIL_";
    private static final String IDENTIFYCODE_EMAIL_SEND="IDENTIFYCODE_EMAIL_SEND_";

    @Override
    public boolean sendCode(String email) {

        try {
            //生成短信验证码
            String randcode = getIdentifyNum(6);
            String redisKey =IDENTIFYCODE_EMAIL + email;
            String reSendKey =IDENTIFYCODE_EMAIL_SEND +email;
            boolean exists = stringRedisTemplate.hasKey(reSendKey);
            // 存在刚发送的验证码，60 秒内只能发一次
            if (exists) {
                log.info("您的操作过于频繁，请稍后再试！");
                return false;
            }
            // 验证码 5 分钟过期
            stringRedisTemplate.opsForValue().set(redisKey,randcode,300l, TimeUnit.SECONDS);
            // 验证码 60 秒内只能发一次
            stringRedisTemplate.opsForValue().set(reSendKey,randcode,60l, TimeUnit.SECONDS);
            boolean b = serviceFeignClient.sendEmail(email, randcode);

            if(!b){
                //如果发送失败，则可以重新发送
                stringRedisTemplate.delete(redisKey);
                stringRedisTemplate.delete(reSendKey);
            }
            return b;
        } catch (Exception e) {
            log.error("{}",e);
            return false;
        }
    }

    @Override
    public Integer validate(String email, String code) {
        String redisKey =IDENTIFYCODE_EMAIL + email;
        String reSendKey =IDENTIFYCODE_EMAIL_SEND +email;
        boolean exists = stringRedisTemplate.hasKey(redisKey);
        if(exists){
            if(code.equals(stringRedisTemplate.opsForValue().get(redisKey))){
                //如果验证成功，则可以重新发送
                stringRedisTemplate.delete(redisKey);
                stringRedisTemplate.delete(reSendKey);
                return 0;
            }else {
                return 1;
            }
        }else {
            return 2;
        }
    }

    /**
     * 生成 n 位的随机数
     * @param n
     * @return
     */
    private String getIdentifyNum(int n) {
        StringBuilder strCode = new StringBuilder();
        for (int i = 0; i < n; i++) {
            int code = random.nextInt(10);
            strCode.append(code);
        }
        return strCode.toString();
    }
}
