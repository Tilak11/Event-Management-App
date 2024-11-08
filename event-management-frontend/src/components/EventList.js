// src/components/EventList.js
import React from 'react';

const EventList = ({ events, onEdit, onDelete, readOnly = false }) => (
  <div style={styles.container}>
    {events && events.length > 0 ? (
      <table style={styles.table}>
        <thead>
          <tr>
            <th style={styles.header}>Event ID</th>
            <th style={styles.header}>Title</th>
            <th style={styles.header}>Date</th>
            <th style={styles.header}>Location</th>
            <th style={styles.header}>Capacity</th>
            <th style={styles.header}>Amount Invested</th>
            {!readOnly && <th style={styles.header}>Actions</th>}
          </tr>
        </thead>
        <tbody>
          {events.map((event) => (
            <tr key={event.id} style={styles.row}>
              <td style={styles.cell}>{event.id}</td>
              <td style={styles.cell}>{event.title || "N/A"}</td>
              <td style={styles.cell}>{event.date || "N/A"}</td>
              <td style={styles.cell}>{event.location || "N/A"}</td>
              <td style={styles.cell}>{event.capacity || "N/A"}</td>
              <td style={styles.cell}>${event.amountInvested || "N/A"}</td>
              {!readOnly && (
                <td style={styles.cell}>
                  <button onClick={() => onEdit(event)} style={styles.button}>Edit</button>
                  <button onClick={() => onDelete(event.id)} style={styles.button}>Delete</button>
                </td>
              )}
            </tr>
          ))}
        </tbody>
      </table>
    ) : (
      <p>No events available</p>
    )}
  </div>
);

const styles = {
  container: {
    display: 'flex',
    justifyContent: 'center',
    marginTop: '20px',
  },
  table: {
    width: '80%', // Set the table width to 80% of its container
    maxWidth: '800px', // Limit the max width of the table
    backgroundColor: '#f9f9f9',
    borderCollapse: 'collapse',
  },
  header: {
    backgroundColor: '#f2f2f2',
    padding: '10px',
    textAlign: 'left',
    borderBottom: '2px solid #ddd',
  },
  row: {
    borderBottom: '1px solid #ddd',
  },
  cell: {
    padding: '10px',
    textAlign: 'left',
  },
  button: {
    marginRight: '5px',
    padding: '5px 10px',
    cursor: 'pointer',
  },
};

export default EventList;
