import React from 'react';
import { Button, Col, Row } from 'antd';
import logo from '../../assets/basketLogo.png';
import { useNavigate } from 'react-router-dom';
import Title from 'antd/es/skeleton/Title';

const RegisterPage = () => {
    const navigate = useNavigate();

    const navigateToUserRegister = () => {
        navigate('/register/user');
    };

    const navigateToSellerRegister = () => {
        navigate('/register/seller');
    };
    return (
        <Row justify="center" align="middle" style={{ height: '70vh' }}>
            <Col xs={24} sm={18} md={12} lg={8}>
        <div style={{ width: '300', display: 'flex', flexDirection: 'column', alignItems: 'center', marginTop: '50px',  padding: '24px', background: '#fff', borderRadius: '16px', boxShadow: '0 8px 8px rgba(0, 0, 0, 0.1)', backgroundColor: "rgba(91, 114, 150, 0.39)"}}>
        <img src={logo} alt="Logo" style={{ height: 70, width: 140 }} />
            <Title level={2} style={{ textAlign: 'center' }}>Register</Title>
            <Button type="primary" onClick={navigateToUserRegister} style={{ marginBottom: '16px' }}>
                Register as User
            </Button>
            <Button type="primary" onClick={navigateToSellerRegister}>
                Register as Seller
            </Button>
        </div>
        </Col>
        </Row>
    );
};
    

export default RegisterPage;
