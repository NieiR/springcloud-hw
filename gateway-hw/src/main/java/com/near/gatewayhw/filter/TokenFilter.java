package com.near.gatewayhw.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author near
 * @description 设置一个 TokenFilter，排除特定的接口，其他接口只有当 token 检验通过了才能访问。
 * @date 2020-10-12 14:21
 **/
@Slf4j
@Component
public class TokenFilter implements GlobalFilter, Ordered {
    @Resource
    private StringRedisTemplate stringRedisTemplate;


    /**
     * 过滤器核心方法
     * @param exchange 封装了 request 和 response 对象的上下文
     * @param chain 网关过滤器链（包含全局过滤器和单路由过滤器）
     * @return
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 思路：获取客户端 ip，判断是否在黑名单中，在的话就拒绝访问，不在的话就放行
        // 从上下文中取出 request 和 response 对象
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        String path = request.getPath().toString();
        if(path.contains("user/") || path.contains("code/create/")){
            // 合法请求，放行，执行后续的过滤器
            return chain.filter(exchange);
        }else{
            // 从 request 对象中获取客户端 ip
            String clientIp = request.getRemoteAddress().getHostString();
            String s = request.getCookies().toString();
            log.info("cookies"+s);
            List<HttpCookie> list = request.getCookies().get("token");
            Boolean hasKey=false;
            if(list!=null &&!list.isEmpty()){
                HttpCookie httpCookie = list.get(0);
                String token = httpCookie.getValue();
                hasKey= stringRedisTemplate.hasKey(token);
            }

            if(!hasKey){
                // 决绝访问，返回
                response.setStatusCode(HttpStatus.FORBIDDEN); // 状态码
                log.debug("=====>IP:" + clientIp + " 您没有权限，访问被禁止");
                String data = "You do not have permission, access is forbidden";
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
        return 1;
    }
}
