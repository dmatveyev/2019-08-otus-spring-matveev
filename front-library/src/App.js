import React from "react"
import './App.css';
import Navbar from "./component/navbar/Navbar";
import {Route} from "react-router-dom";
import HeaderContainer from "./component/header/HeaderContainer";
import BooksContainer from "./component/content/books/BooksContainer";
import Genres from "./component/content/Genres/Genres";
import AuthorsContainer from "./component/content/Authors/AuthorsContainer";
import GenresContainer from "./component/content/Genres/GenresContainer";

const App = (props) => {


    return (
    <div className="app-wrapper">
      <HeaderContainer />
      <Navbar/>
      <div className="app-wrapper-content">
        <Route path="/books" render={() => <BooksContainer store={props.store} />} />
        <Route path="/authors" render={() => <AuthorsContainer store={props.store} />} />
        <Route path="/genres" render={() => <GenresContainer store={props.store} />} />
      </div>
    </div>
  );
};

export default App;
