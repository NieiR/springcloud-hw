package com.near.gatewayhw.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @author near
 * @description 定义全局过滤器，会对所有路由生效
 * @date 2020-10-12 14:20
 **/
@Slf4j
@Component  // 让容器扫描到，等同于注册了
public class BlackListFilter implements GlobalFilter, Ordered {
    @Value("${limit.time}")
    private String limitTime;
    @Value("${limit.count}")
    private String limitCount;

    @Resource
    private RedisTemplate redisTemplate;


    /**
     * 过滤器核心方法
     * @param exchange 封装了 request 和 response 对象的上下文
     * @param chain 网关过滤器链（包含全局过滤器和单路由过滤器）
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 思路：获取客户端 ip，判断是否在黑名单中，在的话就拒绝访问，不在的话就放行
        // 从上下文中取出 request 和 response 对象
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        String path = request.getPath().toString();
        if(path.contains("/register/")){
            // 从 request 对象中获取客户端 ip
            String clientIp = request.getRemoteAddress().getHostString();
            // 拿着 clientIp 去黑名单中查询，存在的话就决绝访问
            Object obj = redisTemplate.opsForValue().get(clientIp);
            long count =0;
            if (obj != null) {
                count = Long.parseLong(obj.toString());
            }
            //保存到 redis 中，有效时间为 60s
            redisTemplate.opsForValue().set(clientIp,count,Long.parseLong(limitTime), TimeUnit.SECONDS);

            if(Long.parseLong(limitCount)<count) {
                // 决绝访问，返回
                response.setStatusCode(HttpStatus.SEE_OTHER); // 状态码
                log.debug("=====>IP:" + clientIp + " 您频繁进⾏注册，请求已被拒绝");
                String data = "You register frequently and your request has been rejected!";
                DataBuffer wrap = response.bufferFactory().wrap(data.getBytes());
                return response.writeWith(Mono.just(wrap));
            }
        }
        // 合法请求，放行，执行后续的过滤器
        return chain.filter(exchange);
    }



    /**
     * 返回值表示当前过滤器的顺序(优先级)，数值越小，优先级越高
     * @return
     */
    @Override
    public int getOrder() {
        return 0;
    }
}
