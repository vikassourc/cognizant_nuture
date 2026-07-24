import React from 'react';
import officeImg from './office.jpg';
import './App.css';

// List of office space objects
const officeList = [
  { id: 1, name: 'DBS',        rent: 50000,  address: 'Chennai' },
  { id: 2, name: 'Tech Park',  rent: 75000,  address: 'Bangalore' },
  { id: 3, name: 'Regus',      rent: 45000,  address: 'Mumbai' },
  { id: 4, name: 'WeWork',     rent: 90000,  address: 'Hyderabad' },
  { id: 5, name: 'Smartworks', rent: 55000,  address: 'Pune' },
];

function App() {
  // Single office object
  const office = officeList[0];

  return (
    <div className="App">
      {/* JSX Element: Heading */}
      <h1>Office Space , at Affordable Range</h1>

      {/* JSX Attribute: Image of the office space */}
      <img src={officeImg} alt="Office Space" className="office-img" />

      {/* Single office object details */}
      <p><strong>Name: {office.name}</strong></p>
      <p style={{ color: office.rent < 60000 ? 'red' : 'green' }}>
        <strong>Rent: Rs. {office.rent}</strong>
      </p>
      <p><strong>Address: {office.address}</strong></p>

      <hr />

      {/* Loop through list of office objects */}
      <h2>List of Available Office Spaces</h2>
      <ul className="office-list">
        {officeList.map((item) => (
          <li key={item.id} className="office-item">
            <p><strong>Name: {item.name}</strong></p>
            {/* CSS: Red if rent < 60000, Green if rent > 60000 */}
            <p style={{ color: item.rent < 60000 ? 'red' : 'green' }}>
              <strong>Rent: Rs. {item.rent}</strong>
            </p>
            <p><strong>Address: {item.address}</strong></p>
          </li>
        ))}
      </ul>
    </div>
  );
}

export default App;
