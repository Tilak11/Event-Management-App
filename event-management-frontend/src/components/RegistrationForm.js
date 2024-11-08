// src/components/RegistrationForm.js
import React, { useState, useEffect } from 'react';

const RegistrationForm = ({ registrationData, onSubmit, onCancel }) => {
  const [formData, setFormData] = useState({
    eventId: '',
    attendeeName: '',
    attendeeEmail: '',
  });

  useEffect(() => {
    if (registrationData) {
      setFormData(registrationData);
    }
  }, [registrationData]);

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    onSubmit(formData);
    setFormData({ eventId: '', attendeeName: '', attendeeEmail: '' });
  };

  return (
    <form onSubmit={handleSubmit} className="form-container">
      <input name="eventId" value={formData.eventId} onChange={handleChange} placeholder="Event ID" />
      <input name="attendeeName" value={formData.attendeeName} onChange={handleChange} placeholder="Attendee Name" />
      <input name="attendeeEmail" value={formData.attendeeEmail} onChange={handleChange} placeholder="Attendee Email" />
      <button type="submit">{registrationData ? "Update Registration" : "Register"}</button>
      {registrationData && <button type="button" onClick={onCancel}>Cancel</button>}
    </form>
  );
};

export default RegistrationForm;
