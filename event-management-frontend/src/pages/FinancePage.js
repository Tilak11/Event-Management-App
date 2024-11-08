// src/pages/FinancePage.js
import React, { useEffect, useState } from 'react';
import Header from '../Header';
import { useNavigate } from 'react-router-dom';
import EventList from '../components/EventList';
import InvestmentForm from '../components/InvestmentForm';
import { fetchEvents } from '../services/eventService';
import { getInvestments, investInEvent, updateInvestment, deleteInvestment } from '../services/financeService';

const FinancePage = () => {
  const [events, setEvents] = useState([]);
  const [investments, setInvestments] = useState([]);
  const [selectedInvestment, setSelectedInvestment] = useState(null);
  const [selectedEvent, setSelectedEvent] = useState(null);
  const navigate = useNavigate();

  useEffect(() => {
    loadEvents();
    loadInvestments();
  }, []);

  const loadEvents = async () => {
    const response = await fetchEvents();
    setEvents(response);
  };

  const loadInvestments = async () => {
    const response = await getInvestments();
    setInvestments(response);
  };

  const handleAddInvestment = async (investmentData) => {
    await investInEvent(investmentData); // investmentData includes eventId from form
    loadInvestments();
    setSelectedInvestment(null);
    setSelectedEvent(null);
  };

  const handleUpdateInvestment = async (investmentData) => {
    await updateInvestment(selectedInvestment.id, investmentData);
    loadInvestments();
    setSelectedInvestment(null);
  };

  const handleDeleteInvestment = async (investmentId) => {
    await deleteInvestment(investmentId);
    setSelectedEvent(null);
    loadInvestments();
  };

  const handleCancelEdit = () => {
    setSelectedInvestment(null); // Clear selected investment and reset form
  };

  return (
    <div>
      <Header />
      <h1>Finance Page - Invest in Events</h1>
      

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

      <h2>Investments</h2>
      <ul>
        {investments.map((investment) => (
          <li key={investment.id}>
            <strong>Event ID:</strong> {investment.eventId} - <strong>Investor:</strong> {investment.investorName}
            <button onClick={() => setSelectedInvestment(investment)}>Edit</button>
            {/* <button onClick={() => handleDeleteInvestment(investment.id)}>Delete</button> */}
          </li>
        ))}
      </ul>

      <h2>{selectedInvestment ? 'Edit Investment' : 'Add Investment'}</h2>
      <InvestmentForm
        eventId={selectedEvent ? selectedEvent.id : null} // Pass selected eventId if available
        investmentData={selectedInvestment}
        onSubmit={selectedInvestment ? handleUpdateInvestment : handleAddInvestment}
        onCancel={handleCancelEdit}
      />
    </div>
  );
};

export default FinancePage;

