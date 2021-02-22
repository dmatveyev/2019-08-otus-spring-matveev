const ADD_BOOK = 'ADD_BOOK';
const UPDATE_BOOK = 'UPDATE_BOOK';

let initialState = {
    books : [
        {title: "ddd1", author: "adf1", genre: "dddd1", isbn: "34234234"},
        {title: "ddd2", author: "adf2", genre: "dddd2", isbn: "34234234"},
        {title: "ddd3", author: "adf3", genre: "dddd3", isbn: "34234234"}
    ],
    newBook: {
        title: "",
        author: "",
        genre: "",
        isbn: ""
    }
};

const bookReducer = (state = initialState, action) => {

    switch (action.type) {
        case ADD_BOOK: {
            let newBook = {
                ...state.newBook
            };
            return {
                ...state,
                books: [...state.books, newBook],
                newBook: {
                    title: '',
                    author: '',
                    genre: '',
                    isbn: ''
                }
            };
        }
        case UPDATE_BOOK: {
            return {
                ...state,
                newBook: action.newBook
            }

        }
        default:
            return state;
    }
};

export const addBookActionCreator = () => ({
  type: ADD_BOOK
});

export const updateNewBookTextActionCreator = (newBook) => ({
    type: UPDATE_BOOK,
    newBook: newBook
});

export default bookReducer;