import React from "react"
import styles from './Header.module.css';
import {NavLink} from "react-router-dom";

const Header = (props) => {
    return (

            <header className={styles.header}>
                <img className={styles.logo} src="22.png" alt=""/>
                <div className={styles.loginBlock}>
                    {props.isAuth ? <div>
                            {props.email}
                            {/*<img alt="" src={props.ava} className={styles.ava}/>*/}

                        </div>:
                        <NavLink to={'/login'}>Login</NavLink>}
                </div>
            </header>
    )
};

export default Header;