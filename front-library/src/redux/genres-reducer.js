import {createGenre, getGenresApi} from "../api/api";

const ADD_GENRE = 'ADD_GENRE';
const UPDATE_GENRE = 'UPDATE_GENRE';
const SET_GENRES = 'SET_GENRES';

let initialState = {
    genres: [],
    newGenre: {name: "", code: ""}

};

export const setGenres = (genres) => {
    return {
        type: SET_GENRES,
        genres: genres
    }
};


export const getGenres = () => {
    return (dispatch) => {
        getGenresApi()
            .then(data => {
                dispatch(setGenres(data))
            });
    };

};

const genreReducer = (state = initialState, action) => {

    switch (action.type) {
        case SET_GENRES: {
            return {
                ...state,
                genres: action.genres
            }
        }

        case ADD_GENRE: {
            let newGenre = {...action.newGenre};

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

export const addGenre = (genre) =>{
    return (dispatch) => {
        createGenre(genre)
            .then(data => {
                dispatch(addGenreActionCreator(data))
            });
    };
};

export const addGenreActionCreator = (genre) => ({
    type: ADD_GENRE,
    newGenre: genre
});

export const updateGenreActionCreator = (newGenre) => ({
    type: UPDATE_GENRE,
    newGenre
});

export default genreReducer;
