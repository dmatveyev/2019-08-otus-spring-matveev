import {authMeApi} from "../api/api";

const SET_USER_DATA = 'SET_USER_DATA';

let initialState = {
    userId: null,
    email: null,
    login: null,
    fullName: null,
    status: null,
    ava: null,
    isAuth: false,
    items: null
};
const authReducer = (state = initialState, action) => {

    switch (action.type) {
        case SET_USER_DATA:
            return {
                ...state,
                ...action.data,
                isAuth: true
            };

        default:
            return state;
    }
};

export const authMe = () => {
    return (dispatch) => {
        authMeApi().then(data => {
                if (data.status === 200) {
                    let {userId, login, email} = data.data;
                    dispatch(setUserData(userId, email, login))
                }
            }
        )
    }
};


const setUserData = (userId, email, login) => ({
    type: SET_USER_DATA,
    data: {userId, email, login}
});

export default authReducer;