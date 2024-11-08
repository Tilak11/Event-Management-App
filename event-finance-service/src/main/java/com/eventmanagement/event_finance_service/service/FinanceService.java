package com.eventmanagement.event_finance_service.service;

import com.eventmanagement.event_finance_service.entity.Investment;
import com.eventmanagement.event_finance_service.exception.ResourceNotFoundException;
import com.eventmanagement.event_finance_service.repository.InvestmentRepository;
import com.eventmanagement.event_finance_service.entity.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class FinanceService {

    @Autowired
    private InvestmentRepository investmentRepository;

    @Autowired
    private RestTemplate restTemplate;

//    private final String EVENT_CREATION_SERVICE_URL = "http://localhost:8081/api/events/";
//    private final String EVENT_CREATION_AUTH_URL = "http://localhost:8081/api/auth";
    
    private final String EVENT_CREATION_SERVICE_URL = "http://event-creation-service:8081/api/events/";
    private final String EVENT_CREATION_AUTH_URL = "http://event-creation-service:8081/api/auth";

	 private final String USERNAME = "user";
	    private final String PASSWORD = "mypass";

    // Method to fetch a new JWT token
    private String getJwtToken() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");

        String authRequestBody = String.format("{\"username\":\"%s\", \"password\":\"%s\"}", USERNAME, PASSWORD);
        HttpEntity<String> request = new HttpEntity<>(authRequestBody, headers);

        ResponseEntity<String> response = restTemplate.exchange(EVENT_CREATION_AUTH_URL, HttpMethod.POST, request, String.class);

        if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
            // Assuming the token is returned in the response body directly
            return response.getBody();
        } else {
            throw new RuntimeException("Failed to fetch JWT token");
        }
    }

    // Invest in event
    public Investment investInEvent(Investment investment) {
        // Fetch event details from Event Creation Service
        Event event = verifyEventExists(investment.getEventId());

        // Update the event's amount invested
        updateEventInvestment(event, investment.getAmount());

        // Save the investment in the local database
        return investmentRepository.save(investment);
    }

    // Method to verify if the event exists
    private Event verifyEventExists(Long eventId) {
        String jwtToken = getJwtToken();  // Fetch a new JWT token before each request

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + jwtToken);

        String eventUrl = EVENT_CREATION_SERVICE_URL + eventId;
        HttpEntity<Void> entity = new HttpEntity<>(headers);
        ResponseEntity<Event> response = restTemplate.exchange(eventUrl, HttpMethod.GET, entity, Event.class);

        if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
            return response.getBody();
        } else {
            throw new ResourceNotFoundException("Event not found with id: " + eventId);
        }
    }

    // Method to update the event's amount invested
    private void updateEventInvestment(Event event, Double amount) {
        String jwtToken = getJwtToken();  // Fetch a new JWT token before each request

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + jwtToken);

        event.setAmountInvested(event.getAmountInvested() + amount);
        HttpEntity<Event> updateEntity = new HttpEntity<>(event, headers);
        restTemplate.exchange(EVENT_CREATION_SERVICE_URL + event.getId(), HttpMethod.PUT, updateEntity, Void.class);
    }

    // Get all investments
    public List<Investment> getAllInvestments() {
        return investmentRepository.findAll();
    }

    // Get investment by ID
    public Investment getInvestmentById(Long id) {
        return investmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Investment not found with id: " + id));
    }

    // Update investment
    public void updateInvestment(Long id, Investment investmentDetails) {
        String jwtToken = getJwtToken();  // Fetch a new JWT token before each request

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + jwtToken);

        // Fetch the event details from the Event Creation Service with authorization header
        String eventUrl = EVENT_CREATION_SERVICE_URL + id;
        HttpEntity<Void> entity = new HttpEntity<>(headers);
        ResponseEntity<Event> response = restTemplate.exchange(eventUrl, HttpMethod.GET, entity, Event.class);

        if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
            Event event = response.getBody();

            // Update the event's amount invested
            event.setAmountInvested(event.getAmountInvested() + investmentDetails.getAmount());

            // Send the updated event details back to the Event Creation Service with authorization header
            HttpEntity<Event> updateEntity = new HttpEntity<>(event, headers);
            restTemplate.exchange(eventUrl, HttpMethod.PUT, updateEntity, Void.class);
        } else {
            throw new ResourceNotFoundException("Event not found with id: " + id);
        }
    }

    // Delete investment
    public void deleteInvestment(Long id) {
        Investment investment = investmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Investment not found with id: " + id));

        investmentRepository.delete(investment);
    }
}
