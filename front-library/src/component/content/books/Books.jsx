import React from "react";


class Books extends React.Component {

    constructor(props) {
        super(props);
    }

    render() {
        return (
            <div>
                <BooksTable {...this.props} />


            </div>
        )
    }
}


export default Books


class BookRow extends React.Component {
    constructor(props) {
        super(props);
    }

    render() {
        const book = this.props.book;

        return (
            <tr >
                <td>{book.title}</td>
                <td>{book.author}</td>
                <td>{book.genre}</td>
                <td>{book.isbn}</td>
            </tr>
        );
    }
}

class BooksTable extends React.Component {

    constructor(props) {
        super(props);
    }

    render() {
        const rows = [];
        this.props.books.forEach((book) => {
            rows.push(
                <BookRow
                    book={book}
                    key={book.title}/>
            );
        });

        return (
            <table>
                <thead>
                <tr>
                    <th>Name</th>
                    <th>Author</th>
                    <th>Genre</th>
                    <th>ISBN</th>
                </tr>
                </thead>
                <tbody>{rows}</tbody>
            </table>
        );
    }
}






