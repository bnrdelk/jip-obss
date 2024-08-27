import React, { useState, useEffect, useContext } from 'react';
import { Button, Form, Input, message, Modal, List, Avatar, Tabs, Tag, Typography } from 'antd';
import { useNavigate } from 'react-router-dom';
import axiosInstance from '../axiosConfig/AxiosInstance';
import { UserContext } from '../context/UserContext';
import BlacklistedSellers from './BlackListedSellers';
import ProductFavorites from '../product/ProductFavorites';

const { Title } = Typography;
const { TabPane } = Tabs;

const ProfilePage = () => {
    const { user, setUser } = useContext(UserContext);
    const [isEditing, setIsEditing] = useState(false);
    const [products, setProducts] = useState([]);
    const [form] = Form.useForm();

    useEffect(() => {
        if (user) {
            form.setFieldsValue({
                name: user.name,
                surname: user.surname,
                email: user.email,
            });

            if (user.role === 'SELLER') {
                fetchSellerProducts();
            }
        }
    }, [user, form]);

    const fetchSellerProducts = async () => {
        try {
            const response = await axiosInstance.get(`/sellers/${user.userId}/get-products`);
            setProducts(response.data.data);
        } catch (error) {
            message.error('Failed to fetch seller products.');
        }
    };

    const handleEditClick = () => {
        setIsEditing(true);
    };

    return (
        <div style={{ padding: '70px', background: '#f0f2f5', minHeight: '70vh' }}>
            <Title level={3}>Profile Page</Title>
            <Tabs defaultActiveKey="1" size="large">
                <TabPane tab="Information" key="1">
                    <div style={{ marginBottom: '16px' }}>
                        <Button type="primary" onClick={handleEditClick} size="small">Edit</Button>
                    </div>
                    {user && (
                        <div>
                            <p><strong>Name:</strong> {user.name}</p>
                            <p><strong>Surname:</strong> {user.surname}</p>
                            <p><strong>Email:</strong> {user.email}</p>
                            <p><strong>Tags:</strong></p>
                           
                        </div>
                    )}
                    {user && user.role === 'SELLER' && (
                            <div style={{ marginTop: '24px' }}>
                                <Title level={4}>Products Sold</Title>
                                <List
                                    itemLayout="horizontal"
                                    dataSource={products}
                                    renderItem={product => (
                                            <List.Item.Meta
                                                avatar={<Avatar src={product.imageUrl} />}
                                                title={product.name}
                                                description={`Price: ${product.price}`}
                                            />
                                    )}
                                />
                            </div>
                        )}
                </TabPane>
                <TabPane tab="Blacklisted" key="3">
                    <BlacklistedSellers />
                </TabPane>
            </Tabs>
            
        </div>
    );
};

export default ProfilePage;
