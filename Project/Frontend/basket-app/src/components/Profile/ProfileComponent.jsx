import React, { useState, useEffect, useContext } from 'react';
import { Button, List, message, Modal, Tag } from 'antd';
import axiosInstance from '../axiosConfig/AxiosInstance';
import { useNavigate, useParams } from 'react-router-dom';
import axios from 'axios';
import { UserContext } from '../context/UserContext';

const ProfileComponent = () => {
    const { id } = useParams();
    const [userData, setUserData] = useState(null);
    const [favoriteProducts, setFavoriteProducts] = useState([]);
    const [loading, setLoading] = useState(false);
    const { user } = useContext(UserContext);
    const navigate = useNavigate();

    useEffect(() => {
        const fetchUser = async () => {
            try {
                const response = await axios.get(`http://localhost:8080/v1/users/${id}`);
                console.log(response.data.data)
                setUserData(response.data.data); 
            } catch (error) {
                console.error('Error fetching user:', error);
                message.error('Failed to fetch user data.');
            }
        };

        const fetchFavoriteProducts = async () => {
            try {
                const response = await axios.get(`http://localhost:8080/v1/products/user/favorites/${id}`);
                setFavoriteProducts(response.data.data); 
            } catch (error) {
                console.error('Error fetching favorite products:', error);
                message.error('Failed to fetch favorite products.');
            }
        };

        fetchUser();
        fetchFavoriteProducts();
    }, [id]);

    
    const handleBlacklistUser = async () => {
        setLoading(true);
        try {
            await axiosInstance.post(`/users/${user.userId}/blacklist/${id}`);
            message.success('User has been blacklisted.');
            navigate('/');
        } catch (error) {
            console.error('Error blacklisting user:', error);
            message.error('Failed to blacklist user.');
        } finally {
            setLoading(false);
        }
    };
    return (
        <div style={{ padding: '16px', background: '#f0f2f5' }}>
            {userData ? (
                <div>
                    <h2>User Profile</h2>
                    <p><strong>Name:</strong> {userData.fullName}</p>
                    <p><strong>Email:</strong> {userData.email}</p>
                    <div style={{ marginBottom: '16px' }}>
                        {userData.roles.map(role => (
                            <Tag color="blue" key={role.id} >
                                {role.name}
                            </Tag>
                        ))}
                    </div>
                    <Button 
                        type="danger" 
                        onClick={() => 
                            Modal.confirm({
                                title: 'Are you sure you want to blacklist this user?',
                                onOk: handleBlacklistUser
                            })
                        }
                        loading={loading}
                    >
                        Blacklist User
                    </Button>
                    <h3 style={{ marginTop: '24px' }}>Favorite Products</h3>
                    <List
                        itemLayout="horizontal"
                        dataSource={favoriteProducts}
                        renderItem={(product) => (
                            <List.Item>
                                <List.Item.Meta
                                    title={product.name}
                                    description={`Price: ${product.price}`}
                                />
                            </List.Item>
                        )}
                    />
                </div>
            ) : (
                <p>Loading user data...</p>
            )}
        </div>
    );
};

export default ProfileComponent;