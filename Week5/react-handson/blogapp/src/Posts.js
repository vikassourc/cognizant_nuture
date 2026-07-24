import React, { Component } from 'react';
import Post from './Post';

// Posts class-based component demonstrating React Lifecycle hooks:
// 1. componentDidMount()  - fetches posts from API after component mounts
// 2. componentDidCatch()  - error boundary to catch errors in child components

class Posts extends Component {
  // Step 1: Initialize state in constructor
  constructor(props) {
    super(props);
    this.state = {
      posts: [],          // Array to store fetched blog posts
      loading: true,      // Loading indicator flag
      error: null,        // Stores any error message
      hasError: false,    // Flag for error boundary
    };
    console.log('Constructor: Component is being initialized');
  }

  // Step 2: componentDidMount - called after component is rendered to the DOM
  // Ideal place to make API calls / fetch data
  componentDidMount() {
    console.log('componentDidMount: Component has mounted. Fetching posts...');

    fetch('https://jsonplaceholder.typicode.com/posts?_limit=10')
      .then((response) => {
        if (!response.ok) {
          throw new Error(`HTTP error! status: ${response.status}`);
        }
        return response.json();
      })
      .then((data) => {
        console.log('Data fetched successfully:', data.length, 'posts loaded');
        this.setState({
          posts: data,
          loading: false,
        });
      })
      .catch((err) => {
        console.error('Error fetching posts:', err.message);
        this.setState({
          error: err.message,
          loading: false,
        });
      });
  }

  // Step 3: componentDidCatch - Error Boundary lifecycle hook
  // Catches JavaScript errors anywhere in child component tree
  // Parameters:
  //   error - the actual error object
  //   info  - object with componentStack property (which component threw)
  componentDidCatch(error, info) {
    console.error('componentDidCatch: An error was caught!');
    console.error('Error:', error);
    console.error('Component Stack:', info.componentStack);

    this.setState({
      hasError: true,
      error: error.message,
    });
  }

  // Step 4: render - describes what the UI should look like
  render() {
    console.log('render: Component is rendering...');

    const { posts, loading, error, hasError } = this.state;

    // Error boundary fallback UI
    if (hasError) {
      return (
        <div className="error-boundary">
          <h2>⚠️ Something went wrong.</h2>
          <p>{error}</p>
          <button onClick={() => this.setState({ hasError: false, error: null })}>
            Try Again
          </button>
        </div>
      );
    }

    // Loading state
    if (loading) {
      return (
        <div className="loading-container">
          <div className="spinner"></div>
          <p>Loading posts...</p>
        </div>
      );
    }

    // Error from fetch
    if (error) {
      return (
        <div className="error-container">
          <h3>❌ Failed to load posts</h3>
          <p>{error}</p>
        </div>
      );
    }

    // Render posts
    return (
      <div className="posts-container">
        <h2 className="posts-heading">📝 Blog Posts</h2>
        <p className="posts-subtext">
          Loaded <strong>{posts.length}</strong> posts via{' '}
          <code>componentDidMount()</code>
        </p>
        <div className="posts-grid">
          {posts.map((post) => (
            <Post
              key={post.id}
              id={post.id}
              title={post.title}
              body={post.body}
            />
          ))}
        </div>
      </div>
    );
  }
}

export default Posts;
