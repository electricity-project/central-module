package com.electricity.project.centralmodule.centralkafkaproducer.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/calculations-access/power-production")
@Slf4j
public class KafkaPowerStationController {


    @PostMapping("/list")
    public ResponseEntity<Object> addPowerStations(@RequestBody String message) {
        log.info("Message body: {}", message);
        return ResponseEntity.noContent().build();
    }

}
