import React, { Component } from 'react';
import CourseDetails from './CourseDetails';
import BookDetails from './BookDetails';
import BlogDetails from './BlogDetails';
import './App.css';

// CONDITIONAL RENDERING METHOD 4: switch statement (tab navigation)

class App extends Component {
  constructor(props) {
    super(props);
    this.state = {
      activeTab: 'all',       // 'all' | 'courses' | 'books' | 'blogs'
      showAll: false,
      showOutOfStock: false,
      showDrafts: false,
    };
  }

  // METHOD 4: switch — render content based on active tab
  renderContent() {
    switch (this.state.activeTab) {
      case 'courses':
        return <CourseDetails showAll={this.state.showAll} />;
      case 'books':
        return <BookDetails showOutOfStock={this.state.showOutOfStock} />;
      case 'blogs':
        return <BlogDetails showDrafts={this.state.showDrafts} />;
      case 'all':
      default:
        return (
          <div className="columns-wrapper">
            <CourseDetails showAll={this.state.showAll} />
            <div className="divider" />
            <BookDetails showOutOfStock={this.state.showOutOfStock} />
            <div className="divider" />
            <BlogDetails showDrafts={this.state.showDrafts} />
          </div>
        );
    }
  }

  render() {
    const { activeTab, showAll, showOutOfStock, showDrafts } = this.state;

    return (
      <div className="App">
        <h1>📝 Blogger App</h1>
        <p className="subtitle">Conditional Rendering — 6 Methods Demonstrated</p>

        {/* Tab navigation — switch (Method 4) */}
        <div className="tabs">
          {['all', 'courses', 'books', 'blogs'].map((tab) => (
            <button
              key={tab}
              className={activeTab === tab ? 'tab active' : 'tab'}
              onClick={() => this.setState({ activeTab: tab })}
            >
              {tab.charAt(0).toUpperCase() + tab.slice(1)}
            </button>
          ))}
        </div>

        {/* Toggle options */}
        <div className="toggles">
          <label>
            <input
              type="checkbox"
              checked={showAll}
              onChange={() => this.setState({ showAll: !showAll })}
            />
            &nbsp;Show All Courses (if/else)
          </label>
          <label>
            <input
              type="checkbox"
              checked={showOutOfStock}
              onChange={() => this.setState({ showOutOfStock: !showOutOfStock })}
            />
            &nbsp;Show Out-of-Stock Books (&amp;&amp;)
          </label>
          <label>
            <input
              type="checkbox"
              checked={showDrafts}
              onChange={() => this.setState({ showDrafts: !showDrafts })}
            />
            &nbsp;Show Draft Blogs (ternary)
          </label>
        </div>

        <hr />

        {/* Rendered content via switch statement */}
        {this.renderContent()}
      </div>
    );
  }
}

export default App;
