import React from "react";
import styles from './Genres.module.css'
import GenreForm from "./GenreForm";

const Genres = (props) => {

    let rows = [];
    props.genres.forEach((genre) => rows.push(
        <tr key={genre.id}>
            <td>{genre.id}</td>
            <td>{genre.name}</td>
            <td>{genre.code}</td>
            <td><button onClick={() => props.onEditGenre(genre)}>Edit</button></td>
            <td><button className={styles.deleteBtn} onClick={() => props.onDeleteGenre(genre)}>Delete</button></td>
        </tr>
    ));


    let pagesCount = Math.ceil(props.totalCount / props.pageSize);

    let pages = [];

    for (let i = 1; i <= pagesCount; i++) {
        pages.push(i);
    }


    return (
        <div>
            <div className={styles.pagination}>
                {pages.map(p => {
                    return <span key={p} className={props.currentPage === p ? styles.selectedPage : undefined}
                                 onClick={(e) => {props.onPageChanged(p)}}>
                        {p}</span>
                })
                }

            </div>
            <div>
                <table>
                    <thead>
                    <tr>
                        <th>ID</th>
                        <th>Name</th>
                        <th>Code</th>
                        <th/>
                        <th/>
                    </tr>
                    </thead>
                    <tbody>
                    {rows}
                    </tbody>
                </table>
            </div>
            <GenreForm {...props}/>
        </div>
    )
};

export default Genres