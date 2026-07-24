import React, { Component } from 'react';
// Step 8: Import the CSS Module
import styles from './CohortDetails.module.css';

class CohortDetails extends Component {
  render() {
    const { cohortId, cohortName, startDate, status, coach, trainer } = this.props;

    // Step 10: Green color if status is "Ongoing", blue in all other cases
    const headingColor = status === 'Ongoing' ? 'green' : 'blue';

    return (
      // Step 9: Apply the box class to the container div
      <div className={styles.box}>
        <h3 style={{ color: headingColor }}>
          {cohortId} -{cohortName}
        </h3>
        <dl>
          <dt>Started On</dt>
          <dd>{startDate}</dd>

          <dt>Current Status</dt>
          <dd>{status}</dd>

          <dt>Coach</dt>
          <dd>{coach}</dd>

          <dt>Trainer</dt>
          <dd>{trainer}</dd>
        </dl>
      </div>
    );
  }
}

export default CohortDetails;
