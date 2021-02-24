import {Field, reduxForm} from "redux-form";
import React, {Component} from "react"
import {Input} from "../../common/FormControls/FormControls";
import {maxLengthCreator, minLengthCreator, required} from "../../validator/validators";

class Login extends Component {

    maxLength = maxLengthCreator(10);
    minLength = minLengthCreator(3);
    render() {
        return (
            <div>
                <h1>Login</h1>
                <form onSubmit={this.props.handleSubmit}>
                    <label>Login</label>
                    <div>
                        <Field placeHolder="login"
                               component={Input}
                               name="login"
                               validate={[required, this.maxLength, this.minLength]}/>
                    </div>
                    <label>Password</label>
                    <div>
                        <Field placeHolder="password"
                               component={Input}
                               name="password"
                               validate={[required, this.maxLength, this.minLength]}/>
                    </div>
                    <button>Login</button>

                </form>
            </div>
        )
    }
}


export default reduxForm({
    form: "loginForm"
})(Login)