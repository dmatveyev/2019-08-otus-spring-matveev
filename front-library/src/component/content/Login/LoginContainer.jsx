import React, {Component} from "react"
import Login from "./Login";
import {connect} from "react-redux";
import {authMe} from "../../../redux/auth-reducer";
class LoginContainer extends Component {

    onSubmit = (data) => {
        this.props.authMe();
        alert(`${data.login} + ${data.password}`)
    };

    render() {
        return (
           <Login {...this.props} onSubmit={this.onSubmit}/>
        )
    }
}




let mapStateToProps = (state) => {

    return {
        isAuth: state.auth.isAuth
    }
};

let mapDispatchToProps = {
  authMe
};

export default connect(mapStateToProps, mapDispatchToProps)(LoginContainer)