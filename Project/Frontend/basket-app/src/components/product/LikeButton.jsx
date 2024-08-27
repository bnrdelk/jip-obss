import { useState } from 'react';
import axiosInstance from '../axiosConfig/AxiosInstance';

const LikeButton = (fetchProducts) => {
    const [error, setError] = useState(null);

    const handleLike = async (productId, likedByUser) => {
        try {
            if (likedByUser) {
                await axiosInstance.post(`/products/unlike/${productId}`);
            } else {
                await axiosInstance.post(`/products/like/${productId}`);
            }
            await fetchProducts(); // refresh
        } catch (error) {
            console.error('Error liking/unliking product: ', error);
            setError('Failed to update like status');
        }
    };

    return { handleLike, error };
};

export default LikeButton;

