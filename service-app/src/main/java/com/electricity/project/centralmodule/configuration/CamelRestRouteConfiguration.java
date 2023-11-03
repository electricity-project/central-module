package com.electricity.project.centralmodule.configuration;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CamelRestRouteConfiguration extends RouteBuilder {

    @Value("${server.port}")
    private int serverPort;

    @Override
    public void configure() throws Exception {
        restConfiguration()
                .dataFormatProperty("prettyPrint", "true")
                .component("servlet")
                .bindingMode(RestBindingMode.json)
                .port(serverPort);
    }
}
