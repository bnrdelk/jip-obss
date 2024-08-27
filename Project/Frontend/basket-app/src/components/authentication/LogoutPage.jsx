import React, { useEffect, useContext } from 'react';
import { useNavigate } from 'react-router-dom';
import { UserContext } from '../context/UserContext'; 

const Logout = () => {
    const { setUser, setLoggedIn } = useContext(UserContext); 

    useEffect(() => {
        setUser(null);
        localStorage.removeItem('token'); 
        localStorage.removeItem('user'); 
        setLoggedIn(false);

        window.location.href = '/';
    }, [ setUser, setLoggedIn]);

    return <p>Logging out...</p>;
};

export default Logout;