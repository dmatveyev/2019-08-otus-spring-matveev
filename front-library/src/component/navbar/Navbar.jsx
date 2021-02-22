import React from "react";
import styles from "./Navbar.module.css";
import {NavLink} from "react-router-dom";

const Navbar = () => {
    return (
        <nav className={styles.nav}>
            <div className={styles.item}>
                <NavLink to="/books" activeClassName={styles.activeLink}>Books
                </NavLink>
            </div>
            <div className={styles.item}>
                <NavLink to="/authors" activeClassName={styles.activeLink}>
                    Authors
                </NavLink>
            </div>
            <div className={styles.item}>
                <NavLink to="/genres" activeClassName={styles.activeLink}>
                    Genres
                </NavLink>
            </div>
        </nav>
    )
};

export default Navbar