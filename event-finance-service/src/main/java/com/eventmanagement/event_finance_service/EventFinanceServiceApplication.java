
package com.eventmanagement.event_finance_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class EventFinanceServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(EventFinanceServiceApplication.class, args);
    }

    // Define RestTemplate as a Bean to use for inter-service communication
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
