// src/components/EventForm.js
import React, { useState, useEffect } from 'react';

const EventForm = ({ eventData, onAddEvent, onCancel }) => {
  const [formData, setFormData] = useState({
    title: '',
    description: '',
    location: '',
    date: '',
    capacity: '',
  });

  useEffect(() => {
    if (eventData) {
      // Populate form with selected event data for editing
      setFormData(eventData);
    } else {
      // Clear form if no event is selected
      setFormData({
        title: '',
        description: '',
        location: '',
        date: '',
        capacity: '',
      });
    }
  }, [eventData]);

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    onAddEvent(formData); // Call add or update function based on parent component
    setFormData({ title: '', description: '', location: '', date: '', capacity: '' });
  };

  return (
    <form onSubmit={handleSubmit} className="form-container">
      <input
        name="title"
        value={formData.title}
        onChange={handleChange}
        placeholder="Title"
      />
      <input
        name="description"
        value={formData.description}
        onChange={handleChange}
        placeholder="Description"
      />
      <input
        name="location"
        value={formData.location}
        onChange={handleChange}
        placeholder="Location"
      />
      <input
        name="date"
        value={formData.date}
        onChange={handleChange}
        type="datetime-local"
      />
      <input
        name="capacity"
        value={formData.capacity}
        onChange={handleChange}
        type="number"
        placeholder="Capacity"
      />
      <button type="submit">{eventData ? 'Update Event' : 'Create Event'}</button>
      {eventData && <button type="button" onClick={onCancel}>Cancel</button>}
    </form>
  );
};

export default EventForm;
