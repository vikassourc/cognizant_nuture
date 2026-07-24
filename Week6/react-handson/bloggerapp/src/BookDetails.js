import React, { Component } from 'react';

// CONDITIONAL RENDERING METHOD 3: && (Logical AND / Short-Circuit Evaluation)
// CONDITIONAL RENDERING METHOD 6: IIFE (Immediately Invoked Function Expression)

class BookDetails extends Component {
  render() {
    const books = [
      { id: 1, title: 'Master React',            price: 670, inStock: true  },
      { id: 2, title: 'Deep Dive into Angular 11', price: 800, inStock: true  },
      { id: 3, title: 'Mongo Essentials',          price: 450, inStock: false },
    ];

    const { showOutOfStock } = this.props;

    return (
      <div className="column">
        <h2>Book Details</h2>

        {books.map((book) => (
          <div key={book.id} className="item">
            {/* METHOD 6: IIFE — inline self-invoking function in JSX */}
            {(() => {
              if (!showOutOfStock && !book.inStock) return null;
              return <h3>{book.title}</h3>;
            })()}

            {/* METHOD 3: && Short-Circuit — only show price if book is renderable */}
            {(showOutOfStock || book.inStock) && (
              <p>{book.price}</p>
            )}

            {/* METHOD 3: && Short-Circuit — show "Out of Stock" badge only when applicable */}
            {!book.inStock && showOutOfStock && (
              <span className="badge">Out of Stock</span>
            )}
          </div>
        ))}
      </div>
    );
  }
}

export default BookDetails;
