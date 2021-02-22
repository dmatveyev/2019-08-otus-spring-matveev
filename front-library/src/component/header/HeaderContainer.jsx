import React from "react";
import Header from "./Header";
import {connect} from "react-redux";


let mapStateToProps = (state) => ({
    isAuth: state.auth.isAuth,
    fullName: state.auth.fullName,
    ava: state.auth.ava
});


export default connect(mapStateToProps)(Header)

//export default HeaderContainer