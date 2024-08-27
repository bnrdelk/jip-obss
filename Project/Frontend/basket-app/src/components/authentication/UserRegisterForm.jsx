import React from 'react';
import { Form, Input, Button, Typography, Alert, Row, Col } from 'antd';
import axiosInstance from '../axiosConfig/AxiosInstance';
import logo from '../../assets/basketLogo.png';
const { Title } = Typography;

const UserRegisterForm = () => {
    const [form] = Form.useForm();
    const [message, setMessage] = React.useState('');

    const handleSubmit = async (values) => {
        try {
            await axiosInstance.post('/auth/register/user', values);
            setMessage('User registered successfully');
        } catch (error) {
            setMessage('Failed to register user');
        }
    };

    return (
        <Row justify="center" align="middle" style={{ height: '100vh' }}>
            <Col xs={24} sm={18} md={12} lg={8}>
                <div style={{ padding: '24px', background: '#fff', borderRadius: '16px', boxShadow: '0 8px 8px rgba(0, 0, 0, 0.1)', backgroundColor: "rgba(91, 114, 150, 0.39)"}}>
                <img src={logo} alt="Logo" style={{ height: 70, width: 140 }} />
                    <Title level={2} style={{ textAlign: 'center' }}>Register as User</Title>
                    {message && <Alert message={message} type={message.includes('successfully') ? 'success' : 'error'} showIcon style={{ marginBottom: '16px' }} />}
                    <Form
                        form={form}
                        layout="vertical"
                        onFinish={handleSubmit}
                    >
                        <Form.Item name="email" label="Email" rules={[{ required: true, message: 'Please input your email!' }, { type: 'email', message: 'The input is not valid email!' }]}>
                            <Input />
                        </Form.Item>
                        <Form.Item name="name" label="Name" rules={[{ required: true, message: 'Please input your name!' }]}>
                            <Input />
                        </Form.Item>
                        <Form.Item name="password" label="Password" rules={[{ required: true, message: 'Please input your password!' }]}>
                            <Input.Password />
                        </Form.Item>
                        <Form.Item>
                            <Button type="primary" htmlType="submit" block>
                                Register User
                            </Button>
                        </Form.Item>
                    </Form>
                </div>
            </Col>
        </Row>
    );
};

export default UserRegisterForm;
