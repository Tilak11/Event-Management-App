
package com.eventmanagement.event_finance_service.entity;

import java.time.LocalDateTime;

public class Event {
    private Long id;
    private String title;
    private String description;
    private String location;
    private LocalDateTime date;
    private Integer capacity;
    private Double amountInvested;

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

	public Double getAmountInvested() {
		// TODO Auto-generated method stub
		return amountInvested;
	}

	public void setAmountInvested(double d) {
		this.amountInvested = d;
		
	}
}
