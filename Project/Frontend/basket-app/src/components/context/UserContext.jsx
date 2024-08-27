import React, { createContext, useState, useEffect } from 'react';

const UserContext = createContext();

const UserProvider = ({ children }) => {
    const [user, setUser] = useState(null);
    const [loggedIn, setLoggedIn] = useState(false);

    useEffect(() => {
        const storedUser = JSON.parse(localStorage.getItem('user'));
        const accessToken = storedUser?.accessToken;
        localStorage.setItem('token', accessToken);
        if (storedUser && storedUser.accessToken) {
            setUser(storedUser);
            setLoggedIn(true);
        }
    }, []);

    const updateUser = (userData) => {
        localStorage.setItem('user', JSON.stringify(userData));
        setUser(userData);
    };

    return (
        <UserContext.Provider value={{ user, loggedIn, setUser: updateUser, setLoggedIn }}>
            {children}
        </UserContext.Provider>
    );
};

export { UserContext, UserProvider };
