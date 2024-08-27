import React, { useState, useEffect, useContext } from 'react';
import { useNavigate } from 'react-router-dom';
import { Form, Input, InputNumber, Checkbox, Button, message, Typography, Col, Row, Card } from 'antd';
import axiosInstance from '../axiosConfig/AxiosInstance';
import { UserContext } from '../context/UserContext';
import axios from 'axios';

const { Item: FormItem } = Form;
const { Title } = Typography;

const ProductForm = () => {
    const [productName, setProductName] = useState('');
    const [productPrice, setProductPrice] = useState('');
    const [description, setDescription] = useState('');
    const [selectedTags, setSelectedTags] = useState([]);
    const [tags, setTags] = useState([]);
    const [imageUrl, setImageUrl] = useState('');
    const [error, setError] = useState('');
    const { user } = useContext(UserContext);
    const [file, setFile] = useState(null);
    const navigate = useNavigate();

    useEffect(() => {
        if (!user) {
            navigate('/login');
        }
    }, [user, navigate]);

    useEffect(() => {
        const fetchTags = async () => {
            try {
                const response = await axios.get('http://localhost:8080/v1/tags');
                const tagsFromResponse = response.data.data;

                if (Array.isArray(tagsFromResponse)) {
                    setTags(tagsFromResponse.map(tag => tag.tagType));
                } else {
                    setError('Failed to fetch tags.');
                }
            } catch (err) {
                setError('Failed to fetch tags.');
            }
        };

        fetchTags();
    }, []);

    const handleTagChange = (event) => {
        const { value, checked } = event.target;
        setSelectedTags(prevTags =>
            checked ? [...prevTags, value] : prevTags.filter(tag => tag !== value)
        );
    };

    const handleFileChange = (e) => {
        setFile(e.target.files[0]);
    };

    const handleImageUpload = async () => {
        if (!file) {
            message.error('Please select a file.');
            return;
        }
        const formData = new FormData();
        formData.append('file', file);

        try {
            const response = await axios.post('http://localhost:8080/v1/products/upload', formData, {
                headers: {
                    'Content-Type': 'multipart/form-data',
                },
            });
            setImageUrl(response.data.url); 
            message.success('Image uploaded successfully.');
        } catch (error) {
            message.error('Image upload failed.');
        }
    };

    const handleSubmit = async (values) => {
        if (selectedTags.length === 0) {
            setError('Please select at least one tag.');
            return;
        }
        try {
            const email = user.email;
            const normalizedTags = selectedTags.map(tag => tag.toUpperCase());

            await axiosInstance.post(`/products/add?email=${email}`, {
                name: productName,
                price: productPrice,
                description: description,
                tags: normalizedTags,
                image: imageUrl 
            });

            message.success('Product added successfully!');
            navigate('/products'); 
        } catch (error) {
            const errorMessage = error.response?.data?.message || 'Failed to add product. Please try again.';
            setError(errorMessage);
        }
    };

    return (
        <Row justify="center" align="middle" style={{ minHeight: '100vh', backgroundColor: '#f0f2f5' }}>
            <Col xs={24} sm={18} md={16} lg={12}>
                <Card
                    title={<Title level={2} style={{ textAlign: 'center' }}>Add New Product</Title>}
                    bordered={false}
                    style={{ padding: '24px', borderRadius: '8px', boxShadow: '0 4px 8px rgba(0, 0, 0, 0.1)', backgroundColor: '#fff' }}
                >
                    <Form
                        onFinish={handleSubmit}
                        layout="vertical"
                    >
                        <FormItem label="Product Name" name="name" rules={[{ required: true, message: 'Please input product name!' }]}>
                            <Input value={productName} onChange={(e) => setProductName(e.target.value)} />
                        </FormItem>
                        <FormItem label="Description" name="description" rules={[{ required: true, message: 'Please input description!' }]}>
                            <Input.TextArea rows={4} value={description} onChange={(e) => setDescription(e.target.value)} />
                        </FormItem>
                        <FormItem label="Product Price" name="price" rules={[{ required: true, message: 'Please input product price!' }]}>
                            <InputNumber
                                min={0}
                                step={0.01}
                                style={{ width: '100%' }}
                                value={productPrice}
                                onChange={(value) => setProductPrice(value)}
                            />
                        </FormItem>
                        <FormItem label="Select Tags">
                            {tags.map(tag => (
                                <Checkbox
                                    key={tag}
                                    value={tag}
                                    checked={selectedTags.includes(tag)}
                                    onChange={handleTagChange}
                                    style={{ marginRight: '10px' }}
                                >
                                    {tag}
                                </Checkbox>
                            ))}
                        </FormItem>
                        <FormItem label="Upload Image">
                            <input type="file" onChange={handleFileChange} accept=".png,.jpg,.jpeg" />
                            <Button
                                type="default"
                                style={{ marginTop: '10px' }}
                                onClick={handleImageUpload}
                            >
                                Upload
                            </Button>
                        </FormItem>
                        {error && <FormItem><div style={{ color: 'red' }}>{error}</div></FormItem>}
                        <FormItem>
                            <Button type="primary" htmlType="submit" block>
                                Add Product
                            </Button>
                        </FormItem>
                    </Form>
                </Card>
            </Col>
        </Row>
    );
};

export default ProductForm;
