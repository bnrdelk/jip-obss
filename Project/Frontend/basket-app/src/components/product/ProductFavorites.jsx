import React, { useEffect, useState, useContext } from 'react';
import axiosInstance from '../axiosConfig/AxiosInstance';
import { List, Avatar, Button, message } from 'antd';
import { HeartOutlined, HeartFilled, StopOutlined } from '@ant-design/icons';
import { UserContext } from '../context/UserContext';
import axios from 'axios';

const ProductFavorites = () => {
    
    const { user } = useContext(UserContext);
    const [favorites, setFavorites] = useState([]);
    const [error, setError] = useState(null);

    const fetchFavorites = async () => {
        if (!user || !user.accessToken) {
            setError('User not authenticated.');
            return;
        }

        try {
            const response = await axios.get(`http://localhost:8080/v1/products/user/favorites/${user.userId}`);
            setFavorites(response.data.data);
        } catch (error) {
            console.error('Error fetching favorites:', error);
            setError('Failed to fetch favorite products.');
        }
    };

    const handleLike = async (productId, likedByUser) => {
        try {
            const response = await axiosInstance.post(`/products/${likedByUser ? 'unlike' : 'like'}/${productId}`);
            message.success(`Product ${likedByUser ? 'unliked' : 'liked'} successfully.`);
        } catch (error) {
            console.error('Error liking/unliking product:', error.response ? error.response.data : error.message);
            message.error('Failed to update product like status.');
        }
    };


    useEffect(() => {
        if (user && user.accessToken) {
            fetchFavorites();
        }
    }, [user]); 

    if (error) {
        return <div>Error: {error}</div>;
    }

    return (
        <div>
            <h2>Favorite Products</h2>
            {favorites.length === 0 ? (
                <p>No favorite products found.</p>
            ) : (
                <List
                    itemLayout="horizontal"
                    dataSource={favorites}
                    renderItem={(product) => (
                        <List.Item
                            actions={[
                                <Button
                                    icon={product.likedByUser ? <HeartFilled /> : <HeartOutlined />}
                                    onClick={() => handleLike(product.id, product.likedByUser)}
                                />,
                            ]}
                        >
                            <List.Item.Meta
                                avatar={<Avatar src={product.image ? `http://localhost:8080${product.image}` : '/defaultImage.jpeg'} />}
                                title={product.name}
                                description={`Price: $${product.price}`}
                            />
                        </List.Item>
                    )}
                />
            )}
        </div>
    );
};

export default ProductFavorites;
