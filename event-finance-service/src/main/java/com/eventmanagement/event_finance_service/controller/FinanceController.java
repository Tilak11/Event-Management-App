
package com.eventmanagement.event_finance_service.controller;

import com.eventmanagement.event_finance_service.entity.Investment;
import com.eventmanagement.event_finance_service.service.FinanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins = "http://localhost:3000") 
@RestController
@RequestMapping("/api/finance")
public class FinanceController {

    @Autowired
    private FinanceService financeService;

    // Make an investment
    @PostMapping
    public Investment investInEvent(@RequestBody Investment investment) {
        // Call the FinanceService to handle investment
        return financeService.investInEvent(investment);
    }

    // Update investment
    @PutMapping("/{id}")
    public void updateInvestment(@PathVariable Long id, @RequestBody Investment investmentDetails) {
        // Call the FinanceService to update an existing investment
        financeService.updateInvestment(id, investmentDetails);
    }

    // Delete investment
    @DeleteMapping("/{id}")
    public void deleteInvestment(@PathVariable Long id) {
        // Call the FinanceService to delete an investment
        financeService.deleteInvestment(id);
    }

    // Get all investments
    @GetMapping
    public List<Investment> getAllInvestments() {
        // Call the FinanceService to retrieve all investments
        return financeService.getAllInvestments();
    }

    // Get investment by ID
    @GetMapping("/{id}")
    public Investment getInvestmentById(@PathVariable Long id) {
        // Call the FinanceService to retrieve a specific investment by ID
        return financeService.getInvestmentById(id);
    }
}
