
package com.eventmanagement.event_registration_service.controller;

import com.eventmanagement.event_registration_service.entity.Registration;
import com.eventmanagement.event_registration_service.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins = "http://localhost:3000") 
@RestController
@RequestMapping("/api/registrations")
public class RegistrationController {

    @Autowired
    private RegistrationService registrationService;

    // Register for an event
    @PostMapping
    public Registration registerForEvent(@RequestBody Registration registration) {
        return registrationService.registerForEvent(registration);
    }

    // Update registration
    @PutMapping("/{id}")
    public Registration updateRegistration(@PathVariable Long id, @RequestBody Registration registrationDetails) {
        return registrationService.updateRegistration(id, registrationDetails);
    }

    // Cancel registration
    @DeleteMapping("/{id}")
    public void cancelRegistration(@PathVariable Long id) {
        registrationService.cancelRegistration(id);
    }

    // Get all registrations
    @GetMapping
    public List<Registration> getAllRegistrations() {
        return registrationService.getAllRegistrations();
    }

    // Get registration by ID
    @GetMapping("/{id}")
    public Registration getRegistrationById(@PathVariable Long id) {
        return registrationService.getRegistrationById(id);
    }
}
