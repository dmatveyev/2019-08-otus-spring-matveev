import React from "react";
import Books from "./Books";
import {connect} from "react-redux"
import {addBookActionCreator, updateNewBookTextActionCreator} from "../../../redux/book-reducer";


let mapStateToProps = (state) => {
    return {
        books: state.books.books,
        newBook: state.books.newBook,

    }
};

let mapDispatchToProps = (dispatch) => {

    return {
        addBook: () => {
            let action = addBookActionCreator();
            dispatch(action);
        },
        updateNewBookText: (newBook) => {
            let action = updateNewBookTextActionCreator(newBook);
            dispatch(action);
        }
    }
};

let BooksContainer = connect(mapStateToProps, mapDispatchToProps)(Books);
export default BooksContainer;

