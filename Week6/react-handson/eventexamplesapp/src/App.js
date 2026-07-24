import React, { Component } from 'react';
import CurrencyConvertor from './CurrencyConvertor';
import './App.css';

class App extends Component {
  constructor(props) {
    super(props);
    this.state = { count: 1 };
  }

  // Method 1: Increment the counter value
  increment = () => {
    this.setState({ count: this.state.count + 1 });
  };

  // Method 2: Say Hello with a static message
  sayHello = () => {
    alert('Hello: Member!');
  };

  // Increment button invokes MULTIPLE methods
  handleIncrement = () => {
    this.increment();   // Method 1
    this.sayHello();    // Method 2
  };

  // Decrement the counter value
  decrement = () => {
    this.setState({ count: this.state.count - 1 });
  };

  // Say Welcome - function that takes an argument
  sayWelcome = (msg) => {
    alert(msg);
  };

  // Synthetic event "OnPress" - displays "I was clicked"
  handleClick = (e) => {
    alert(e.type + ': I was clicked');
  };

  render() {
    return (
      <div className="App">
        {/* Counter display */}
        <p>{this.state.count}</p>

        {/* Increment button: invokes multiple methods */}
        <button onClick={this.handleIncrement}>Increment</button><br />

        {/* Decrement button */}
        <button onClick={this.decrement}>Decrement</button><br />

        {/* Say Welcome button: passes "welcome" as argument */}
        <button onClick={() => this.sayWelcome('welcome')}>Say welcome</button><br />

        {/* Click on me: synthetic OnPress event */}
        <button onClick={this.handleClick}>Click on me</button>

        <br /><br />

        {/* CurrencyConvertor component */}
        <CurrencyConvertor />
      </div>
    );
  }
}

export default App;
