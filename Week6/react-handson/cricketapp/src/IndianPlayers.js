import React, { Component } from 'react';

class IndianPlayers extends Component {
  render() {
    // ES6 Destructuring: Extract individual players from array
    const players = ['Sachin1', 'Dhoni2', 'Virat3', 'Rohit4', 'Yuvaraj5', 'Raina6'];
    const [first, second, third, fourth, fifth, sixth] = players;

    // ES6 Spread / Merge: Merge two arrays into one
    const T20players = ['Mr. First Player', 'Mr. Second Player', 'Mr. Third Player'];
    const RanjiTrophyPlayers = ['Mr. Fourth Player', 'Mr. Fifth Player', 'Mr. Sixth Player'];
    const mergedPlayers = [...T20players, ...RanjiTrophyPlayers];

    return (
      <div>
        {/* ES6 Destructuring - Odd position players (1st, 3rd, 5th) */}
        <h2>Odd Players</h2>
        <ul>
          <li>First : {first}</li>
          <li>Third : {third}</li>
          <li>Fifth : {fifth}</li>
        </ul>

        <hr />

        {/* ES6 Destructuring - Even position players (2nd, 4th, 6th) */}
        <h2>Even Players</h2>
        <ul>
          <li>Second : {second}</li>
          <li>Fourth : {fourth}</li>
          <li>Sixth : {sixth}</li>
        </ul>

        <hr />

        {/* ES6 Spread/Merge - Merged T20 + RanjiTrophy players */}
        <h2>List of Indian Players Merged:</h2>
        <ul>
          {mergedPlayers.map((player, index) => (
            <li key={index}>{player}</li>
          ))}
        </ul>
      </div>
    );
  }
}

export default IndianPlayers;
