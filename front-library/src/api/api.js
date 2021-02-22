import axios from "axios";

const baseUrl = "http://localhost:8081/api";

const instance = axios.create({
    baseURL: baseUrl
});

export const getGenresApi = () => {
    return instance
        .get("/genres")
        .then(response => response.data)
};

export const createGenre = (genre) => {

    return instance.post("/genre", genre)
        .then(response => response.data)
};
