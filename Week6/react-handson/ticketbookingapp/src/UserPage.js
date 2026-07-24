import React, { Component } from 'react';

// UserPage: shown when user IS logged in
// Allows logged-in users to book tickets
class UserPage extends Component {
  constructor(props) {
    super(props);
    this.state = {
      from: '',
      to: '',
      date: '',
      booked: false,
    };
  }

  handleChange = (e) => {
    this.setState({ [e.target.name]: e.target.value });
  };

  handleBook = (e) => {
    e.preventDefault();
    const { from, to, date } = this.state;
    if (from && to && date) {
      this.setState({ booked: true });
    } else {
      alert('Please fill all fields to book a ticket.');
    }
  };

  render() {
    const { onLogout } = this.props;
    const { from, to, date, booked } = this.state;

    return (
      <div>
        <h2>Welcome back</h2>
        <button onClick={onLogout}>Logout</button>

        <hr />

        <h3>Book Your Ticket</h3>
        {booked ? (
          <div>
            <p><strong>✅ Ticket Booked Successfully!</strong></p>
            <p>From: {from} → To: {to} | Date: {date}</p>
            <button onClick={() => this.setState({ booked: false, from: '', to: '', date: '' })}>
              Book Another
            </button>
          </div>
        ) : (
          <form onSubmit={this.handleBook}>
            <table>
              <tbody>
                <tr>
                  <td><label>From:</label></td>
                  <td>
                    <input
                      type="text"
                      name="from"
                      value={from}
                      placeholder="Origin city"
                      onChange={this.handleChange}
                    />
                  </td>
                </tr>
                <tr>
                  <td><label>To:</label></td>
                  <td>
                    <input
                      type="text"
                      name="to"
                      value={to}
                      placeholder="Destination city"
                      onChange={this.handleChange}
                    />
                  </td>
                </tr>
                <tr>
                  <td><label>Date:</label></td>
                  <td>
                    <input
                      type="date"
                      name="date"
                      value={date}
                      onChange={this.handleChange}
                    />
                  </td>
                </tr>
                <tr>
                  <td></td>
                  <td><button type="submit">Book Ticket</button></td>
                </tr>
              </tbody>
            </table>
          </form>
        )}
      </div>
    );
  }
}

export default UserPage;
