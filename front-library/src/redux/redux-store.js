import {applyMiddleware, combineReducers, createStore} from "redux";
import authReducer from "./auth-reducer";
import thunk  from "redux-thunk"
import bookReducer from "./book-reducer";
import authorReducer from "./author-reducer";
import genreReducer from "./genres-reducer";

let reducers = combineReducers({
    auth: authReducer,
    books: bookReducer,
    authors: authorReducer,
    genres: genreReducer
});

let store = createStore(reducers, applyMiddleware(thunk));

window.store=store;

export default store;