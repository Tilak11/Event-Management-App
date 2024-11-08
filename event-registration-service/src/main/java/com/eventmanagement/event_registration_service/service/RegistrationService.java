
package com.eventmanagement.event_registration_service.service;

import com.eventmanagement.event_registration_service.entity.Registration;
import com.eventmanagement.event_registration_service.exception.ResourceNotFoundException;
import com.eventmanagement.event_registration_service.repository.RegistrationRepository;
import com.eventmanagement.event_registration_service.entity.Event;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class RegistrationService {

	@Autowired
	private RegistrationRepository registrationRepository;

	@Autowired
	private RestTemplate restTemplate;

	// Define the URL of the Event Creation Service (Port 8081)
//	private final String EVENT_CREATION_SERVICE_URL = "http://localhost:8081/api/events/";
//	private final String EVENT_CREATION_AUTH_URL = "http://localhost:8081/api/auth";
	
	private final String EVENT_CREATION_SERVICE_URL = "http://event-creation-service:8081/api/events/";
	private final String EVENT_CREATION_AUTH_URL = "http://event-creation-service:8081/api/auth";


	 private final String USERNAME = "user";
	    private final String PASSWORD = "mypass";

	    public Registration registerForEvent(Registration registration) {
	        // Save the attendee's registration in the local database
	        Registration savedRegistration = registrationRepository.save(registration);

	        // Notify the Event Creation Service to reduce the event's capacity
	        updateEventCapacity(registration.getEventId(), -1);

	        return savedRegistration;
	    }

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

	    // Method to communicate with Event Creation Service to update event capacity
	    private void updateEventCapacity(Long eventId, int capacityChange) {
	        String jwtToken = getJwtToken();  // Fetch a new JWT token before each request

	        HttpHeaders headers = new HttpHeaders();
	        headers.set("Authorization", "Bearer " + jwtToken);

	        String eventUrl = EVENT_CREATION_SERVICE_URL + eventId;

	        // Make an HTTP GET request with headers to get the event details
	        HttpEntity<Void> entity = new HttpEntity<>(headers);
	        ResponseEntity<Event> response = restTemplate.exchange(eventUrl, HttpMethod.GET, entity, Event.class);

	        if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
	            Event event = response.getBody();
	            // Adjust event capacity
	            event.setCapacity(event.getCapacity() + capacityChange);

	            // Make an HTTP PUT request to update the event's capacity
	            HttpEntity<Event> updateEntity = new HttpEntity<>(event, headers);
	            restTemplate.exchange(eventUrl, HttpMethod.PUT, updateEntity, Void.class);
	        } else {
	            throw new ResourceNotFoundException("Event not found with id: " + eventId);
	        }
	    }

	public Registration updateRegistration(Long id, Registration registrationDetails) {
		Registration registration = registrationRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Registration not found with id: " + id));

		registration.setAttendeeName(registrationDetails.getAttendeeName());
		registration.setAttendeeEmail(registrationDetails.getAttendeeEmail());

		return registrationRepository.save(registration);
	}

	public void cancelRegistration(Long id) {
		Registration registration = registrationRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Registration not found with id: " + id));

		// Notify Event Creation Service to increase the event capacity when
		// registration is canceled
		updateEventCapacity(registration.getEventId(), 1);

		registrationRepository.delete(registration);
	}

	public List<Registration> getAllRegistrations() {
		return registrationRepository.findAll();
	}

	public Registration getRegistrationById(Long id) {
		return registrationRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Registration not found with id: " + id));
	}
}
