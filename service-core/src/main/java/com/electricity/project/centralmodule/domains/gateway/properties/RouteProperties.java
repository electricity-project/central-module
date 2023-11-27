package com.electricity.project.centralmodule.domains.gateway.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@PropertySource(value = "classpath:routing-application.yaml", factory = YamlPropertySourceFactory.class)
@ConfigurationProperties(prefix = "gateway")
@Getter
@Setter
public class RouteProperties {
    private List<GatewayRoute> routes;

    @Getter
    @Setter
    public static class GatewayRoute {
        private String name;
        private String mappingUrl;
        private String applicationUrl;
    }

}
