import React from 'react';

// GuestPage: shown when user is NOT logged in
// Displays flight details and Login button
function GuestPage({ onLogin }) {
  const flights = [
    { id: 1, from: 'Chennai',   to: 'Delhi',     time: '06:00 AM', price: '₹4,500' },
    { id: 2, from: 'Mumbai',    to: 'Bangalore',  time: '09:30 AM', price: '₹3,200' },
    { id: 3, from: 'Hyderabad', to: 'Kolkata',    time: '01:15 PM', price: '₹5,800' },
    { id: 4, from: 'Delhi',     to: 'Chennai',    time: '04:45 PM', price: '₹4,100' },
  ];

  return (
    <div>
      <h2>Please sign up.</h2>
      <button onClick={onLogin}>Login</button>

      <hr />

      <h3>Available Flights</h3>
      <table border="1" cellPadding="8" cellSpacing="0">
        <thead>
          <tr>
            <th>#</th>
            <th>From</th>
            <th>To</th>
            <th>Departure</th>
            <th>Price</th>
          </tr>
        </thead>
        <tbody>
          {flights.map((flight) => (
            <tr key={flight.id}>
              <td>{flight.id}</td>
              <td>{flight.from}</td>
              <td>{flight.to}</td>
              <td>{flight.time}</td>
              <td>{flight.price}</td>
            </tr>
          ))}
        </tbody>
      </table>
      <p><em>Please login to book a ticket.</em></p>
    </div>
  );
}

export default GuestPage;
