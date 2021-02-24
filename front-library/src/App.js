import React, {Component} from "react"
import './App.css';
import Navbar from "./component/navbar/Navbar";
import {Route} from "react-router-dom";
import HeaderContainer from "./component/header/HeaderContainer";
import BooksContainer from "./component/content/books/BooksContainer";
import Genres from "./component/content/Genres/Genres";
import AuthorsContainer from "./component/content/Authors/AuthorsContainer";
import GenresContainer from "./component/content/Genres/GenresContainer";
import Login from "./component/content/Login/Login";
import LoginContainer from "./component/content/Login/LoginContainer";

class App extends Component {
    render() {


        return (
            <div className="app-wrapper">
                <HeaderContainer/>
                <Navbar/>
                <div className="app-wrapper-content">
                    <Route path="/books" render={() => <BooksContainer store={this.props.store}/>}/>
                    <Route path="/authors" render={() => <AuthorsContainer store={this.props.store}/>}/>
                    <Route path="/genres" render={() => <GenresContainer store={this.props.store}/>}/>
                    <Route path="/login" render={() => <LoginContainer store={this.props.store}/>}/>
                </div>
            </div>
        );
    }
}

export default App;
