import React, { Component } from 'react';

// CONDITIONAL RENDERING METHOD 2: Ternary Operator (condition ? true : false)

class BlogDetails extends Component {
  render() {
    const blogs = [
      {
        id: 1,
        title: 'React Learning',
        author: 'Stephen Biz',
        body: 'Welcome to learning React!',
        published: true,
      },
      {
        id: 2,
        title: 'Installation',
        author: 'Schewzdenier',
        body: 'You can install React from npm.',
        published: true,
      },
      {
        id: 3,
        title: 'Draft Post',
        author: 'Unknown',
        body: 'This post is not published yet.',
        published: false,
      },
    ];

    const { showDrafts } = this.props;

    return (
      <div className="column">
        <h2>Blog Details</h2>

        {blogs.map((blog) => (
          // METHOD 2: Ternary — decide whether to render the blog card at all
          blog.published || showDrafts ? (
            <div key={blog.id} className="item">
              {/* METHOD 2: Ternary — show title styled differently if draft */}
              <h3 style={{ color: blog.published ? 'black' : 'gray' }}>
                {blog.title}
              </h3>

              {/* METHOD 2: Ternary — show author name or "Anonymous" */}
              <p><strong>{blog.author ? blog.author : 'Anonymous'}</strong></p>

              {/* METHOD 2: Ternary — show body or a placeholder */}
              <p>{blog.published ? blog.body : <em>(Draft — not published)</em>}</p>
            </div>
          ) : null
        ))}
      </div>
    );
  }
}

export default BlogDetails;
