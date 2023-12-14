package com.electricity.project.centralmodule.domains.powerproduction.control;

import com.electricity.project.centralmodule.domains.powerproduction.configuration.KafkaDefaultConfiguration;
import com.electricity.project.centralmodule.powerproduction.PowerProductionDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.AggregationStrategies;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
@Slf4j
public class PowerProductionRoute extends RouteBuilder {

    private static final String CALCULATIONS_POWER_PRODUCTION_ENDPOINT = "/power-production/list";

    @Override
    public void configure() {
        from(KafkaDefaultConfiguration.KAFKA_COMPONENT_NAME + ":power-stations")
            .unmarshal().json(JsonLibrary.Jackson, PowerProductionDTO.class)
            .aggregate(constant(true), AggregationStrategies
                        .flexible(PowerProductionDTO.class)
                        .accumulateInCollection(ArrayList.class)
                        .pick(body())
            ).completionInterval(2000)
//                .completionTimeout(1000)
//                .completionSize(300)
            .parallelProcessing(true)
            .marshal().json(JsonLibrary.Jackson, String.class)
            .setHeader(Exchange.HTTP_METHOD, simple("POST"))
            .setHeader(Exchange.CONTENT_TYPE, constant("application/json"))
            .log(LoggingLevel.INFO, log, "The body was - ${body}")
            .to("{{calculation.database.application.address}}" + CALCULATIONS_POWER_PRODUCTION_ENDPOINT);
    }
}
