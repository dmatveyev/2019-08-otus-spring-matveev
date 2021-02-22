
const SET_USER_DATA = 'SET_USER_DATA';

let initialState = {
    id: null,
    fullName: null,
    status: null,
    ava: null,
    isAuth: false,
    items: null
};

export const authMe = () => {


    return () => {
    }
};

const authReducer = (state = initialState, action) => {

    switch (action.type) {
        case SET_USER_DATA:
            return  {
                ...state,
                ...action.data,
                isAuth: true
            };

        default:
            return state;
    }
};

const simpleReducer = (state, action) => {
  return  {
      ...state,
      ...action
  }
};

export default simpleReducer;