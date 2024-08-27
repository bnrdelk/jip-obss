import axios from 'axios';

const baseURL = 'http://localhost:8080/v1';
const getToken = () => {
    return localStorage.getItem('token');
};

console.log('Token:', getToken());

const axiosInstance = axios.create({
    baseURL,
});

axiosInstance.interceptors.request.use(
    config => {
        const token = getToken();
        console.log(token)
        if (token && token !== "undefined") { 
            config.headers.Authorization = `Bearer ${token}`;
        }
        return config;
    },
    error => Promise.reject(error)
);

axiosInstance.interceptors.response.use(
    response => response,
    error => {console.log('Error Response:', error.response);
        if (error.response.status === 401) {
            window.location.href = '/login';
        }
        return Promise.reject(error);
    }
);

export default axiosInstance ;
