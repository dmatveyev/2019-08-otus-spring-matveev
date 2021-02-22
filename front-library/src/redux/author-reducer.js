const ADD_AUTHOR = 'ADD_AUTHOR';
const UPDATE_AUTHOR = 'UPDATE_AUTHOR';

let initialState = {
    authors : [
        {name: "Swift"},
        {name: "Brontie"}
    ],
    newAuthor: {
        name: ""
    }
};

const authorReducer = (state = initialState, action) => {

    switch (action.type) {
        case ADD_AUTHOR: {
            let newAuthor = {
                ...state.newAuthor
            };
            return {
                ...state,
                authors: [...state.authors, newAuthor],
                newAuthor: {
                    name: ''
                }

            };
        }
        case UPDATE_AUTHOR: {
            return {
                ...state,
                newAuthor: action.newAuthor,
            }

        }
        default:
            return state;
    }
};

export const addAuthorActionCreator = () => ({
  type: ADD_AUTHOR
});

export const updateNewAuthorTextActionCreator = (newAuthor) => ({
    type: UPDATE_AUTHOR,
    newAuthor
});

export default authorReducer;