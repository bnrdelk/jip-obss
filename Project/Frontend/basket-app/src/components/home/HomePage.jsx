import React from 'react';
import { Row, Col, Button, Space } from 'antd';
import { LoginOutlined, ShoppingOutlined, UserAddOutlined } from '@ant-design/icons';
import { useNavigate } from 'react-router-dom';
import TagChart from './TagChart';
import logo from '../../assets/basketLogo.png';

const HomePage = () => {
    const navigate = useNavigate();

    const handleNavigateToProducts = () => {
        navigate('/products');
    };

    const handleNavigateToLogin = () => {
        navigate('/login');
    };

    const handleNavigateToRegister = () => {
        navigate('/register');
    };

    return (
        <div style={{ padding: '20px', background: '#f0f2f5' }}>
            <Row gutter={[16, 16]}>
                <Col xs={24} md={12}>
                    <div style={{
                        background: '#fff',
                        padding: '20px',
                        borderRadius: '8px',
                        boxShadow: '0 2px 8px rgba(0, 0, 0, 0.1)',
                        minHeight: '200px'
                    }}>
                        <TagChart />
                    </div>
                </Col>
                <Col xs={24} md={12}>
                    <div style={{
                        textAlign: 'center',
                        background: 'rgba(7, 34, 76, 1)',
                        padding: '20px',
                        borderRadius: '8px',
                        boxShadow: '0 2px 8px rgba(0, 0, 0, 0.1)'
                    }}>
                        <img src={logo} alt="Basket Logo" style={{ width: '200px', borderRadius: '4px', marginBottom: '20px' }} />
                        <h2 style={{ color: '#ffffff' }}>Welcome to Basket</h2>
                        <p style={{ color: '#ffffff' }}>
                            Lorem ipsum dolor sit amet consectetur adipisicing elit. Aliquam recusandae qui eos placeat corporis eveniet quisquam error vitae iure distinctio at quis, fuga accusamus corrupti velit. Tempora at voluptate illum.
                        </p>
                    </div>
                </Col>
            </Row>
            <Row gutter={[16, 16]} style={{ marginTop: '20px' }}>
                <Col xs={24} md={12}>
                    <div style={{
                        textAlign: 'center',
                        background: 'rgba(197, 196, 199, 1)',
                        padding: '20px',
                        borderRadius: '8px',
                        boxShadow: '0 2px 8px rgba(0, 0, 0, 0.1)'
                    }}>
                        <h2 style={{ color: '#ffffff' }}>Explore Our Products</h2>
                        <p style={{ color: '#ffffff' }}>
                            Check out our wide range of products. Find what you're looking for and more!
                        </p>
                        <Space>
                            <Button type="primary" icon={<ShoppingOutlined />} onClick={handleNavigateToProducts}>
                                Go to Products
                            </Button>
                        </Space>
                    </div>
                </Col>
                <Col xs={24} md={12}>
                    <div style={{
                        textAlign: 'center',
                        background: 'rgba(202, 196, 211, 1)',
                        padding: '20px',
                        borderRadius: '8px',
                        boxShadow: '0 2px 8px rgba(0, 0, 0, 0.1)',
                        height: '186px'
                    }}>
                        <h2>Welcome Back!</h2>
                        <p>Access your account or create a new one to get started.</p>
                        <Space>
                            <Button 
                                type="primary" 
                                icon={<LoginOutlined />} 
                                onClick={handleNavigateToLogin}
                            >
                                Login
                            </Button>
                            <Button 
                                type="default" 
                                icon={<UserAddOutlined />} 
                                onClick={handleNavigateToRegister}
                            >
                                Register
                            </Button>
                        </Space>
                    </div>
                </Col>
            </Row>
        </div>
    );
};

export default HomePage;
