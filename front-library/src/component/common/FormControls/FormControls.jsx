import React from "react"
import styles from "./FormControl.module.css"


export const Input = (props) => {
    const {input, meta, child, ...restProps} = props;
    return (
        //Подумать, как предать элемент
        <FormControl {...props} ><input {...input} {...restProps}/> </FormControl>
    )
};

const FormControl = ({input, meta, child, ...props}) => {
    let error = meta.error;
    let hasError = meta.touched && error;
    return (
        <div className={styles.formControl + " " + (hasError ? styles.error: "") }>
            <div>
                {props.children}
            </div>
            {hasError && <span>{error}</span>}
        </div>
    )

};