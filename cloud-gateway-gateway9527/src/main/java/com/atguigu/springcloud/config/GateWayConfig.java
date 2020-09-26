package com.atguigu.springcloud.config;

import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.PredicateSpec;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Function;

@Configuration
public class GateWayConfig {

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder routeLocatorBuilder) {
        RouteLocatorBuilder.Builder routes = routeLocatorBuilder.routes();
        //使用网关配置百度新闻的案列
        routes.route("path_route_atguigu",
                new Function<PredicateSpec, Route.AsyncBuilder>() {
                    @Override
                    public Route.AsyncBuilder apply(PredicateSpec r) {
                        return r.path("/guonei")
                                .uri("http://news.baidu.com/guonei");
                    }
                }
        ).build();

        //使用网关配置https://www.jd.com的案列
        routes.route("jd.com",r->r.path("/shopping").uri("https://www.jd.com")).build();



        return routes.build();
    }

}
