import React from 'react';
import CalculateScore from './Components/CalculateScore';

function App() {
  return (
    <div>
      <CalculateScore Name="Vikas" School="Cognizant Academy" Total={430} goal={500} />
    </div>
  );
}

export default App;
