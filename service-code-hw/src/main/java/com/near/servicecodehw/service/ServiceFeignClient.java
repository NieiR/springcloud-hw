package com.near.servicecodehw.service;

import com.near.servicecodehw.service.impl.SendFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author near
 * @description 大概描述所属模块和介绍
 * @date 2020-10-12 13:48
 **/
// @FeignClient 表明当前类是一个 Feign 客户端，value 指定该客户端要请求的服务名称（登记到注册中心上的服务提供者的服务名称）
@FeignClient(value = "service-email-hw",fallback = SendFallback.class,path = "/email")
public interface ServiceFeignClient {
    // Feign 要做的事情就是，拼装 url 发起请求
    // 我们调用该方法就是调用本地接口方法，那么实际上做的是远程请求
    @RequestMapping("/{email}/{code}")
    boolean sendEmail(@PathVariable String email, @PathVariable String code);
}
