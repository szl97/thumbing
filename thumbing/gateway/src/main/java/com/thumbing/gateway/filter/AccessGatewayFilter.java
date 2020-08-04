package com.thumbing.gateway.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thumbing.gateway.client.IAuthServiceClient;
import com.thumbing.shared.auth.authentication.AuthorizationContextHolder;
import com.thumbing.shared.response.BaseApiResult;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.GATEWAY_ROUTE_ATTR;

/**
 * @Author: Stan Sai
 * @Date: 2020/7/14 14:39
 */
@Configuration
@Slf4j
public class AccessGatewayFilter implements GlobalFilter, Ordered {
    @Autowired
    IAuthServiceClient authService;

    @Autowired
    ObjectMapper objectMapper;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String authentication = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        String method = request.getMethodValue();
        String url = request.getPath().value();
        String destServiceName = ((Route) exchange.getAttribute(GATEWAY_ROUTE_ATTR)).getUri().getHost();
        log.debug("url:{},method:{},headers:{}", url, method, request.getHeaders());
        //todo:通过指定的serviceName以及url去auth服务调用 如果跳过则跳过，如果需要权限验证 不通过则返回false

        BaseApiResult authorized = authService.auth(authentication, destServiceName, url);
        if (!authorized.isSuccess()) {
            return unLogin(exchange);
        }
        if(Boolean.TRUE.equals(authorized.getData())) return chain.filter(exchange);
        else return unauthorized(exchange);
    }


    @SneakyThrows
    private Mono<Void> unauthorized(ServerWebExchange serverWebExchange) {
        serverWebExchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
        serverWebExchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);
        DataBuffer buffer = serverWebExchange.getResponse()
                .bufferFactory().wrap(objectMapper.writeValueAsBytes(BaseApiResult.errorServer("无权限访问")));
        return serverWebExchange.getResponse().writeWith(Flux.just(buffer));
    }

    @SneakyThrows
    private Mono<Void> unLogin(ServerWebExchange serverWebExchange) {
        serverWebExchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        serverWebExchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);
        DataBuffer buffer = serverWebExchange.getResponse()
                .bufferFactory().wrap(objectMapper.writeValueAsBytes(BaseApiResult.errorServer("尚未登陆")));
        return serverWebExchange.getResponse().writeWith(Flux.just(buffer));
    }

    @Override
    public int getOrder() {
        return 999;
    }
}
