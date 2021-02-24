import React from "react"
import {Field, reduxForm} from "redux-form";
import {maxLengthCreator, minLengthCreator, required} from "../../validator/validators";
import {Input} from "../../common/FormControls/FormControls";

class GenreForm extends React.Component {

    maxLength =  maxLengthCreator(10)
    minLength =  minLengthCreator(3)

    render() {
        return (
            <div>
                <h1>Genre</h1>
                <form onSubmit={this.props.handleSubmit}>
                    <label>Name</label>
                    <div>
                        <Field placeholder="Genre name"
                               component={Input}
                               name={"name"}
                               validate={[required, this.maxLength,this.minLength]}
                        />
                    </div>
                    <label>Code</label>
                    <div>
                        <Field placeholder="Genre code"
                               component={Input}
                               name={"code"}
                               validate={[required, this.maxLength,this.minLength]}
                        />
                    </div>
                    <div>
                        <button>Add</button>
                    </div>
                </form>
            </div>
        )
    }
}

const ReduxGenreForm = reduxForm({
    form: 'genreFom'
})(GenreForm);

export default ReduxGenreForm