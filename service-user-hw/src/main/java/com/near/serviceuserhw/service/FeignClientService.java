package com.near.serviceuserhw.service;

import com.near.serviceuserhw.service.impl.CheckFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author near
 * @description 大概描述所属模块和介绍
 * @date 2020-10-12 14:09
 **/
// @FeignClient 表明当前类是一个 Feign 客户端，value 指定该客户端要请求的服务名称（登记到注册中心上的服务提供者的服务名称）
@FeignClient(value = "service-code-hw",fallback = CheckFallback.class,path = "/code")
public interface FeignClientService {
    // Feign 要做的事情就是，拼装 url 发起请求
    // 我们调用该方法就是调用本地接口方法，那么实际上做的是远程请求
    @RequestMapping("/validate/{email}/{code}")
    Integer validate(@PathVariable String email, @PathVariable String code);
}
