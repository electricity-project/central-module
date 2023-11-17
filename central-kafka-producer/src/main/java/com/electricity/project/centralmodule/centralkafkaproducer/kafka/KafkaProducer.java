package com.electricity.project.centralmodule.centralkafkaproducer.kafka;

import com.electricity.project.centralmodule.centralkafkaproducer.api.ImmutablePowerProductionDTO;
import com.electricity.project.centralmodule.centralkafkaproducer.api.PowerProductionDTO;
import com.electricity.project.centralmodule.centralkafkaproducer.api.PowerStationState;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Component
@RequiredArgsConstructor
@EnableScheduling
@Slf4j
public class KafkaProducer {

    private final List<String> ipv6List = List.of("192.168.0.1", "192.168.0.2", "192.168.1.1", "192.168.2.2");

    private final KafkaTemplate<String, String> kafkaTemplate;

    private final Random random = new Random();

    private final ObjectMapper objectMapper = JsonMapper.builder()
            .addModule(new JavaTimeModule())
            .addModule(new Jdk8Module())
            .build();

    @Value("${kafka.power.station.topic}")
    private String powerStationTopic;

    @Scheduled(fixedRateString = "${kafka.message.producer.rate.ms}")
    public void sendMessage() {
        ipv6List.forEach(ipv6 -> {
            ImmutablePowerProductionDTO powerProductionDTO = PowerProductionDTO.builder()
                    .id(Optional.empty())
                    .producedPower(random.nextLong() % 100000)
                    .ipv6Address(ipv6)
                    .timestamp(LocalDateTime.now())
                    .state(PowerStationState.WORKING)
                    .build();
            try {
                kafkaTemplate.send(powerStationTopic, objectMapper.writeValueAsString(powerProductionDTO));
            } catch (JsonProcessingException e) {
                log.error("Error processing object: {}", powerProductionDTO, e);
            }
        });

    }
}
