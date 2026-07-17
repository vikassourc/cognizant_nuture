import React from 'react';
import '../Stylesheets/mystyle.css';

function CalculateScore(props) {
  const average = (props.Total / props.goal) * 100;

  return (
    <div className="container">
      <h1 className="heading">Student Score Calculator</h1>
      <table className="table">
        <tbody>
          <tr>
            <td className="label">Name</td>
            <td className="value">{props.Name}</td>
          </tr>
          <tr>
            <td className="label">School</td>
            <td className="value">{props.School}</td>
          </tr>
          <tr>
            <td className="label">Total Score</td>
            <td className="value">{props.Total}</td>
          </tr>
          <tr>
            <td className="label">Goal</td>
            <td className="value">{props.goal}</td>
          </tr>
          <tr>
            <td className="label">Average Score</td>
            <td className="value">{average.toFixed(2)}%</td>
          </tr>
        </tbody>
      </table>
    </div>
  );
}

export default CalculateScore;
