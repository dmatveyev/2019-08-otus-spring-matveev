import axios from "axios";

export const getGenresApi = () => {
    return axios
        .get("http://localhost:8081/api/genres")
        .then(response => response.data)
};

export const createGenre = (genre) => {

    return axios.post("http://localhost:8081/api/genre", genre)
        .then(response => response.data)
};
