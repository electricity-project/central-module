package com.electricity.project.centralmodule.domains.powerproduction.control;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.builder.component.ComponentsBuilderFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaDefaultConfiguration extends RouteBuilder {

    @Value("${kafka.broker.address}")
    private String kafkaBrokerAddress;

    public static final String KAFKA_COMPONENT_NAME = "kafka";


    @Override
    public void configure() throws Exception {
        ComponentsBuilderFactory.kafka()
                .brokers(kafkaBrokerAddress)
                .register(getCamelContext(), KAFKA_COMPONENT_NAME);
    }
}
