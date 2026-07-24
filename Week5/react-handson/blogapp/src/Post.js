import React from 'react';

// Post component - displays a single blog post
// Props: id, title, body
const Post = ({ id, title, body }) => {
  return (
    <div className="post-card">
      <div className="post-id">Post #{id}</div>
      <h3 className="post-title">{title}</h3>
      <p className="post-body">{body}</p>
    </div>
  );
};

export default Post;
