import React, { Component } from 'react';
import Home from './Components/Home';
import About from './Components/About';
import Contact from './Components/Contact';

class App extends Component {
  render() {
    return (
      <div>
        <Home />
        <About />
        <Contact />
      </div>
    );
  }
}

export default App;
