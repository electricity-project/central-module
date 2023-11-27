package com.electricity.project.centralmodule.domains.gateway;

import com.electricity.project.centralmodule.domains.gateway.properties.RouteProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.Buildable;
import org.springframework.cloud.gateway.route.builder.PredicateSpec;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Function;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class MainGatewayRoute {

    private final RouteProperties routeProperties;

    @Bean
    public RouteLocator mainRouteLocator(RouteLocatorBuilder builder) {
        RouteLocatorBuilder.Builder routeBuilder = builder.routes();
        routeProperties.getRoutes()
                .forEach(gatewayRoute -> routeBuilder.route(
                        gatewayRoute.getName(),
                        configureGateway(gatewayRoute.getMappingUrl(), gatewayRoute.getApplicationUrl()))
                );
        return routeBuilder.build();
    }

    private Function<PredicateSpec, Buildable<Route>> configureGateway(String mappingUrl, String applicationUrl) {
        return r -> r.path(mappingUrl + "/**")
                .filters(f -> f.rewritePath(mappingUrl, ""))
                .uri(applicationUrl);
    }
}
