const ADD_GENRE = 'ADD_GENRE';
const UPDATE_GENRE = 'UPDATE_GENRE';

let initialState = {
    genres: [
        {name: "Action", code: "001"},
    ],
    newGenre: {name: "", code: ""}

};

const genreReducer = (state = initialState, action) => {

    switch (action.type) {
        case ADD_GENRE: {
            let newGenre = {...state.newGenre};

            return {
                ...state,
                genres: [...state.genres, newGenre],
                newGenre: {name: '', code: ''}
            }
        }
        case UPDATE_GENRE: {
            return {
                ...state,
                newGenre: action.newGenre
            }
        }
        default: return state;
    }


};

export const addGenreActionCreator = () => ({
    type: ADD_GENRE
});

export const updateGenreActionCreator = (newGenre) => ({
   type: UPDATE_GENRE,
   newGenre
});

export default genreReducer;
