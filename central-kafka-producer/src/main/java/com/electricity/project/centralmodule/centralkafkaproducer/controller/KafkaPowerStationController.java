package com.electricity.project.centralmodule.centralkafkaproducer.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/kafka")
@Slf4j
public class KafkaPowerStationController {


    @PostMapping("/add")
    public ResponseEntity<Object> addPowerStations(@RequestBody List<String> messages) {
        log.info("Number of messages = {}", messages.size());
        return ResponseEntity.noContent().build();
    }

}
