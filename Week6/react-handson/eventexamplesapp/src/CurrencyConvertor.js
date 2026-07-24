import React, { Component } from 'react';

class CurrencyConvertor extends Component {
  constructor(props) {
    super(props);
    this.state = {
      amount: '',
      currency: '',
    };
  }

  // Handle form input changes
  handleChange = (e) => {
    this.setState({ [e.target.name]: e.target.value });
  };

  // Handle Submit: Convert INR to Euro (1 Euro = 80 INR)
  handleSubmit = (e) => {
    e.preventDefault();
    const { amount, currency } = this.state;
    const convertedAmount = amount * 80;
    alert(`Converting to: ${currency} Amount is ${convertedAmount}`);
  };

  render() {
    return (
      <div>
        <h2 style={{ color: 'green' }}>Currency Convertor!!!</h2>
        <form onSubmit={this.handleSubmit}>
          <table>
            <tbody>
              <tr>
                <td><label>Amount:</label></td>
                <td>
                  <input
                    type="text"
                    name="amount"
                    value={this.state.amount}
                    onChange={this.handleChange}
                  />
                </td>
              </tr>
              <tr>
                <td><label>Currency:</label></td>
                <td>
                  <input
                    type="text"
                    name="currency"
                    value={this.state.currency}
                    onChange={this.handleChange}
                  />
                </td>
              </tr>
              <tr>
                <td></td>
                <td><button type="submit">Submit</button></td>
              </tr>
            </tbody>
          </table>
        </form>
      </div>
    );
  }
}

export default CurrencyConvertor;
