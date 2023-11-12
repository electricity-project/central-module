package com.electricity.project.centralmodule.centralkafkaproducer.kafka;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@EnableScheduling
public class KafkaProducer {

    private long iterator = 0L;

    private final KafkaTemplate<String, String> kafkaTemplate;

    @Value("${kafka.power.station.topic}")
    private String powerStationTopic;

    @Scheduled(fixedRateString = "${kafka.message.producer.rate.ms}")
    public void sendMessage(){
        kafkaTemplate.send(powerStationTopic, "To jest wiadomość numer: " + iterator++);
    }
}
