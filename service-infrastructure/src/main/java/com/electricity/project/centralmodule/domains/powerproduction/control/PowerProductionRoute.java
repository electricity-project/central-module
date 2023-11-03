package com.electricity.project.centralmodule.domains.powerproduction.control;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class PowerProductionRoute extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        from(KafkaDefaultConfiguration.KAFKA_COMPONENT_NAME + ":power-stations")
                .log("Odebrałem wiadomość");
//                .multicast()
//                .to("bean:", "")
    }
}
