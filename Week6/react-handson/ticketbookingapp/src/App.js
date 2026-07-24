import React, { Component } from 'react';
import GuestPage from './GuestPage';
import UserPage from './UserPage';
import './App.css';

class App extends Component {
  constructor(props) {
    super(props);
    // State to track login status
    this.state = { isLoggedIn: false };
  }

  // Login handler - set isLoggedIn to true → show UserPage
  handleLogin = () => {
    this.setState({ isLoggedIn: true });
  };

  // Logout handler - set isLoggedIn to false → show GuestPage
  handleLogout = () => {
    this.setState({ isLoggedIn: false });
  };

  render() {
    const { isLoggedIn } = this.state;

    // Simple if/else: show UserPage if logged in, GuestPage if not
    let page;
    if (isLoggedIn) {
      page = <UserPage onLogout={this.handleLogout} />;
    } else {
      page = <GuestPage onLogin={this.handleLogin} />;
    }

    return (
      <div className="App">
        <h1>✈️ Ticket Booking App</h1>
        <hr />
        {page}
      </div>
    );
  }
}

export default App;
