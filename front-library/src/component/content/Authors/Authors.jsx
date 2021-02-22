import React from "react";


const Authors = (props) => {

    let authorNameRef  = React.createRef();
    const rows = [];
    props.authors.forEach((author) => {
        rows.push(
            <tr key={author.name}>
                <td>{author.name}</td>
            </tr>
        );
    });

    let onAddAuthor = () => {
        props.addAuthor();
    };

    let onAuthorNameChange = () => {
        let newAuthorName = authorNameRef.current.value;
        let newAuthor = {
            name: newAuthorName
        };
        props.updateNewAuthorText(newAuthor)
    };

    return (
        <div>
            <table>
                <thead>
                <tr>
                    <th>Name</th>
                </tr>
                </thead>
                <tbody>{rows}</tbody>
            </table>
            <div>
                <input placeholder="Author name"
                       ref={authorNameRef}
                       onChange={onAuthorNameChange}
                       value={props.newAuthor.name} />
                <button onClick={onAddAuthor}>Add Author</button>
            </div>
        </div>
    )
};

export default Authors