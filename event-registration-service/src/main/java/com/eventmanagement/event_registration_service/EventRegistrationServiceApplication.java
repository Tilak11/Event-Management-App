
package com.eventmanagement.event_registration_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class EventRegistrationServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(EventRegistrationServiceApplication.class, args);
    }

    // Define RestTemplate as a Bean to use for inter-service communication
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
