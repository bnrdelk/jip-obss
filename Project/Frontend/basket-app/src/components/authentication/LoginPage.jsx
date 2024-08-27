// src/authentication/LoginPage.js
import React, { useState, useContext } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';
import axiosInstance from '../axiosConfig/AxiosInstance';
import { UserContext } from '../context/UserContext';
import logo from '../../assets/basketLogo.png';
import { Alert, Button, Col, Form, Input, Row, Typography } from 'antd';

const { Title } = Typography;

function LoginPage() {
    const [message, setMessage] = useState('');
    const [isEmailValid, setIsEmailValid] = useState(false);
    const navigate = useNavigate();
    const { setUser, setLoggedIn } = useContext(UserContext);

    const validateEmail = (email) => {
        const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        return emailRegex.test(email);
    };

    const onFinish = async (values) => {
        const { email, password } = values;
        try {
            const response = await axiosInstance.post('/auth/login', {
                email,
                password,
            });

            const { accessToken, userId, roles } = response.data.data;
            const userRole = roles.find(role => role.name === 'ADMIN') 
                            || roles.find(role => role.name === 'SELLER')
                            || roles.find(role => role.name === 'USER');

            const userData = { email, accessToken, userId, role: userRole ? userRole.name : 'USER' };

            localStorage.setItem('user', JSON.stringify(userData));
            localStorage.setItem('token', accessToken);

            setUser(userData);
            setLoggedIn(true);

            setMessage('Login successful!');
            navigate('/');
        } catch (error) {
            setMessage('Login failed. Please check your credentials.');
        }
    };

    const onValuesChange = (changedValues) => {
        if (changedValues.email) {
            setIsEmailValid(validateEmail(changedValues.email));
        }
    };

    return (
        <Row justify="center" align="middle" style={{ height: '70vh' }}>
            <Col xs={24} sm={18} md={12} lg={8}>
                <div style={{ padding: '24px', background: '#fff', borderRadius: '16px', boxShadow: '0 8px 8px rgba(0, 0, 0, 0.1)', backgroundColor: "rgba(91, 114, 150, 0.39)" }}>
                    <img src={logo} alt="Logo" style={{ height: 70, width: 140, display: 'block', margin: '0 auto' }} />
                    <Title level={2} style={{ textAlign: 'center' }}>Login</Title>
                    {message && <Alert message={message} type={message.includes('successful') ? 'success' : 'error'} showIcon style={{ marginBottom: '16px' }} />}
                    <Form
                        layout="vertical"
                        onFinish={onFinish}
                        onValuesChange={onValuesChange}
                    >
                        <Form.Item
                            label="Email"
                            name="email"
                            rules={[
                                { required: true, message: 'Please input your email!' },
                                { type: 'email', message: 'The input is not valid email!' }
                            ]}
                        >
                            <Input />
                        </Form.Item>
                        <Form.Item
                            label="Password"
                            name="password"
                            rules={[{ required: true, message: 'Please input your password!' }]}
                        >
                            <Input.Password />
                        </Form.Item>
                        <Form.Item>
                            <Button type="primary" htmlType="submit" disabled={!isEmailValid} block>
                                Login
                            </Button>
                        </Form.Item>
                    </Form>
                </div>
            </Col>
        </Row>
    );
}

export default LoginPage;
