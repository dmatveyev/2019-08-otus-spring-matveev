import Genres from "./Genres";
import {connect} from "react-redux";
import {
    addGenre,
    getGenres,
    getGenresCount,
    setCurrentPage,
    updateGenreActionCreator
} from "../../../redux/genres-reducer";
import * as React from "react";

class GenresContainer extends React.Component {

    componentDidMount() {
       this.props.getGenres(this.props.currentPage, this.props.pageSize)
       this.props.getGenresCount()
    }

    onPageChanged = (pageNumber) => {
        this.props.setCurrentPage(pageNumber);
        this.props.getGenres(pageNumber, this.props.pageSize);
        this.props.getGenresCount();
    };

    onAddGenre = (newGenre) => {
        this.props.addGenre(newGenre);
        this.props.getGenres(this.props.currentPage, this.props.pageSize);
        this.props.getGenresCount();
    };


    render() {
        return (
            <div>
                <Genres {...this.props}
                        onPageChanged ={this.onPageChanged}
                        onAddGenre={this.onAddGenre}/>
            </div>
        )
    }
}


let mapStateToProps = (state) => {
    
    return {
        genres: state.genres.genres,
        newGenre: state.genres.newGenre,
        pageSize: state.genres.pageSize,
        totalCount: state.genres.totalCount,
        currentPage: state.genres.currentPage
    }
};

let AC = {
    addGenre: addGenre,
    updateGenre: updateGenreActionCreator,
    getGenres,
    getGenresCount,
    setCurrentPage,
};


let gc = connect(mapStateToProps, AC)(GenresContainer);

export default gc;