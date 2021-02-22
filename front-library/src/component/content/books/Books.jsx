import React from "react";


class Books extends React.Component {

    constructor(props) {
        super(props);
    }

    render() {
        let newBookTitle = React.createRef();
        let newBookAuthor = React.createRef();
        let newBookGenre = React.createRef();
        let newBookIsbn = React.createRef();
        let onAddBook = () => {
            let title = newBookTitle.current.value;
            let author = newBookAuthor.current.value;
            let genre = newBookGenre.current.value;
            let isbn = newBookIsbn.current.value;
            if (title && author && genre && isbn) {
                this.props.addBook()
            } else {
                alert('empty data!')
            }
        };

        let onBookChange = () => {
            let title = newBookTitle.current.value;
            let author = newBookAuthor.current.value;
            let genre = newBookGenre.current.value;
            let isbn = newBookIsbn.current.value;

            let newBook = {
                title,
                author,
                genre,
                isbn
            };

            this.props.updateNewBookText(newBook);


        };

        return (
            <div>
                <BooksTable {...this.props} />
                <div>
                    <input placeholder="Add new Book Title"
                           ref={newBookTitle}
                           onChange={onBookChange}
                           value={this.props.newBook.title}/>
                    <input placeholder="Add new Book Author"
                           ref={newBookAuthor}
                           onChange={onBookChange}
                           value={this.props.newBook.author}/>
                    <input placeholder="Add new Book Genre"
                           ref={newBookGenre}
                           onChange={onBookChange}
                           value={this.props.newBook.genre}/>
                    <input placeholder="Add new Book isbn"
                           ref={newBookIsbn}
                           onChange={onBookChange}
                           value={this.props.newBook.isbn}/>
                    <button className="add new" onClick={onAddBook}>Add</button>
                </div>

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






