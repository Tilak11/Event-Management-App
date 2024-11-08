// src/services/financeService.js
import axios from 'axios';
import config from '../config';

const financeApi = axios.create({
  baseURL: config.FINANCE_BASE_URL,
});


// Make an investment
export const investInEvent = async (investmentData) => {
  try {
    const response = await financeApi.post('/finance', investmentData);
    return response.data;
  } catch (error) {
    console.error("Error making investment:", error);
    throw error;
  }
};

// Fetch all investments
export const getInvestments = async () => {
  try {
    const response = await financeApi.get('/finance');
    return response.data;
  } catch (error) {
    console.error("Error fetching investments:", error);
    throw error;
  }
};

// Fetch investment by ID
export const getInvestmentById = async (id) => {
  try {
    const response = await financeApi.get(`/finance/${id}`);
    return response.data;
  } catch (error) {
    console.error(`Error fetching investment with ID ${id}:`, error);
    throw error;
  }
};

// Update an investment
export const updateInvestment = async (id, investmentData) => {
  try {
    const response = await financeApi.put(`/finance/${id}`, investmentData);
    return response.data;
  } catch (error) {
    console.error(`Error updating investment with ID ${id}:`, error);
    throw error;
  }
};

// Delete an investment
export const deleteInvestment = async (id) => {
  try {
    await financeApi.delete(`/finance/${id}`);
  } catch (error) {
    console.error(`Error deleting investment with ID ${id}:`, error);
    throw error;
  }
};
