// src/components/InvestmentForm.js
import React, { useState, useEffect } from 'react';

const InvestmentForm = ({ eventId, investmentData, onSubmit, onCancel }) => {
  const [formData, setFormData] = useState({
    eventId: eventId || '', // Pre-fill with eventId if available
    investorName: '',
    amount: '',
  });

  // Populate form fields if investmentData is provided (for editing)
  useEffect(() => {
    if (investmentData) {
      setFormData({
        eventId: investmentData.eventId,
        investorName: investmentData.investorName,
        amount: investmentData.amount,
      });
    } else if (eventId) {
      // Set eventId if passed from FinancePage (for new investment)
      setFormData((prevData) => ({ ...prevData, eventId }));
    }
  }, [investmentData, eventId]);

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleSubmit = (e) => {
    e.preventDefault();

    // Create the investment payload
    const investmentPayload = {
      ...formData,
      eventId: formData.eventId, // Ensure eventId is included
    };

    // Call the onSubmit function with the investment data
    onSubmit(investmentPayload);

    // Reset form fields after submission
    setFormData({ eventId: eventId || '', investorName: '', amount: '' });
  };

  return (
    <form onSubmit={handleSubmit} className="form-container">
      <input
        name="eventId"
        value={formData.eventId}
        onChange={handleChange}
        placeholder="Event ID"
        disabled={Boolean(eventId)} // Disable if eventId is passed from parent
      />
      <input
        name="investorName"
        value={formData.investorName}
        onChange={handleChange}
        placeholder="Investor Name"
      />
      <input
        name="amount"
        type="number"
        value={formData.amount}
        onChange={handleChange}
        placeholder="Investment Amount"
      />
      <button type="submit">
        {investmentData ? "Update Investment" : "Add Investment"}
      </button>
      {investmentData && <button type="button" onClick={onCancel}>Cancel</button>}
    </form>
  );
};

export default InvestmentForm;
