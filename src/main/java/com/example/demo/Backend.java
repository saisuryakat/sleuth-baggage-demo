package com.example.demo;

import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@EnableAutoConfiguration
@RestController
public class Backend {

    Logger logger = LoggerFactory.getLogger(Backend.class);

    @RequestMapping("/api")
    public String printDate(
        @RequestHeader(name = "X-Correlation-Id", required = false) String correlationId) {
        logger.info("Backend");
        if (correlationId != null) {
            return new Date().toString() + " " + correlationId;
        }
        return new Date().toString();
    }

    public static void main(String[] args) {
        SpringApplication.run(Backend.class,
            "--spring.application.name=backend",
            "--server.port=9000"
        );
    }
}
