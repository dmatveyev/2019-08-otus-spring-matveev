import React from "react";


class BookForm extends React.Component {


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
                <h1>Book</h1>
                <div>
                    <label>Title</label>
                    <input placeholder="Add new Book Title"
                           ref={newBookTitle}
                           onChange={onBookChange}
                           value={this.props.newBook.title}/>
                </div>
                <div>
                    <label>Author</label>
                    <input placeholder="Add new Book Author"
                           ref={newBookAuthor}
                           onChange={onBookChange}
                           value={this.props.newBook.author}/>
                </div>
                <div>
                    <label>Genre</label>
                    <input placeholder="Add new Book Genre"
                           ref={newBookGenre}
                           onChange={onBookChange}
                           value={this.props.newBook.genre}/>
                </div>
                <div>
                    <label>ISBN</label>
                    <input placeholder="Add new Book isbn"
                           ref={newBookIsbn}
                           onChange={onBookChange}
                           value={this.props.newBook.isbn}/>
                </div>
                <div>
                    <button className="addNew" onClick={onAddBook}>Add</button>
                    <button className="close" onClick={onAddBook}>Close</button>
                </div>
            </div>
        )
    }
}