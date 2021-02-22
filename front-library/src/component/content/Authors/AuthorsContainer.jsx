import React from "react"
import Authors from "./Authors";
import {connect} from "react-redux";
import {addAuthorActionCreator, updateNewAuthorTextActionCreator} from "../../../redux/author-reducer";


let mapStateToProps = (state) => {

    return {
        authors: state.authors.authors,
        newAuthor: state.authors.newAuthor
    }
};

let mapDispatchToProps = (dispatch) => {
    return {
        addAuthor: () => {
            let action = addAuthorActionCreator();
            dispatch(action);
        },
        updateNewAuthorText: (newAuthor) => {
            let action = updateNewAuthorTextActionCreator(newAuthor);
            dispatch(action);
        }

    }

};

let AuthorsContainer = connect(mapStateToProps, mapDispatchToProps)(Authors);
export default AuthorsContainer