package com.electricity.project.centralmodule.domains.powerproduction.control;

import com.electricity.project.centralmodule.domains.powerproduction.configuration.KafkaDefaultConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.builder.AggregationStrategies;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class PowerProductionRoute extends RouteBuilder {
    private Integer messageNumber = 0;

    @Override
    public void configure() {
        from(KafkaDefaultConfiguration.KAFKA_COMPONENT_NAME + ":power-stations")
            .aggregate(constant(true), AggregationStrategies.flexible(String.class)
                .accumulateInCollection(ArrayList.class)
                .pick(body()))
                .completionInterval(2000)
//                .completionTimeout(1000)
//                .completionSize(300)
                .parallelProcessing(true)
                .process(exchange -> {
                    Object body = exchange.getIn().getBody();
                    log.info("Odebrałem wiadomość numer: {}", messageNumber++);
                    log.info("Liczba skumulowanych wiadomości: {}", ((List<?>) body).size());
                })
                .marshal().json(JsonLibrary.Jackson)
                .setHeader(Exchange.HTTP_METHOD, simple("POST"))
                .setHeader(Exchange.CONTENT_TYPE, constant("application/json"))
                .log("The body was - ${body}")
                .to("http://{{calculation.database.application.address}}/kafka/add");
//                .multicast()
//                .to("bean:", "")
    }
}
