import Genres from "./Genres";
import {connect} from "react-redux";
import {
    addGenre, deleteGenre,
    getGenres,
    getGenresCount,
    setCurrentPage
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

    onEditGenre = (genre) => {
        alert(`editing: ${genre}`)
    };

    onDeleteGenre = (genre) => {
        this.props.deleteGenre(genre);
        this.props.getGenres(this.props.currentPage, this.props.pageSize);
        this.props.getGenresCount();
    };

    onSubmit = (newGenre) => {
        this.props.addGenre(newGenre);
        this.props.getGenres(this.props.currentPage, this.props.pageSize);
        this.props.getGenresCount();
    };


    render() {
        return (
            <div>
                <Genres {...this.props}
                        onPageChanged ={this.onPageChanged}
                        onEditGenre={this.onEditGenre}
                        onDeleteGenre={this.onDeleteGenre}
                        onSubmit={this.onSubmit}
                />
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
    addGenre,
    deleteGenre,
    getGenres,
    getGenresCount,
    setCurrentPage,
};


let gc = connect(mapStateToProps, AC)(GenresContainer);

export default gc;