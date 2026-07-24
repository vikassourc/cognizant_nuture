import React from 'react';
import CohortDetails from './CohortDetails';
import './App.css';

// Sample cohort data matching the reference image
const cohorts = [
  {
    cohortId: 'INTADMDF10',
    cohortName: '.NET FSD',
    startDate: '22-Feb-2022',
    status: 'Scheduled',
    coach: 'Aathma',
    trainer: 'Jojo Jose',
  },
  {
    cohortId: 'ADM21JF014',
    cohortName: 'Java FSD',
    startDate: '10-Sep-2021',
    status: 'Ongoing',
    coach: 'Apoorv',
    trainer: 'Elisa Smith',
  },
  {
    cohortId: 'CDBJF21025',
    cohortName: 'Java FSD',
    startDate: '24-Dec-2021',
    status: 'Ongoing',
    coach: 'Aathma',
    trainer: 'John Doe',
  },
];

function App() {
  return (
    <div className="App">
      <h2>Cohorts Details</h2>
      <div className="cohorts-wrapper">
        {cohorts.map((cohort) => (
          <CohortDetails
            key={cohort.cohortId}
            cohortId={cohort.cohortId}
            cohortName={cohort.cohortName}
            startDate={cohort.startDate}
            status={cohort.status}
            coach={cohort.coach}
            trainer={cohort.trainer}
          />
        ))}
      </div>
    </div>
  );
}

export default App;
