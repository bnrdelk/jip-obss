import React, { useState, useEffect } from 'react';
import axiosInstance from '../axiosConfig/AxiosInstance';
import { Alert, Spin, Table, Popconfirm, Button } from 'antd';
import { useContext } from 'react';
import { UserContext } from '../context/UserContext';

const BlacklistedSellers = () => {
    const [blacklistedSellers, setBlacklistedSellers] = useState([]);
    const [error, setError] = useState(null);
    const [loading, setLoading] = useState(false);
    const { user } = useContext(UserContext);

    useEffect(() => {
        const fetchBlacklistedSellers = async () => {
            setLoading(true);
            try {
                const response = await axiosInstance.get('/users/blacklisted-sellers');
                setBlacklistedSellers(response.data.data);
                setError(null);
            } catch (error) {
                setError('Failed to fetch blacklisted sellers.');
                console.error('Error fetching blacklisted sellers:', error);
            } finally {
                setLoading(false);
            }
        };

        fetchBlacklistedSellers();
    }, []);

    const handleRemoveBlacklist = async (sellerId) => {
        if (user?.userId) {
            try {
                await axiosInstance.delete(`/users/${user.userId}/blacklist/${sellerId}`);
                setBlacklistedSellers(prev => prev.filter(seller => seller.id !== sellerId));
                setError(null);
            } catch (error) {
                setError('Failed to remove seller from blacklist.');
                console.error('Error removing seller from blacklist:', error);
            }
        } else {
            setError('User ID is not available.');
        }
    };

    const columns = [
        {
            title: 'Name',
            dataIndex: 'fullName',
            key: 'fullName',
        },
        {
            title: 'Email',
            dataIndex: 'email',
            key: 'email',
        },
        {
            title: 'Action',
            key: 'action',
            render: (_, record) => (
                <Popconfirm
                    title="Are you sure you want to remove this seller from blacklist?"
                    onConfirm={() => handleRemoveBlacklist(record.id)}
                    okText="Yes"
                    cancelText="No"
                >
                    <Button type="link">Remove</Button>
                </Popconfirm>
            ),
        },
    ];

    return (
        <div>
            {loading && <Spin />}
            {error && <Alert message={error} type="error" />}
            {blacklistedSellers.length === 0 && !loading && !error && <p>No blacklisted sellers found.</p>}
            <Table
                dataSource={blacklistedSellers}
                columns={columns}
                rowKey="id"
                pagination={{
                    pageSize: 5, 
                    total: blacklistedSellers.length, 
                }}
            />
        </div>
    );
};

export default BlacklistedSellers;
