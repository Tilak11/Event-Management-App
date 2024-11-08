// src/services/eventService.js
import axios from 'axios';

let authToken = null; // Global variable to store the token

// Initialize axios instance
const eventApi = axios.create({
  baseURL: 'http://localhost:8081/api',  // Ensure correct backend port (8081)
});

// Function to fetch the auth token with a retry mechanism
const fetchAuthToken = async (retries = 5, delay = 1000) => {
  try {
    const response = await axios.post('http://localhost:8081/api/auth', {
      username: "user",
      password: "mypass",
    });
    authToken = response.data; // Store the token
    eventApi.defaults.headers.common['Authorization'] = `Bearer ${authToken}`; // Set token in headers
  } catch (error) {
    if (retries > 0) {
      console.warn(`Auth token fetch failed. Retrying in ${delay / 1000} seconds...`);
      await new Promise(res => setTimeout(res, delay));
      return fetchAuthToken(retries - 1, delay);  // Retry fetching the token
    } else {
      console.error("Error fetching auth token after retries:", error);
      throw error;
    }
  }
};

// Immediately fetch auth token when this module is loaded
fetchAuthToken();
export { authToken };


// Fetch all events
export const fetchEvents = async () => {
  try {
    const response = await eventApi.get('/events');
    return response.data;
  } catch (error) {
    console.error("Error fetching events:", error);
    throw error;
  }
};

// Create a new event
export const createEvent = async (eventData) => {
  try {
    const response = await eventApi.post('/events', eventData);
    return response.data;
  } catch (error) {
    console.error("Error creating event:", error);
    throw error;
  }
};

// Get event by ID
export const getEventById = async (id) => {
  try {
    const response = await eventApi.get(`/events/${id}`);
    return response.data;
  } catch (error) {
    console.error(`Error fetching event with ID ${id}:`, error);
    throw error;
  }
};

// Update event
export const updateEvent = async (id, eventData) => {
  try {
    const response = await eventApi.put(`/events/${id}`, eventData);
    return response.data;
  } catch (error) {
    console.error(`Error updating event with ID ${id}:`, error);
    throw error;
  }
};

// Delete event
export const deleteEvent = async (id) => {
  try {
    await eventApi.delete(`/events/${id}`);
  } catch (error) {
    console.error(`Error deleting event with ID ${id}:`, error);
    throw error;
  }
};
