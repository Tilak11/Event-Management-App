
package com.eventmanagement.event_creation_service.service;

import com.eventmanagement.event_creation_service.entity.Event;
import com.eventmanagement.event_creation_service.exception.ResourceNotFoundException;
import com.eventmanagement.event_creation_service.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    // Create Event
    public Event createEvent(Event event) {
        return eventRepository.save(event);
    }

    // Get all Events
    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    // Get Event by ID
    public Event getEventById(Long id) {
        return eventRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found with id: " + id));
    }

    // Update Event, including amount invested
    public Event updateEvent(Long id, Event eventDetails) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found with id: " + id));

        event.setTitle(eventDetails.getTitle());
        event.setDescription(eventDetails.getDescription());
        event.setLocation(eventDetails.getLocation());
        event.setDate(eventDetails.getDate());
        event.setCapacity(eventDetails.getCapacity());
        event.setAmountInvested(eventDetails.getAmountInvested());

        return eventRepository.save(event);
    }

    // Delete Event
    public void deleteEvent(Long id) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found with id: " + id));
        eventRepository.delete(event);
    }
}
