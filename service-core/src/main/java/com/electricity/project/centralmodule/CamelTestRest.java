package com.electricity.project.centralmodule;

import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CamelTestRest extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        rest("/rest")
            .get("/hello")
            .to("direct:hello");

        from("direct:hello")
            .transform().simple("Hi siema")
            .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(200));
    }
}
