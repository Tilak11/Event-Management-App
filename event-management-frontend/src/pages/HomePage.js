// src/pages/HomePage.js
import React, { useEffect, useState } from 'react';
import Header from '../Header';
import EventList from '../components/EventList';
import EventForm from '../components/EventForm';
import { fetchEvents, createEvent, updateEvent, deleteEvent } from '../services/eventService';

const HomePage = () => {
  const [events, setEvents] = useState([]);
  const [selectedEvent, setSelectedEvent] = useState(null); // Tracks the event being edited

  useEffect(() => {
    loadEvents();
  }, []);

  const loadEvents = async () => {
    const response = await fetchEvents();
    setEvents(response);
  };

  const handleAddEvent = async (eventData) => {
    await createEvent(eventData);
    loadEvents();
    setSelectedEvent(null); // Clear selection after creating a new event
  };

  const handleUpdateEvent = async (eventData) => {
    await updateEvent(selectedEvent.id, eventData);
    loadEvents();
    setSelectedEvent(null); // Clear selection after updating an event
  };

  const handleDeleteEvent = async (eventId) => {
    await deleteEvent(eventId);
    loadEvents();
  };

  const handleCancelEdit = () => {
    setSelectedEvent(null); // Clear selected event and reset form
  };

  return (
    <div>
            <Header />

      <h1>Event Management</h1>

      {events.length === 0 ? (
        <div style={{ display: 'flex', justifyContent: 'center', alignItems: 'center', height: '200px' }}>
          <p>No events available at the moment.</p>
        </div>
      ) : (
        <EventList
        events={events}
        onEdit={setSelectedEvent} // Set the event to be edited
        onDelete={handleDeleteEvent}
      />
      )}
      

      <h2>{selectedEvent ? 'Edit Event' : 'Create New Event'}</h2>
      <EventForm
        eventData={selectedEvent} // Pass selected event for editing
        onAddEvent={selectedEvent ? handleUpdateEvent : handleAddEvent}
        onCancel={handleCancelEdit} // Handle form cancel button
      />
    </div>
  );
};

export default HomePage;
  