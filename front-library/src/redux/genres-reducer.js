import {createGenre, deleteGenreApi, getGenresApi, getGenresCountApi} from "../api/api";

const ADD_GENRE = 'ADD_GENRE';
const SET_GENRES = 'SET_GENRES';
const SET_GENRES_COUNT = 'SET_GENRES_COUNT';
const SET_CURRENT_PAGE = 'SET_CURRENT_PAGE';

let initialState = {
    genres: [],
    totalCount: 0,
    pageSize: 5,
    currentPage: 1

};

const genreReducer = (state = initialState, action) => {

    switch (action.type) {
        case SET_GENRES: {
            return {
                ...state,
                genres: action.genres
            }
        }

        case SET_GENRES_COUNT: {
            return {
                ...state,
                totalCount: action.totalCount
            }
        }

        case ADD_GENRE: {
            let newGenre = {...action.newGenre};

            return {
                ...state,
                genres: [...state.genres, newGenre],
            }
        }
        case SET_CURRENT_PAGE: {
            return {
                ...state,
                currentPage: action.currentPage
            }
        }
        default: return state;
    }
};

export const setGenres = (genres) => {
    return {
        type: SET_GENRES,
        genres: genres
    }
};


export const getGenres = (page = 1, count = 1) => {
    return (dispatch) => {
        getGenresApi(page, count)
            .then(data => {
                dispatch(setGenres(data))
            });
    };

};


export const getGenresCount = () => {
    return (dispatch) => {
        getGenresCountApi()
            .then(data => {
                dispatch({
                    type: SET_GENRES_COUNT,
                    totalCount: data
                })
            });
    };

};

export const setCurrentPage = (currentPage) => ({
    type: SET_CURRENT_PAGE,
    currentPage
});

export const addGenre = (genre) =>{
    return (dispatch) => {
        createGenre(genre)
            .then(data => {
                dispatch(addGenreActionCreator(data))
            });
    };
};

export const deleteGenre = (genre) =>{
    return (dispatch) => {
        deleteGenreApi(genre).then(r => r)
    };
};

export const addGenreActionCreator = (genre) => ({
    type: ADD_GENRE,
    newGenre: genre
});

export default genreReducer;
