import Genres from "./Genres";
import {connect} from "react-redux";
import {addGenreActionCreator, updateGenreActionCreator} from "../../../redux/genres-reducer";

let mapStateToProps = (state) => {
    
    return {
        genres: state.genres.genres,
        newGenre: state.genres.newGenre
    }
};

let mapDispatchToProps = (dispatch) => {
    
    return {
        addGenre: () => {
            dispatch(addGenreActionCreator())
        },
        updateGenre: (newGenre) => {
            dispatch(updateGenreActionCreator(newGenre))
        }
        
    }
};

let GenresContainer = connect(mapStateToProps, mapDispatchToProps)(Genres);

export default GenresContainer;