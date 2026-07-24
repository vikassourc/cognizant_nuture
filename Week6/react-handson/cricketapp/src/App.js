import React from 'react';
import ListofPlayers from './ListofPlayers';
import IndianPlayers from './IndianPlayers';
import './App.css';

function App() {
  // Flag variable: change to false to display IndianPlayers component
  // flag = true  → shows ListofPlayers
  // flag = false → shows IndianPlayers
  const flag = true;

  // Simple if/else to conditionally render components
  let component;
  if (flag) {
    component = <ListofPlayers />;
  } else {
    component = <IndianPlayers />;
  }

  return (
    <div className="App">
      {component}
    </div>
  );
}

export default App;
