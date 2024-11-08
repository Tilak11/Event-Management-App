// src/components/Header.js
import React from 'react';
import { useNavigate } from 'react-router-dom';

const Header = () => {
  const navigate = useNavigate();

  return (
    <header style={styles.header}>
      <nav style={styles.nav}>
        <button style={styles.navButton} onClick={() => navigate('/')}>Event Page</button>
        <button style={styles.navButton} onClick={() => navigate('/register')}>Registration Page</button>
        <button style={styles.navButton} onClick={() => navigate('/finance')}>Finance Page</button>
      </nav>
    </header>
  );
};

const styles = {
  header: {
    backgroundColor: '#333667',
    padding: '10px 20px',
    color: 'white',
  },
  nav: {
    display: 'flex',
    justifyContent: 'center',
  },
  navButton: {
    backgroundColor: 'transparent',
    border: 'none',
    color: 'white',
    fontSize: '16px',
    cursor: 'pointer',
    padding: '10px 20px',
    textDecoration: 'none',
  },
  navButtonHover: {
    textDecoration: 'underline',
  },
};

export default Header;
