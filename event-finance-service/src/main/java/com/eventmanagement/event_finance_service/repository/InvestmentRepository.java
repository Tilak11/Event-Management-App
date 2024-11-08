
package com.eventmanagement.event_finance_service.repository;

import com.eventmanagement.event_finance_service.entity.Investment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvestmentRepository extends JpaRepository<Investment, Long> {
    // Additional query methods can be defined here if needed
}
