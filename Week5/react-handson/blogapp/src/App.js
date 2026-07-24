import React from 'react';
import Posts from './Posts';
import './App.css';

function App() {
  return (
    <div className="App">
      <header className="app-header">
        <h1>📰 BlogApp</h1>
        <p>React Component Lifecycle — Week 5 Lab</p>
      </header>
      <main className="app-main">
        <Posts />
      </main>
    </div>
  );
}

export default App;
