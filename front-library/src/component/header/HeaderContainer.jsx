import React from "react";
import Header from "./Header";

class HeaderContainer extends React.Component {


    render() {
        return (
            <div>
                <Header {...this.props}/>
            </div>

        )
    }

}

let mapStateToProps = (state) => ({
    //isAuth: state.auth.isAuth,
    //fullName: state.auth.fullName,
    //ava: state.auth.ava
});


//export default connect(mapStateToProps)(HeaderContainer)

export default HeaderContainer