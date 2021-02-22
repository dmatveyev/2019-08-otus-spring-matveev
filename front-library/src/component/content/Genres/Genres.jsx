import React from "react";

const Genres = (props) => {

    let rows = [];
    props.genres.forEach((genre) => rows.push(
        <tr key={genre.code}>
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
        } else {
            alert("empty data!")
        }

    };
    return (
        <div>
            <table>
                <thead>
                <tr>
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
    )
};

export default Genres