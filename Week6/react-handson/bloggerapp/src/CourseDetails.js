import React, { Component } from 'react';

// CONDITIONAL RENDERING METHOD 1: if/else statement
// CONDITIONAL RENDERING METHOD 5: Element Variable (store JSX in a variable)

class CourseDetails extends Component {
  render() {
    const courses = [
      { id: 1, name: 'Angular', date: '4/5/2021',  active: true  },
      { id: 2, name: 'React',   date: '6/3/2020',  active: true  },
      { id: 3, name: 'Vue',     date: '1/1/2022',  active: false },
    ];

    const showAll = this.props.showAll || false;

    // METHOD 5: Element Variable — store JSX in a variable
    let heading;
    if (showAll) {
      heading = <h2>All Course Details</h2>;
    } else {
      heading = <h2>Course Details</h2>;
    }

    return (
      <div className="column">
        {/* Element variable rendered here */}
        {heading}

        {courses.map((course) => {
          // METHOD 1: if/else — only render active courses unless showAll
          if (!showAll && !course.active) {
            return null;
          } else {
            return (
              <div key={course.id} className="item">
                <h3>{course.name}</h3>
                <p>{course.date}</p>
              </div>
            );
          }
        })}
      </div>
    );
  }
}

export default CourseDetails;
