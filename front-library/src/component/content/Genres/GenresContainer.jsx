import Genres from "./Genres";
import {connect} from "react-redux";
import {addGenre, getGenres, updateGenreActionCreator} from "../../../redux/genres-reducer";
import * as React from "react";

class GenresContainer extends React.Component {

    componentDidMount() {
       this.props.getGenres()
    }


    render() {
        return (
            <div>
                <Genres {...this.props}/>
            </div>
        )
    }
}


let mapStateToProps = (state) => {
    
    return {
        genres: state.genres.genres,
        newGenre: state.genres.newGenre
    }
};

let AC = {
    addGenre: addGenre,
    updateGenre: updateGenreActionCreator,
    getGenres
};


let gc = connect(mapStateToProps, AC)(GenresContainer);

export default gc;