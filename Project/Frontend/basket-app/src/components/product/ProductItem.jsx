import React, { useContext } from 'react';
import { Link } from 'react-router-dom';
import { Card, Badge, Avatar, Tag, Alert } from 'antd';
import { HeartOutlined, HeartFilled, StopOutlined } from '@ant-design/icons';
import { UserContext } from '../context/UserContext';
import axiosInstance from '../axiosConfig/AxiosInstance';

const { Meta } = Card;

const ProductItem = ({ product, onLikeToggle, onBlacklistSeller, tags }) => {
    const { user } = useContext(UserContext);
    if (!product) {
        return <div>Loading...</div>;
    }

    const sellerId = product.seller ? product.seller.id : null;
    const imageUrl = product.image ? `http://localhost:8080${product.image}` : '/defaultImage.jpeg';

    const handleBlacklist = async () => {
        try {
            if (user?.userId) {
                const response = await axiosInstance.post(`/users/${user.userId}/blacklist/${product.seller.id}`);
                onBlacklistSeller(product.seller.id);
                return (
                    <Alert
                        message="Successful"
                        description={response.data?.data ? response.data.data.message : "User added to the blacklist."}
                        type="success"
                        showIcon
                    />
                );
            } else {
                return (
                    <Alert
                        message="Warning"
                        description="User ID cannot be null."
                        type="warning"
                        showIcon
                    />
                );
            }
        } catch (error) {
            console.error('Error blacklisting seller:', error);
            alert('Failed to blacklist seller.');
        }
    };

    return (
        <Badge count={product.likeCount} style={{ backgroundColor: 'blue' }}>
            <Card
                style={{ width: 250, height: 350, borderRadius: '0', margin: '2px', display: 'flex', flexDirection: 'column', justifyContent: 'space-between' }}
                cover={
                    <img
                        alt={product.name}
                        src={imageUrl}
                        style={{ objectFit: 'cover', height: '200px' }} 
                    />
                }
                actions={[
                    product.likedByUser ? (
                        <HeartFilled style={{ color: 'red' }} onClick={() => onLikeToggle(product.id, product.likedByUser)} />
                    ) : (
                        <HeartOutlined style={{ color: 'red' }} onClick={() => onLikeToggle(product.id, product.likedByUser)} />
                    ),
                    <StopOutlined
                        key="blacklist"
                        style={{ color: 'red' }}
                        onClick={handleBlacklist}
                    />,
                ]}
            >
                <Meta
                    avatar={
                        <Link to={`/profile/${sellerId}`}>
                            <Avatar src="https://api.dicebear.com/7.x/miniavs/svg?seed=8" />
                        </Link>
                    }
                    title={product.name}
                    description={`Price: $${product.price}`}
                />
                <div style={{ marginTop: '8px' }}>
                    {tags && tags.map((tag, index) => (
                        <Tag color='geekblue' key={index}>
                            {tag}
                        </Tag>
                    ))}
                </div>
            </Card>
        </Badge>
    );
};

export default ProductItem;
