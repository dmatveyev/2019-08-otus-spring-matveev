import axios from "axios";

const baseUrl = "http://localhost:8081/api";

const instance = axios.create({
    baseURL: baseUrl
});

export const getGenresApi = (page, count) => {
    return instance
        .get(`/genres?page=${page}&count=${count}`)
        .then(response => response.data)
};

export const getGenresCountApi = () => {
    return instance
        .get(`/genres/count`)
        .then(response => response.data)
};


export const createGenre = (genre) => {

    return instance.post("/genre", genre)
        .then(response => response.data)
};
