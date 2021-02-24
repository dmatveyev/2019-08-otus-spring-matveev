import React from "react";
import Header from "./Header";
import {connect} from "react-redux";
import {authMe} from "../../redux/auth-reducer";

class HeaderContainer extends React.Component {

    componentDidMount() {
        this.props.authMe();
    }

    render() {
        return <Header {...this.props}/>
    }
}


let mapStateToProps = (state) => ({
    isAuth: state.auth.isAuth,
    fullName: state.auth.fullName,
    ava: state.auth.ava,
    email: state.auth.email
});


export default connect(mapStateToProps, {authMe} )(HeaderContainer)

//export default HeaderContainer