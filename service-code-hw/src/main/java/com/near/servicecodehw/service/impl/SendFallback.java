package com.near.servicecodehw.service.impl;

import com.near.servicecodehw.service.ServiceFeignClient;
import org.springframework.stereotype.Service;

/**
 * @author near
 * @description 降级的实现类
 * @date 2020-10-12 13:49
 **/
@Service
public class SendFallback implements ServiceFeignClient {
    @Override
    public boolean sendEmail(String email, String code) {
        return false;
    }
}
