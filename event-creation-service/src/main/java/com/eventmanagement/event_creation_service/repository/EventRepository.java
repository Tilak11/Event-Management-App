
package com.eventmanagement.event_creation_service.repository;

import com.eventmanagement.event_creation_service.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
}
