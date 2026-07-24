import React, { Component } from 'react';

class ListofPlayers extends Component {
  render() {
    // ES6: Declare array of 11 players with name and score
    const players = [
      { name: 'Mr. Jack', score: 50 },
      { name: 'Mr. Michael', score: 70 },
      { name: 'Mr. John', score: 40 },
      { name: 'Mr. Ann', score: 61 },
      { name: 'Mr. Elisabeth', score: 61 },
      { name: 'Mr. Sachin', score: 95 },
      { name: 'Mr. Dhoni', score: 100 },
      { name: 'Mr. Virat', score: 84 },
      { name: 'Mr. Jadeja', score: 64 },
      { name: 'Mr. Raina', score: 75 },
      { name: 'Mr. Rohit', score: 80 },
    ];

    // ES6 Arrow Function: Filter players with scores <= 70
    const lowScorers = players.filter((player) => player.score <= 70);

    return (
      <div>
        {/* ES6 map: Display all 11 players */}
        <h2>List of Players</h2>
        <ul>
          {players.map((player, index) => (
            <li key={index}>
              {player.name} {player.score}
            </li>
          ))}
        </ul>

        <hr />

        {/* ES6 Arrow Function Filter: Players with score <= 70 */}
        <h2>List of Players having Scores Less than 70</h2>
        <ul>
          {lowScorers.map((player, index) => (
            <li key={index}>
              {player.name} {player.score}
            </li>
          ))}
        </ul>
      </div>
    );
  }
}

export default ListofPlayers;
