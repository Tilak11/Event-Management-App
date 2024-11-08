
package com.eventmanagement.event_registration_service.repository;

import com.eventmanagement.event_registration_service.entity.Registration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegistrationRepository extends JpaRepository<Registration, Long> {
    // Additional query methods can be defined here if needed
}
