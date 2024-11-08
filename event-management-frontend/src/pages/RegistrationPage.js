// src/pages/RegistrationPage.js
import React, { useEffect, useState } from 'react';
import Header from '../Header';
import { useNavigate } from 'react-router-dom';
import EventList from '../components/EventList';
import RegistrationForm from '../components/RegistrationForm';
import { fetchEvents } from '../services/eventService';
import { getRegistrations, registerForEvent, updateRegistration, cancelRegistration } from '../services/registrationService';

const RegistrationPage = () => {
  const [events, setEvents] = useState([]);
  const [registrations, setRegistrations] = useState([]);
  const [selectedRegistration, setSelectedRegistration] = useState(null);
  const [selectedEvent, setSelectedEvent] = useState(null);
  const navigate = useNavigate();

  useEffect(() => {
    loadEvents();
    loadRegistrations();
  }, []);

  const loadEvents = async () => {
    const response = await fetchEvents();
    setEvents(response);
  };

  const loadRegistrations = async () => {
    const response = await getRegistrations();
    setRegistrations(response);
  };

  const handleAddRegistration = async (registrationData) => {
    await registerForEvent({ ...registrationData});
    loadRegistrations();
    setSelectedEvent(null);
    setSelectedRegistration(null);
  };

  const handleUpdateRegistration = async (registrationData) => {
    await updateRegistration(selectedRegistration.id, registrationData);
    loadRegistrations();
    setSelectedRegistration(null);
  };

  const handleCancelRegistration = async (registrationId) => {
    await cancelRegistration(registrationId);
    setSelectedEvent(null);
    loadRegistrations();
  };

  const handleCancelEdit = () => {
    setSelectedRegistration(null); // Clear selected registration and reset form
  };

  return (
    <div>
      <Header />

      <h1>Register for an Event</h1>

      {events.length === 0 ? (
        <div style={{ display: 'flex', justifyContent: 'center', alignItems: 'center', height: '200px' }}>
          <p>No events available at the moment.</p>
        </div>
      ) : (
        <EventList
          events={events}
          readOnly={true} // Pass readOnly prop to remove Edit/Delete buttons
        />
      )}

      <h2>Registrations</h2>
      <ul>
        {registrations.map((registration) => (
          <li key={registration.id}>
            <strong>Event ID:</strong> {registration.eventId} - <strong>Attendee:</strong> {registration.attendeeName}
            <button onClick={() => setSelectedRegistration(registration)}>Edit</button>
            <button onClick={() => handleCancelRegistration(registration.id)}>Cancel</button>
          </li>
        ))}
      </ul>

      <h2>{selectedRegistration ? 'Edit Registration' : 'Add Registration'}</h2>
      <RegistrationForm
        registrationData={selectedRegistration}
        onSubmit={selectedRegistration ? handleUpdateRegistration : handleAddRegistration}
        onCancel={handleCancelEdit}
      />
    </div>
  );
};

export default RegistrationPage;
