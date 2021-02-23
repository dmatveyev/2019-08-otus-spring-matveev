import React from "react";
import styles from './Genres.module.css'
import {getGenres, getGenresCount} from "../../../redux/genres-reducer";

const Genres = (props) => {

    let rows = [];
    props.genres.forEach((genre) => rows.push(
        <tr key={genre.id}>
            <td>{genre.id}</td>
            <td>{genre.name}</td>
            <td>{genre.code}</td>
        </tr>
    ));

    let genreNameRef = React.createRef();
    let genreCodeRef = React.createRef();

    let onGenreUpdate = () => {
        let code = genreCodeRef.current.value;
        let name = genreNameRef.current.value;
        let newGenre = {
            name: name,
            code: code
        };
        props.updateGenre(newGenre)

    };

    let onAddGenre = () => {
        let code = genreCodeRef.current.value;
        let name = genreNameRef.current.value;
        let newGenre = {
            name,
            code
        };
        if (name && code) {
            props.addGenre(newGenre);
            props.getGenres(props.currentPage, props.pageSize);
            props.getGenresCount();
        } else {
            alert("empty data!")
        }

    };
    let pagesCount = Math.ceil(props.totalCount / props.pageSize);

    let pages = [];

    for (let i = 1; i <= pagesCount; i++) {
        pages.push(i);
    }


    return (
        <div>
            <div className={styles.pagination}>
                {pages.map(p => {
                    return <span className={props.currentPage === p && styles.selectedPage}
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
                    </tr>
                    </thead>
                    <tbody>
                    {rows}
                    </tbody>
                </table>
                <div>
                    <input placeholder="Genre name"
                           ref={genreNameRef}
                           onChange={onGenreUpdate}
                           value={props.newGenre.name}
                    />
                    <input placeholder="Genre code"
                           ref={genreCodeRef}
                           onChange={onGenreUpdate}
                           value={props.newGenre.code}
                    />
                    <button onClick={onAddGenre}>Add</button>
                </div>
            </div>
        </div>
    )
};

export default Genres