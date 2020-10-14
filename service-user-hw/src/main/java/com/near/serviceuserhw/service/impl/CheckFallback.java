package com.near.serviceuserhw.service.impl;

import com.near.serviceuserhw.service.FeignClientService;
import org.springframework.stereotype.Service;

/**
 * @author near
 * @description 大概描述所属模块和介绍
 * @date 2020-10-12 14:11
 **/
@Service
public class CheckFallback implements FeignClientService {
    @Override
    public Integer validate(String email, String code) {
        return 2;
    }
}
