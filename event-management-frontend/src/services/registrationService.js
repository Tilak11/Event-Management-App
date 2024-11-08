// src/services/registrationService.js
import axios from 'axios';
import config from '../config';

const registrationApi = axios.create({
  baseURL: config.REGISTRATION_BASE_URL,
});

// Register for an event
export const registerForEvent = async (registrationData) => {
  try {
    const response = await registrationApi.post('/registrations', registrationData);
    return response.data;
  } catch (error) {
    console.error("Error registering for event:", error);
    throw error;
  }
};

// Fetch all registrations
export const getRegistrations = async () => {
  try {
    const response = await registrationApi.get('/registrations');
    return response.data;
  } catch (error) {
    console.error("Error fetching registrations:", error);
    throw error;
  }
};

// Fetch registration by ID
export const getRegistrationById = async (id) => {
  try {
    const response = await registrationApi.get(`/registrations/${id}`);
    return response.data;
  } catch (error) {
    console.error(`Error fetching registration with ID ${id}:`, error);
    throw error;
  }
};

// Update a registration
export const updateRegistration = async (id, registrationData) => {
  try {
    const response = await registrationApi.put(`/registrations/${id}`, registrationData);
    return response.data;
  } catch (error) {
    console.error(`Error updating registration with ID ${id}:`, error);
    throw error;
  }
};

// Cancel a registration
export const cancelRegistration = async (id) => {
  try {
    await registrationApi.delete(`/registrations/${id}`);
  } catch (error) {
    console.error(`Error canceling registration with ID ${id}:`, error);
    throw error;
  }
};
